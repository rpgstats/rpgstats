package com.rpgstats.services;

import com.rpgstats.entity.User;
import com.rpgstats.messages.AuthOkResponse;
import com.rpgstats.messages.MessageResponse;
import com.rpgstats.security.JwtTokenUtil;
import com.rpgstats.security.RpgStatsUserDetail;
import com.rpgstats.security.RpgStatsUserDetailsService;
import com.rpgstats.security.messages.SigninRequest;
import com.rpgstats.security.messages.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

  GameSystemService gameSystemService;

  SessionService sessionService;

  UserService userService;

  PasswordEncoder passwordEncoder;

  public void setSessionService(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @Autowired
  public void setGameSystemService(GameSystemService gameSystemService) {
    this.gameSystemService = gameSystemService;
  }

  @Autowired
  public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Autowired
  public void setUserDetailsService(RpgStatsUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public RpgStatsUserDetail getUserDetailsFromJwt(Jwt jwt) {
    String token = jwt.getTokenValue();
    return userDetailsService.loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(token));
  }

  public boolean checkUserAccessToSystem(int userId, int systemId) {
    return !gameSystemService.existById(systemId)
        || gameSystemService.existByIdAndOwnerId(systemId, userId);
  }

  public boolean checkUserAccessToSession(int userId, int sessionId) {
    return !sessionService.existById(sessionId)
        || sessionService.existByIdAndOwnerId(sessionId, userId);
  }

  public boolean checkUserAccessToSystem(Jwt authenticationToken, int systemId) {
    return checkUserAccessToSystem(getIdFromJwt(authenticationToken), systemId);
  }

  public boolean checkUserAccessToSession(Jwt authenticationToken, int sessioniD) {
    return checkUserAccessToSession(getIdFromJwt(authenticationToken), sessioniD);
  }

  public Integer getIdFromJwt(Jwt jwt) {
    return userDetailsService
        .loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(jwt.getTokenValue()))
        .getUserId();
  }

  public User getUserFromJwt(Jwt jwt) {
    return userDetailsService
        .loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(jwt.getTokenValue()))
        .getUser();
  }

  public AuthOkResponse login(SigninRequest signinRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signinRequest.getUsername(), signinRequest.getPassword()));
    String token = jwtTokenUtil.generateToken(authentication);
    RpgStatsUserDetail rpgStatsUserDetail = (RpgStatsUserDetail) authentication.getPrincipal();
    AuthOkResponse authOkResponse = new AuthOkResponse();
    authOkResponse.setId(rpgStatsUserDetail.getUserId());
    authOkResponse.setAuthToken(token);
    return authOkResponse;
  }

  @Transactional
  public ResponseEntity<?> register(SignupRequest signUpRequest) {
    userService.register(signUpRequest);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
