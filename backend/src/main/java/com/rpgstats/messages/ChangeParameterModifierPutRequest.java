package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeParameterModifierPutRequest {
  private String name;
  private Integer value;
  private Integer parameterId;
}
