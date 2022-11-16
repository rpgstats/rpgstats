package com.rpgstats.repositories;

import com.rpgstats.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
  List<Session> findByCreator_Id(Integer id);

  Optional<Session> findByIdAndCreator_Id(Integer id, Integer id1);

  List<Session> findByNameLikeIgnoreCase(String name);

  boolean existsByIdAndCreator_Id(Integer id, Integer id1);
}
