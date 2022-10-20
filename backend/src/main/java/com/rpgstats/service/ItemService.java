package com.rpgstats.service;

import com.rpgstats.dto.ItemDTO;
import com.rpgstats.mappers.ItemMapper;
import com.rpgstats.model.Item;
import com.rpgstats.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> getAllItems() {
        List<Item> categories = new ArrayList<>(itemRepository.findAll());
        return ItemMapper.INSTANCE.itemsToItemDTOs(categories);
    }

    public Item getCategoryByName(String name) {
        Optional<Item> item = itemRepository.getItemByName(name);
        if (item.isEmpty()) {
            throw new EntityNotFoundException("Item with such id was not found");
        }
        return item.get();
    }

    public ItemDTO createCategory(ItemDTO item) {
        Item newItem = ItemMapper.INSTANCE.itemDTOToItem(item);
        return ItemMapper.INSTANCE.itemToItemDTO(itemRepository.save(newItem));
    }
}

