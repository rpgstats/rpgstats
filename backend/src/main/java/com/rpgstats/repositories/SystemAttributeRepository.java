package com.rpgstats.repositories;

import com.rpgstats.entity.SystemAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemAttributeRepository extends JpaRepository<SystemAttribute, Integer> {

  List<SystemAttribute> findByGameSystem_Id(Integer id);

  Optional<SystemAttribute> findByGameSystem_IdAndId(Integer id, Integer id1);

  Optional<SystemAttribute> findByIdAndGameSystem_Id(Integer id, Integer id1);
}
