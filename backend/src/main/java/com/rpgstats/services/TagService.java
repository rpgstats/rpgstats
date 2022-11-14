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
  public SystemTagDto getTag(Integer systemId, Integer tagId) {
    return mapper.map(
        tagRepository
            .findByIdAndGameSystem_Id(tagId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Tag with id - %d not found in system with id - %d", tagId, systemId))),
        SystemTagDto.class);
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
    SystemTag tag = tagRepository.findByIdAndGameSystem_Id(tagId, systemId).orElseThrow();
    tag.setName(request.getName());
    tagRepository.save(tag);
    return mapper.map(tag, SystemTagDto.class);
  }

  @Transactional
  public SystemTagDto deleteTag(Integer tagId, Integer systemId) {
    SystemTag tag =
        tagRepository
            .findByIdAndGameSystem_Id(tagId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Tag with id - %d not found in system with id - %d", tagId, systemId)));
    tagRepository.delete(tag);
    return mapper.map(tag, SystemTagDto.class);
  }

  @Transactional
  protected SystemTag getTagById(Integer id) {
    return tagRepository
        .findById(id)
        .orElseThrow(
            () -> new ItemNotFoundException(String.format("Tag by id - %d not found", id)));
  }
}
