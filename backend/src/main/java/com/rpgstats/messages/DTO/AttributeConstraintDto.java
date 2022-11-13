package com.rpgstats.messages.DTO;


import lombok.Data;


@Data
public class AttributeConstraintDto {
    private Integer id;
    private ConstraintTypeDto constraintType;
    private SystemTagDto tag;
    private SystemAttributeDto attribute;
}
