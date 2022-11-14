package com.rpgstats.repositories;

import com.rpgstats.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
  Optional<Session> findByIdAndCreatorId_Id(Integer id, Integer id1);
}
