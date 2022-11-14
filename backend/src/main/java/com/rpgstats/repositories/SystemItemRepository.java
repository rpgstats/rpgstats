package com.rpgstats.repositories;

import com.rpgstats.entity.SystemItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemItemRepository extends JpaRepository<SystemItem, Integer> {

  List<SystemItem> findByGameSystem_Id(Integer id);

  Optional<SystemItem> findByIdAndGameSystem_Id(Integer id, Integer id1);
}
