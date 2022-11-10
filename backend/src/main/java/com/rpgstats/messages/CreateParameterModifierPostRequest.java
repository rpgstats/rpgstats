package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemParameter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateParameterModifierPostRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer value;
    @NotNull
    private Integer parameterId;
}
