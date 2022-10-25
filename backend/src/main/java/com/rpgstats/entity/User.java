package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String username;

    @Column(name = "email")
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Column(name = "password", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String password;

}