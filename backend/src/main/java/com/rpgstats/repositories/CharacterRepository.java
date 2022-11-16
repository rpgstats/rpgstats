package com.rpgstats.repositories;

import com.rpgstats.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
  List<Character> findByUser_Id(Integer id);

  Optional<Character> findByIdAndUser_Id(Integer id, Integer id1);

  boolean existsBySession_IdAndName(Integer id, String name);
}