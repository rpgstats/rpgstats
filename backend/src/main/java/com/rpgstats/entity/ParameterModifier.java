package com.rpgstats.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parameter_modifiers")
public class ParameterModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parameter_id", nullable = false)
    private SystemParameter parameter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public SystemParameter getParameter() {
        return parameter;
    }

    public void setParameter(SystemParameter parameter) {
        this.parameter = parameter;
    }

}