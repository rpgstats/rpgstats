package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class CreateParameterPostRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer minValue;
    @NotNull
    private Integer maxValue;
    @NotNull
    private GameSystem gameSystem;
}
