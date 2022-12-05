package com.rpgstats.messages.DTO;

import lombok.Data;

@Data
public class CharacterDto {
  private Integer id;
  private String name;
  private String description;
  private Integer systemId;
  private Integer sessionId;
  private Integer userId;
}
