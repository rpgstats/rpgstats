package com.rpgstats.messages;

import com.rpgstats.entity.GameSystem;
import lombok.Data;

@Data
public class SystemItemDto {
    private Integer id;
    private String name;
    private Boolean isPresent;
    private GameSystem gameSystem;

}
