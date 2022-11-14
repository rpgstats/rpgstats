package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeParameterPutRequest {
  private String name;
  private Integer minValue;
  private Integer maxValue;
}
