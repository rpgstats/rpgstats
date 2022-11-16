package com.rpgstats.messages.DTO;

import lombok.Data;

@Data
public class UserCharacterDto {
  private Integer id;

  private String name;

  private String description;

  private Integer sessionId;
}
