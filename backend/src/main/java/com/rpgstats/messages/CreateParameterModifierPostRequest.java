package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateParameterModifierPostRequest {
  @NotNull private String name;
  @NotNull private Double value;
  @NotNull private Integer parameterId;
}
