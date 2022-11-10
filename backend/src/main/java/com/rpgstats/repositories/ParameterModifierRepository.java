package com.rpgstats.repositories;

import com.rpgstats.entity.ParameterModifier;
import com.rpgstats.entity.SystemItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParameterModifierRepository extends JpaRepository<ParameterModifier, Integer> {

    List<SystemItem> findByGameSystem_Id(Integer id);

    Optional<ParameterModifier> findByGameSystem_IdAndId(Integer id, Integer id1);

    Optional<ParameterModifier> findByIdAndGameSystem_IdAndGameSystem_Owner_Id(Integer id, Integer id1, Integer id2);
}