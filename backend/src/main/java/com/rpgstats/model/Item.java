package com.rpgstats.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "system_items")
@Setter
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_present", nullable = false)
    private Boolean isPresent;

    @ManyToOne
    @JoinColumn(name = "system_id", nullable = false)
    private System system;
}
