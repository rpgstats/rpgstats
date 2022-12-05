package com.rpgstats.repositories;

import com.rpgstats.entity.CharacterSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterSlotRepository extends JpaRepository<CharacterSlot, Integer> {
  List<CharacterSlot> findByCharacter_Id(Integer id);

  Optional<CharacterSlot> findByIdAndCharacter_Id(Integer id, Integer id1);
}
