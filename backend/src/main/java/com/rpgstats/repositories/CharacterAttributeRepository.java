package com.rpgstats.repositories;

import com.rpgstats.entity.CharacterAttribute;
import com.rpgstats.entity.CharacterAttributeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterAttributeRepository extends JpaRepository<CharacterAttribute, CharacterAttributeId> {
  List<CharacterAttribute> findById_CharacterId(Integer characterId);

  CharacterAttribute findById_CharacterIdAndId_AttributeId(
      Integer characterId, Integer attributeId);
}