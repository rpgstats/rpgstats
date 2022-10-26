package com.rpgstats.repositories;

import com.rpgstats.entity.SystemAttributesParameterModifier;
import com.rpgstats.entity.SystemAttributesParameterModifierId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAttributesParameterModifierRepository extends JpaRepository<SystemAttributesParameterModifier, SystemAttributesParameterModifierId> {
}