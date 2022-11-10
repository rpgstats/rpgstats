package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;

import lombok.Data;

import java.time.Instant;

@Data
public class ChangeParameterPutRequest {
    private String name;
    private Integer minValue;
    private Integer maxValue;
}
