package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemItem;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeItemPutRequest;
import com.rpgstats.messages.CreateItemPostRequest;
import com.rpgstats.messages.DTO.SystemItemDto;
import com.rpgstats.repositories.SystemItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemService {


  SystemItemRepository itemRepository;

  GameSystemService gameSystemService;
  ModelMapper mapper;

  public ItemService(SystemItemRepository itemRepository, GameSystemService gameSystemService, ModelMapper mapper) {
    this.itemRepository = itemRepository;
    this.gameSystemService = gameSystemService;
    this.mapper = mapper;
  }

  @Transactional
  public List<SystemItemDto> getItemsDtoBySystem(Integer systemId) {
    return itemRepository.findByGameSystem_Id(systemId).stream()
        .map(item -> mapper.map(item, SystemItemDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemItemDto getItemDtoById(Integer systemId, Integer itemId) {
    return mapper.map(
        itemRepository
            .findByIdAndGameSystem_Id(systemId, itemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Item with id - %d not found in system with id - %d",
                            itemId, systemId))),
        SystemItemDto.class);
  }

  @Transactional
  public SystemItemDto createItem(Integer systemId, CreateItemPostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemItem item = new SystemItem();
    item.setName(request.getName());
    item.setIsPresent(request.getIsPresent());
    item.setGameSystem(system);
    itemRepository.save(item);
    return mapper.map(item, SystemItemDto.class);
  }

  @Transactional
  public SystemItemDto changeItem(Integer itemId, Integer systemId, ChangeItemPutRequest request) {
    SystemItem item =
        itemRepository
            .findByIdAndGameSystem_Id(itemId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Item with id - %d not found in system with id - %d",
                            itemId, systemId)));
    item.setName(Objects.requireNonNullElse(request.getName(),item.getName()));
    item.setIsPresent(Objects.requireNonNullElse(request.getIsPresent(),item.getIsPresent()));
    itemRepository.save(item);
    return mapper.map(item, SystemItemDto.class);
  }

  @Transactional
  public SystemItemDto deleteItem(Integer itemId, Integer systemId) {
    SystemItem item =
        itemRepository
            .findByIdAndGameSystem_Id(itemId, systemId)
            .orElseThrow();
    itemRepository.delete(item);
    return mapper.map(item, SystemItemDto.class);
  }
}
