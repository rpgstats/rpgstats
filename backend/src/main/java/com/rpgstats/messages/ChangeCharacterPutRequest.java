package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeCharacterPutRequest {
    private String name;
    private String description;
}
