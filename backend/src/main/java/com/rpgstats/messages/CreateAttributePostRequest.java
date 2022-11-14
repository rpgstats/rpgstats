package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAttributePostRequest {
  @NotNull private String name;
  @NotNull private Boolean isPresent;
}
