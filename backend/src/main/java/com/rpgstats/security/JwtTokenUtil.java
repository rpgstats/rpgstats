package com.rpgstats.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtTokenUtil {
  @Autowired JwtEncoder encoder;
  @Autowired JwtDecoder decoder;

  public static final long JWT_TOKEN_VALIDITY = 60 * 10 * 10;

  public String getUserNameFromJwtToken(String authToken) {
    Jwt jwt = this.decoder.decode(authToken);
    return jwt.getSubject();
  }

  public String generateToken(Authentication authentication) {
    return doGenerateToken(authentication.getName());
  }

  private String doGenerateToken(String username) {
    Instant now = Instant.now();
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(JWT_TOKEN_VALIDITY))
            .subject(username)
            .build();
    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
