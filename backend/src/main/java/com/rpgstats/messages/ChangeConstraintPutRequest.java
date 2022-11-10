package com.rpgstats.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class ChangeConstraintPutRequest {
    private Integer constraintTypeId;
    private Integer tagId;
    private Integer attributeId;
}
