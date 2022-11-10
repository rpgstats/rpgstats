package com.rpgstats.repositories;

import com.rpgstats.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findByOwner_Id(Integer id);

    Optional<Character> findByOwner_IdAndId(Integer id, Integer id1);

    @Override
    Optional<Character> findById(Integer integer);
}
