package com.rpgstats.messages;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class ChangeParameterModifierPutRequest {
    private String name;
    private Integer value;
    private Integer parameterId;
}
