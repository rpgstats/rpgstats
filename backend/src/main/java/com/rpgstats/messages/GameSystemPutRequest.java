package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class GameSystemPutRequest {
    private String name;
    private String description;
}
