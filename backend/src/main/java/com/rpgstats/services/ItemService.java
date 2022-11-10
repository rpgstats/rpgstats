package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemItem;
import com.rpgstats.messages.ChangeItemPutRequest;
import com.rpgstats.messages.CreateItemPostRequest;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.DTO.SystemItemDto;
import com.rpgstats.repositories.SystemItemRepository;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {// TODO: добавить exception

    Logger log = LoggerFactory.getLogger(ItemService.class);

    SystemItemRepository itemRepository;
    SystemRepository systemRepository;
    ModelMapper mapper;

    public ItemService(SystemItemRepository itemRepository, SystemRepository systemRepository, ModelMapper mapper) {
        this.itemRepository = itemRepository;
        this.systemRepository = systemRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<SystemItemDto> getItemsBySystem(Integer systemId) {
        return itemRepository.findByGameSystem_Id(systemId).stream().map(item -> mapper.map(item, SystemItemDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public SystemItemDto getItem(Integer systemId, Integer itemId) {
        return mapper.map(itemRepository.findByGameSystem_IdAndId(systemId, itemId).
                orElseThrow(()->new ItemNotFoundException(String.format("Item with id - %d not found in system with id - %d", itemId, systemId))), SystemItemDto.class);
    }

    @Transactional
    public SystemItemDto createItem(Integer userId, Integer systemId, CreateItemPostRequest request) {
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).
                orElseThrow(() -> new ItemNotFoundException(String.format("System not found by id - %d", systemId)));
        SystemItem item = new SystemItem();
        item.setName(request.getName());
        item.setIsPresent(request.getIsPresent());
        item.setGameSystem(system);
        itemRepository.save(item);
        return mapper.map(item, SystemItemDto.class);
    }

    @Transactional
    public SystemItemDto changeItem(Integer userId, Integer itemId, Integer systemId, ChangeItemPutRequest request) {
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow();
        SystemItem item = itemRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(itemId, systemId, userId).
                orElseThrow(() -> new ItemNotFoundException(String.format("Item with id - %d not found in system with id - %d", itemId, systemId)));
        item.setName(request.getName());
        item.setIsPresent(request.getIsPresent());
        item.setGameSystem(system);
        itemRepository.save(item);
        return mapper.map(item, SystemItemDto.class);
    }

    @Transactional
    public SystemItemDto deleteItem(Integer userId, Integer itemId, Integer systemId) {
        SystemItem item = itemRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(itemId, systemId, userId).orElseThrow();
        itemRepository.delete(item);
        return mapper.map(item, SystemItemDto.class);
    }

}
