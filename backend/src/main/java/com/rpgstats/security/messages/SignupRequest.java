package com.rpgstats.security.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignupRequest {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String email;
}
