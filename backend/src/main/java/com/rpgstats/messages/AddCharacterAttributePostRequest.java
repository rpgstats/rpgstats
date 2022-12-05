package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCharacterAttributePostRequest {
  @NotNull private Integer id;
}
