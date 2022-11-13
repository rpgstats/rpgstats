package com.rpgstats.messages.DTO;

import lombok.Data;

@Data
public class ParameterModifierDto {
    private Integer id;
    private String name;
    private Integer value;
    private SystemParameterDto parameter;
}
