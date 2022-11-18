package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeAttributePutRequest;
import com.rpgstats.messages.CreateAttributePostRequest;
import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.repositories.SystemAttributeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeService {

  SystemAttributeRepository attributeRepository;
  GameSystemService gameSystemService;
  ModelMapper mapper;

  public AttributeService(
      SystemAttributeRepository attributeRepository,
      GameSystemService gameSystemService,
      ModelMapper mapper) {
    this.attributeRepository = attributeRepository;
    this.gameSystemService = gameSystemService;
    this.mapper = mapper;
  }

  @Transactional
  public List<SystemAttributeDto> getAttributesBySystem(Integer systemId) {
    return attributeRepository.findByGameSystem_Id(systemId).stream()
        .map(attribute -> mapper.map(attribute, SystemAttributeDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemAttributeDto getAttributeDtoById(Integer systemId, Integer attributeId) {
    return mapper.map(
        attributeRepository
            .findByGameSystem_IdAndId(systemId, attributeId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Attribute with id - %d not found in system with id - %d",
                            attributeId, systemId))),
        SystemAttributeDto.class);
  }

  @Transactional
  public SystemAttributeDto createAttribute(Integer systemId, CreateAttributePostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemAttribute attribute = new SystemAttribute();
    attribute.setName(request.getName());
    attribute.setIsPresent(request.getIsPresent());
    attribute.setGameSystem(system);
    attributeRepository.save(attribute);
    return mapper.map(attribute, SystemAttributeDto.class);
  }

  @Transactional
  public SystemAttributeDto changeAttribute(
      Integer attributeId, Integer systemId, ChangeAttributePutRequest request) {
    SystemAttribute attribute = getAttributeById(attributeId, systemId);
    attribute.setName(request.getName());
    attribute.setIsPresent(request.getIsPresent());
    attributeRepository.save(attribute);
    return mapper.map(attribute, SystemAttributeDto.class);
  }

  @Transactional
  public SystemAttributeDto deleteAttribute(Integer attributeId, Integer systemId) {
    SystemAttribute attribute = getAttributeById(attributeId, systemId);
    attributeRepository.delete(attribute);
    return mapper.map(attribute, SystemAttributeDto.class);
  }

  @Transactional
  protected SystemAttribute getAttributeById(Integer attributeId, Integer systemId) {
    return attributeRepository
        .findByIdAndGameSystem_Id(attributeId, systemId)
        .orElseThrow(
            () -> new ItemNotFoundException(String.format("Attribute not found by id - %d", attributeId)));
  }
}
