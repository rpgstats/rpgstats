package com.rpgstats.messages;

import com.rpgstats.entity.ConstraintType;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.entity.SystemTag;

public class ChangeAttributeConstraintPutRequest {
  private ConstraintType constraintType;
  private SystemTag tag;
  private SystemAttribute attribute;
}
