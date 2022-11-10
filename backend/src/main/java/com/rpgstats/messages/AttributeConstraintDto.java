package com.rpgstats.messages;

import com.rpgstats.entity.ConstraintType;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.entity.SystemTag;
import lombok.Data;

@Data
public class AttributeConstraintDto {
    private Integer id;
    private ConstraintType constraintType;
    private SystemTag tag;
    private SystemAttribute attribute;
}
