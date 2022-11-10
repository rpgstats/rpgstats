package com.rpgstats.messages;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ConstraintTypeDto {
    private Integer id;
    private String name;
}
