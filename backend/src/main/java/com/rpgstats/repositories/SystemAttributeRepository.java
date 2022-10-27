package com.rpgstats.repositories;

import com.rpgstats.entity.SystemAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAttributeRepository extends JpaRepository<SystemAttribute, Integer> {
}