package com.rpgstats.messages;

import lombok.Data;

@Data
public class GameSystemPutRequest {
  private String name;
  private String description;
}
