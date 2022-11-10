package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import lombok.Data;

@Data
public class SystemTagDto {
    private Integer id;
    private String name;
    private GameSystem gameSystem;
}
