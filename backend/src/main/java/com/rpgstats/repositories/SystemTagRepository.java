package com.rpgstats.repositories;

import com.rpgstats.entity.SystemItem;
import com.rpgstats.entity.SystemTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemTagRepository extends JpaRepository<SystemTag, Integer> {
    Optional<SystemTag> findByIdAndGameSystem_Id(Integer id, Integer id1);

    Optional<SystemTag> findByIdAndGameSystem_IdAndGameSystem_Owner_Id(Integer id, Integer id1, Integer id2);

    List<SystemTag> findByGameSystem_Id(Integer id);
}