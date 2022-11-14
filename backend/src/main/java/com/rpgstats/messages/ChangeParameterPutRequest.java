package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeParameterPutRequest {
  private String name;
  private Double minValue;
  private Double maxValue;
}
