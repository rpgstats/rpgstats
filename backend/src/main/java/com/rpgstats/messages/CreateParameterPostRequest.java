package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateParameterPostRequest {
  @NotNull private String name;
  @NotNull private Double minValue;
  @NotNull private Double maxValue;
}
