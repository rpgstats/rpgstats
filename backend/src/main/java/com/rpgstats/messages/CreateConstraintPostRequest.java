package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateConstraintPostRequest {
  @NotNull private Integer constraintTypeId;
  @NotNull private Integer tagId;
  @NotNull private Integer attributeId;
}
