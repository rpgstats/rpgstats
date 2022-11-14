package com.rpgstats.messages.DTO;

import lombok.Data;

@Data
public class SystemParameterModifierDto {
  private Integer id;
  private String name;
  private Integer value;
  private Integer parameterId;
  private String parameterName;
}
