package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCharacterSlotPostRequest {
  @NotNull private String name;
  @NotNull private String iconUrl;
  @NotNull private Boolean isWhitelisted;
  @NotNull private Integer itemId;
}
