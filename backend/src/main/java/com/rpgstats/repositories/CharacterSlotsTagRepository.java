package com.rpgstats.repositories;

import com.rpgstats.entity.CharacterSlotsTag;
import com.rpgstats.entity.CharacterSlotsTagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterSlotsTagRepository extends JpaRepository<CharacterSlotsTag, CharacterSlotsTagId> {
  List<CharacterSlotsTag> findById_SlotId(Integer slotId);
}