package com.rpgstats.repositories;

import com.rpgstats.entity.AttributeConstraint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttributeConstraintRepository extends JpaRepository<AttributeConstraint, Integer> {
    List<AttributeConstraint> findByAttribute_GameSystem_Id(Integer id);

    Optional<AttributeConstraint> findByAttribute_GameSystem_IdAndId(Integer id, Integer id1);

    Optional<AttributeConstraint> findByIdAndAttributeGameSystemOwnerId(Integer id, Integer ownerId);







}