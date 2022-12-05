package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCharacterPutRequest {
  @NotNull private String name;
  @NotNull private String description;
}
