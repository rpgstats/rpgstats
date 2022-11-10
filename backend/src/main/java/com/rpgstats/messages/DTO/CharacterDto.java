package com.rpgstats.messages.DTO;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.Session;
import com.rpgstats.entity.User;
import lombok.Data;


@Data
public class CharacterDto {
    private Integer id;
    private String name;
    private String description;
    private Session gameSession;
    private User owner;
}
