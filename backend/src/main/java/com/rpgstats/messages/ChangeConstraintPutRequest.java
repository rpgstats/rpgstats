package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeConstraintPutRequest {
  private Boolean hasTag;
  private Integer tagId;
  private Integer attributeId;
}
