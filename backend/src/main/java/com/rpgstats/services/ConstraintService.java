package com.rpgstats.services;

import com.rpgstats.entity.AttributeConstraint;
import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.entity.SystemTag;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeConstraintPutRequest;
import com.rpgstats.messages.CreateConstraintPostRequest;
import com.rpgstats.messages.DTO.AttributeConstraintDto;
import com.rpgstats.repositories.AttributeConstraintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstraintService {
  final ModelMapper modelMapper;
  final AttributeConstraintRepository attributeConstraintRepository;
  final AttributeService attributeService;

  final GameSystemService gameSystemService;

  final TagService tagService;
  final UserService userService;

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
  public AttributeConstraintDto getConstraintDtoById(Integer systemId, Integer constraintId) {
    return modelMapper.map(
        getAttributeConstraint(constraintId, systemId), AttributeConstraintDto.class);
  }

  @Transactional
  public AttributeConstraintDto createConstraint(
      Integer systemId, CreateConstraintPostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemAttribute systemAttribute =
        attributeService.getAttributeById(request.getAttributeId(), systemId);
    SystemTag systemTag = tagService.getTagById(request.getTagId(), systemId);
    AttributeConstraint attributeConstraint;
    attributeConstraint = new AttributeConstraint();
    attributeConstraint.setAttribute(systemAttribute);
    attributeConstraint.setTag(systemTag);
    attributeConstraintRepository.save(attributeConstraint);

    return modelMapper.map(attributeConstraint, AttributeConstraintDto.class);
  }

  @Transactional
  public AttributeConstraintDto changeConstraint(
      Integer constraintId, Integer systemId, ChangeConstraintPutRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    AttributeConstraint attributeConstraint = getAttributeConstraint(constraintId, systemId);
    if (request.getAttributeId() != null && request.getTagId() != null) {
      SystemAttribute systemAttribute =
          attributeService.getAttributeById(request.getAttributeId(), systemId);
      SystemTag systemTag = tagService.getTagById(request.getTagId(), systemId);
      attributeConstraint.setAttribute(systemAttribute);
      attributeConstraint.setTag(systemTag);
    } else if (request.getAttributeId() != null) {
      SystemAttribute systemAttribute =
          attributeService.getAttributeById(request.getAttributeId(), systemId);
      attributeConstraint.setAttribute(systemAttribute);
    } else if (request.getTagId() != null) {
      SystemTag systemTag = tagService.getTagById(request.getTagId(), systemId);
      attributeConstraint.setTag(systemTag);
    }
    if (request.getHasTag() != null) {
      attributeConstraint.setHasTag(request.getHasTag());
    }
    attributeConstraintRepository.save(attributeConstraint);
    return modelMapper.map(attributeConstraint, AttributeConstraintDto.class);
  }

  @Transactional
  public void deleteConstraint(Integer constraintId, Integer systemId) {
    AttributeConstraint attributeConstraint = getAttributeConstraint(constraintId, systemId);
    attributeConstraintRepository.delete(attributeConstraint);
  }

  public AttributeConstraint getAttributeConstraint(Integer constraintId, Integer systemId) {
    return attributeConstraintRepository
        .findByAttribute_GameSystem_IdAndId(systemId, constraintId)
        .orElseThrow(
            () ->
                new ItemNotFoundException(
                    String.format("No constraint with id - %d", constraintId)));
  }
}
