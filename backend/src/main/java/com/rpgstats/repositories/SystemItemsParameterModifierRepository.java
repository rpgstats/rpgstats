package com.rpgstats.repositories;

import com.rpgstats.entity.SystemItemsParameterModifier;
import com.rpgstats.entity.SystemItemsParameterModifierId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemItemsParameterModifierRepository extends JpaRepository<SystemItemsParameterModifier, SystemItemsParameterModifierId> {
}