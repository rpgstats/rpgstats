package com.rpgstats.messages;

import lombok.Data;

@Data
public class UpdateCharacterSlotPutRequest {

  private String name;

  private String iconUrl;

  private Boolean isWhitelisted;

  private Integer itemId;
}
