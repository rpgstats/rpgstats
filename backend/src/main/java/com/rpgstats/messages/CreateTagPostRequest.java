package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CreateTagPostRequest {
    @NotNull
    private String name;
    @NotNull
    private GameSystem gameSystem;
}
