package com.rpgstats.messages;

import lombok.Data;

@Data
public class CreateCharacterPostRequest {
  private String name;
  private String description;
  private Integer sessionId;
}
