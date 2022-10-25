package com.rpgstats.model;

import lombok.Data;

import java.time.Instant;

@Data
public class GameSystemDto {

    private Integer id;

    private String name;

    private String description;

    private Instant createdAt;

    private Integer userId;

    private Integer parentSystem;

}
