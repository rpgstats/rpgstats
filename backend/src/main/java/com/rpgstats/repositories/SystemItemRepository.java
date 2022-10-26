package com.rpgstats.repositories;

import com.rpgstats.entity.SystemItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemItemRepository extends JpaRepository<SystemItem, Integer> {
}