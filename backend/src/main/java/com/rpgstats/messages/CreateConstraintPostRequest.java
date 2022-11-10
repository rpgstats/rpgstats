package com.rpgstats.messages;

import com.rpgstats.entity.SystemTag;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CreateConstraintPostRequest {
    @NotNull
    private Integer id;
    @NotNull
    private Integer constraintTypeId;
    @NotNull
    private Integer tagId;
    @NotNull
    private Integer attributeId;
}
