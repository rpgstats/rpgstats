package com.rpgstats.controllers;

import com.rpgstats.messages.AuthOkResponse;
import com.rpgstats.security.messages.SigninRequest;
import com.rpgstats.security.messages.SignupRequest;
import com.rpgstats.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

  AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/auth")
  public AuthOkResponse authPost(@Valid @RequestBody SigninRequest signinRequest) {
    return authService.login(signinRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return authService.register(signUpRequest);
  }
}
