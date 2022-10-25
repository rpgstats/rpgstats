package com.rpgstats.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class GameSystemPutRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
}
