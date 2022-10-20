package com.rpgstats.repository;

import com.rpgstats.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> getItemByName(String name);
}
