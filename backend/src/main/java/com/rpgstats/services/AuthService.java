package com.rpgstats.services;

import com.rpgstats.messages.*;
import com.rpgstats.security.JwtTokenUtil;
import com.rpgstats.security.RpgStatsUserDetail;
import com.rpgstats.security.RpgStatsUserDetailsService;
import com.rpgstats.security.messages.SigninRequest;
import com.rpgstats.security.messages.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {
    JwtTokenUtil jwtTokenUtil;

    AuthenticationManager authenticationManager;
    RpgStatsUserDetailsService userDetailsService;

    UserService userService;

    PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, RpgStatsUserDetailsService userDetailsService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public RpgStatsUserDetail getUserDetailsFromJwt(Jwt jwt) {
        String token = jwt.getTokenValue();
        return userDetailsService.loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(token));
    }

    public Integer getIdFromJwt(Jwt jwt) {
        return userDetailsService.loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(jwt.getTokenValue())).getUserId();
    }


    public AuthOkResponse login(SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        String token = jwtTokenUtil.generateToken(authentication);
        AuthOkResponse authOkResponse = new AuthOkResponse();
        authOkResponse.setAuthToken(token);
        return authOkResponse;
    }

    @Transactional
    public ResponseEntity<?> register(SignupRequest signUpRequest) {
        userService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
