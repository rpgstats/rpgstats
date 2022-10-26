package com.rpgstats.repositories;

import com.rpgstats.entity.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, Integer> {
}