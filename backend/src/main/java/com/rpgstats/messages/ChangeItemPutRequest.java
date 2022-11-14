package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeItemPutRequest {
  private String name;
  private Boolean isPresent;
}
