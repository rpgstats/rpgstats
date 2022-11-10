package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import lombok.Data;
import java.time.Instant;

@Data
public class SystemParameterDto {
    private Integer id;
    private String name;
    private Integer minValue;
    private Integer maxValue;
    private Instant createdAt;
    private GameSystem gameSystem;
}
