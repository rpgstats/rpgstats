package com.rpgstats.messages;

import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.entity.SystemTag;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAttributeConstraintPostRequest {
  @NotNull private SystemTag tag;
  @NotNull private SystemAttribute attribute;
}
