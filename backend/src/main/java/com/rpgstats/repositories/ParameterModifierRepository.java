package com.rpgstats.repositories;

import com.rpgstats.entity.ParameterModifier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterModifierRepository extends JpaRepository<ParameterModifier, Integer> {
}