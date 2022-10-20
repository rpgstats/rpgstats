package com.rpgstats.dto;

import com.rpgstats.model.System;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemDTO {
    private Long id;
    private String name;
    private Boolean isPresent;
    private Long system_id;
}
