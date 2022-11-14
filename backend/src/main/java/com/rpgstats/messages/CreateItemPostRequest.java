package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateItemPostRequest {
  @NotNull private String name;
  @NotNull private Boolean isPresent;
}
