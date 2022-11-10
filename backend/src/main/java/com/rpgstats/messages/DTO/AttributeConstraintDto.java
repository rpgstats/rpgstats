package com.rpgstats.messages.DTO;


import lombok.Data;


@Data
public class AttributeConstraintDto {
    private Integer id;
    private Integer constraintTypeId;
    private String constraintTypeName;
    private Integer tagId;
    private String tagName;
    private Integer attributeId;
    private String attributeName;
}
