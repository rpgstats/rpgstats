package com.rpgstats.messages;

import lombok.Data;

@Data
public class ChangeAttributePutRequest {
    private String name;
    private Boolean isPresent;
}
