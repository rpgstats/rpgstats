package com.rpgstats.repositories;

import com.rpgstats.entity.UsersSession;
import com.rpgstats.entity.UsersSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersSessionRepository extends JpaRepository<UsersSession, UsersSessionId> {
  Optional<UsersSession> findById_UserIdAndId_SessionId(Integer userId, Integer sessionId);

  boolean existsById_UserIdAndId_SessionId(Integer userId, Integer sessionId);
}