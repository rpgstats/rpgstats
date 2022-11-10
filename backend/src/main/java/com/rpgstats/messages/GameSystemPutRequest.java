package com.rpgstats.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
public class GameSystemPutRequest {
    private String name;
    private String description;
}
