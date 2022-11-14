package com.rpgstats.repositories;

import com.rpgstats.entity.ParameterModifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterModifierRepository extends JpaRepository<ParameterModifier, Integer> {
  List<ParameterModifier> findByParameter_GameSystem_Id(Integer id);

  Optional<ParameterModifier> findByIdAndParameter_GameSystem_Id(Integer id, Integer id1);

  boolean existsByName(String name);
}
