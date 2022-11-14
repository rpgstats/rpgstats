package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateParameterPostRequest {
  @NotNull private String name;
  @NotNull private Integer minValue;
  @NotNull private Integer maxValue;
}
