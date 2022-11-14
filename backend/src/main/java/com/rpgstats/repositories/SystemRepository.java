package com.rpgstats.repositories;

import com.rpgstats.entity.GameSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemRepository extends JpaRepository<GameSystem, Integer> {
  List<GameSystem> findByNameLikeIgnoreCase(String name);

  Optional<GameSystem> findByIdAndOwner_Id(Integer id, Integer id1);

  boolean existsByName(String name);
}
