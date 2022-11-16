package com.rpgstats.messages;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class CreateCharacterPostRequest {
  @NotNull private String name;
  @NotNull private String description;
  @NotNull private Integer systemId;
  private Integer sessionId;
}
