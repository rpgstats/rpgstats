package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeConstraintPutRequest {
  private Integer constraintTypeId;
  private Integer tagId;
  private Integer attributeId;
}
