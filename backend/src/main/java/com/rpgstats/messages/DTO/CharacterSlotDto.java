package com.rpgstats.messages.DTO;

import lombok.Data;

@Data
public class CharacterSlotDto {
  private Integer id;

  private String name;

  private String iconUrl;

  private Boolean isWhitelisted;

  private Integer characterId;

  private Integer itemId;
}
