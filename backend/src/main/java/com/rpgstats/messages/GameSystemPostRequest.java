package com.rpgstats.messages;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GameSystemPostRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    private Integer parentSystem;

}
