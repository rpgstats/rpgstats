package com.rpgstats.model;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GameSystemPostRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer parentSystem;

}
