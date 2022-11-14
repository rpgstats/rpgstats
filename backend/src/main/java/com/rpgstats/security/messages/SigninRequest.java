package com.rpgstats.security.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SigninRequest {
  @NotNull String username;
  @NotNull String password;
}
