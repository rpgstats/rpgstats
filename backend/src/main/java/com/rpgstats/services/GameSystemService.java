package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.DTO.GameSystemDto;
import com.rpgstats.messages.GameSystemPostRequest;
import com.rpgstats.messages.GameSystemPutRequest;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameSystemService {
  SystemRepository systemRepository;

  UserService userService;
  ModelMapper modelMapper;

  public GameSystemService(
      SystemRepository systemRepository, UserService userService, ModelMapper modelMapper) {
    this.systemRepository = systemRepository;
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Transactional
  public List<GameSystemDto> getSystemsByName(String name) {
    return systemRepository.findByNameLikeIgnoreCase(name).stream()
        .map(u -> modelMapper.map(u, GameSystemDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public GameSystemDto getSystemDtoById(Integer id) {
    return modelMapper.map(getSystemById(id), GameSystemDto.class);
  }

  @Transactional
  public GameSystemDto createSystem(Integer userId, GameSystemPostRequest postRequest) {
    if (systemRepository.existsByName(postRequest.getName())) {
      throw new ConflictDataException("System with that name already exists");
    }
    GameSystem gameSystem = modelMapper.map(postRequest, GameSystem.class);
    gameSystem.setCreatedAt(Instant.now());
    if (postRequest.getParentSystem() != null) {
      gameSystem.setParentGameSystem(
          systemRepository
              .findById(postRequest.getParentSystem())
              .orElseThrow(
                  () ->
                      new ItemNotFoundException(
                          "Parent system not found with id - " + postRequest.getParentSystem())));
    }
    gameSystem.setOwner(userService.getUserById(userId));
    systemRepository.save(gameSystem);
    return modelMapper.map(gameSystem, GameSystemDto.class);
  }

  @Transactional
  public GameSystemDto changeSystem(Integer systemId, GameSystemPutRequest putRequest) {
    GameSystem gameSystem =
        systemRepository
            .findById(systemId)
            .orElseThrow(
                () -> new ItemNotFoundException("System not found by id - %d".formatted(systemId)));
    if (putRequest.getName() != null) {
      gameSystem.setName(putRequest.getName());
    }
    if (putRequest.getDescription() != null) {
      gameSystem.setDescription(putRequest.getDescription());
    }
    systemRepository.save(gameSystem);
    return modelMapper.map(gameSystem, GameSystemDto.class);
  }

  @Transactional
  public void deleteSystem(Integer systemId) {
    GameSystem gameSystem =
        systemRepository
            .findById(systemId)
            .orElseThrow(() -> new ItemNotFoundException("System not found by id - " + systemId));
    systemRepository.delete(gameSystem);
  }

  protected Optional<GameSystem> findByIdAndOwnerId(Integer id, Integer ownerId) {
    return systemRepository.findByIdAndOwner_Id(id, ownerId);
  }

  @Transactional
  protected GameSystem getSystemById(Integer id) {
    return systemRepository
        .findById(id)
        .orElseThrow(() -> new ItemNotFoundException("System not found by id - " + id));
  }

  @Transactional
  public boolean existById(int systemId) {
    return systemRepository.existsById(systemId);
  }

  @Transactional
  public boolean existByIdAndOwnerId(Integer id, Integer ownerId) {
    return systemRepository.existsByIdAndOwner_Id(id, ownerId);
  }
}
