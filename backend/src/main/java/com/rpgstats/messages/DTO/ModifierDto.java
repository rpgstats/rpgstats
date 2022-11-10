package com.rpgstats.messages.DTO;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemParameter;
import lombok.Data;

@Data
public class ModifierDto {
    private Integer id;
    private String name;
    private Integer value;
    private SystemParameter parameter;
    private GameSystem gameSystem;
}
