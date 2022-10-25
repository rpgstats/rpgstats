package com.rpgstats.repositories;

import com.rpgstats.entity.System;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<System, Integer> {
}