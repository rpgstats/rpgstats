package com.rpgstats.services;

import com.rpgstats.entity.AttributeConstraint;
import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.entity.SystemTag;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeConstraintPutRequest;
import com.rpgstats.messages.CreateConstraintPostRequest;
import com.rpgstats.messages.DTO.AttributeConstraintDto;
import com.rpgstats.repositories.AttributeConstraintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConstraintService {
  ModelMapper modelMapper;
  AttributeConstraintRepository attributeConstraintRepository;
  AttributeService attributeService;

  GameSystemService gameSystemService;

  TagService tagService;
  UserService userService;

  public ConstraintService(
      ModelMapper modelMapper,
      AttributeConstraintRepository attributeConstraintRepository,
      AttributeService attributeService,
      GameSystemService gameSystemService,
      TagService tagService,
      UserService userService) {
    this.modelMapper = modelMapper;
    this.attributeConstraintRepository = attributeConstraintRepository;
    this.attributeService = attributeService;
    this.gameSystemService = gameSystemService;
    this.tagService = tagService;
    this.userService = userService;
  }

  @Transactional
  public List<AttributeConstraintDto> getConstraintsBySystem(Integer systemId) {
    return attributeConstraintRepository.findByAttribute_GameSystem_Id(systemId).stream()
        .map(u -> modelMapper.map(u, AttributeConstraintDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public AttributeConstraintDto getConstraint(Integer systemId, Integer constraintId) {
    return modelMapper.map(
        attributeConstraintRepository
            .findByAttribute_GameSystem_IdAndId(systemId, constraintId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Constaint with id - %d in system with id - %d not found",
                            constraintId, systemId))),
        AttributeConstraintDto.class);
  }

  @Transactional
  public AttributeConstraintDto createConstraint(
      Integer systemId, CreateConstraintPostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemAttribute systemAttribute = attributeService.getAttributeById(request.getAttributeId());
    SystemTag systemTag = tagService.getTagById(request.getTagId());
    AttributeConstraint attributeConstraint;
    if (Objects.equals(system.getId(), systemAttribute.getGameSystem().getId())
        && Objects.equals(system.getId(), systemTag.getGameSystem().getId())) {
      attributeConstraint = new AttributeConstraint();
      attributeConstraint.setAttribute(systemAttribute);
      attributeConstraint.setTag(systemTag);
      attributeConstraintRepository.save(attributeConstraint);
    } else {
      throw new ConflictDataException("Wrong system -> attribute and system -> tag relation");
    }
    return modelMapper.map(attributeConstraint, AttributeConstraintDto.class);
  }

  @Transactional
  public AttributeConstraintDto changeConstraint(
      Integer constraintId, Integer systemId, ChangeConstraintPutRequest request) {
    AttributeConstraint attributeConstraint =
        attributeConstraintRepository
            .findById(constraintId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format("No constraint with id - %d", constraintId)));
    GameSystem system = gameSystemService.getSystemById(systemId);
    if (attributeConstraint.getAttribute().getGameSystem().getId() != system.getId()) {
      throw new ItemNotFoundException(
          String.format("No constraint with id -%d in system with id -%d", constraintId, systemId));
    }
    if (request.getAttributeId() != null && request.getTagId() != null) {
      SystemAttribute systemAttribute = attributeService.getAttributeById(request.getAttributeId());
      SystemTag systemTag = tagService.getTagById(request.getTagId());
      if (Objects.equals(system.getId(), systemAttribute.getGameSystem().getId())
          && system.getId() == systemTag.getGameSystem().getId()) {
        attributeConstraint.setAttribute(systemAttribute);
        attributeConstraint.setTag(systemTag);
      } else {
        throw new ConflictDataException("Wrong system -> attribute and system -> tag relation");
      }
    } else if (request.getAttributeId() != null) {
      SystemAttribute systemAttribute = attributeService.getAttributeById(request.getAttributeId());
      SystemTag systemTag = attributeConstraint.getTag();
      if (Objects.equals(
          systemTag.getGameSystem().getId(), systemAttribute.getGameSystem().getId())) {
        attributeConstraint.setAttribute(systemAttribute);
      } else {
        throw new ConflictDataException("Wrong system -> attribute and system -> tag relation");
      }
    } else if (request.getTagId() != null) {
      SystemAttribute systemAttribute = attributeConstraint.getAttribute();
      SystemTag systemTag = tagService.getTagById(request.getTagId());
      if (Objects.equals(
          systemTag.getGameSystem().getId(), systemAttribute.getGameSystem().getId())) {
        attributeConstraint.setTag(systemTag);
      } else {
        throw new ConflictDataException("Wrong system -> attribute and system -> tag relation");
      }
    }
    if (request.getHasTag() != null) {
      attributeConstraint.setHasTag(request.getHasTag());
    }
    attributeConstraintRepository.save(attributeConstraint);
    return modelMapper.map(attributeConstraint, AttributeConstraintDto.class);
  }

  @Transactional
  public void deleteConstraint(Integer constraintId, Integer systemId) {
    AttributeConstraint attributeConstraint =
        attributeConstraintRepository
            .findById(constraintId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Not constraint with id - %d in system - %d", constraintId, systemId)));
    attributeConstraintRepository.delete(attributeConstraint);
  }
}
