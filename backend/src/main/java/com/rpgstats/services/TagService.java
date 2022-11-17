package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemTag;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeTagPutRequest;
import com.rpgstats.messages.CreateTagPostRequest;
import com.rpgstats.messages.DTO.SystemTagDto;
import com.rpgstats.repositories.SystemTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
  SystemTagRepository tagRepository;
  GameSystemService gameSystemService;
  ModelMapper mapper;

  public TagService(
      SystemTagRepository tagRepository, GameSystemService gameSystemService, ModelMapper mapper) {
    this.tagRepository = tagRepository;
    this.gameSystemService = gameSystemService;
    this.mapper = mapper;
  }

  @Transactional
  public List<SystemTagDto> getTagsBySystem(Integer systemId) {
    return tagRepository.findByGameSystem_Id(systemId).stream()
        .map(tag -> mapper.map(tag, SystemTagDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemTagDto getTagDtoById(Integer systemId, Integer tagId) {
    return mapper.map(getTagById(systemId, tagId), SystemTagDto.class);
  }

  @Transactional
  public SystemTagDto createTag(Integer systemId, CreateTagPostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemTag tag = new SystemTag();
    tag.setName(request.getName());
    tag.setGameSystem(system);
    tagRepository.save(tag);
    return mapper.map(tag, SystemTagDto.class);
  }

  @Transactional
  public SystemTagDto changeTag(Integer tagId, Integer systemId, ChangeTagPutRequest request) {
    SystemTag tag = getTagById(systemId, tagId);
    tag.setName(request.getName());
    tagRepository.save(tag);
    return mapper.map(tag, SystemTagDto.class);
  }

  @Transactional
  public SystemTagDto deleteTag(Integer tagId, Integer systemId) {
    SystemTag tag = getTagById(systemId, tagId);
    tagRepository.delete(tag);
    return mapper.map(tag, SystemTagDto.class);
  }

  @Transactional
  public SystemTag getTagById(Integer systemId, Integer id) {
    return tagRepository
        .findByIdAndGameSystem_Id(id, systemId)
        .orElseThrow(
            () -> new ItemNotFoundException(String.format("Tag by id - %d not found", id)));
  }
}
