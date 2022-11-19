package com.rpgstats.repositories;

import com.rpgstats.entity.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, Integer> {
  List<SystemParameter> findByGameSystem_Id(Integer id);

  Optional<SystemParameter> findByIdAndGameSystem_Id(Integer id, Integer id1);
}
