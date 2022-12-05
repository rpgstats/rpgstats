package com.rpgstats.services;

import com.rpgstats.entity.ParameterModifier;
import com.rpgstats.entity.SystemParameter;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeParameterModifierPutRequest;
import com.rpgstats.messages.CreateParameterModifierPostRequest;
import com.rpgstats.messages.DTO.SystemParameterModifierDto;
import com.rpgstats.repositories.ParameterModifierRepository;
import com.rpgstats.repositories.SystemParameterRepository;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ModifierService {

  Logger log = LoggerFactory.getLogger(ModifierService.class);
  final GameSystemService gameSystemService;
  final ParameterModifierRepository parameterModifierRepository;
  final SystemParameterRepository systemParameterRepository;
  final SystemRepository systemRepository;

  final ParameterService parameterService;
  final ModelMapper mapper;

  public ModifierService(
      GameSystemService gameSystemService,
      ParameterModifierRepository parameterModifierRepository,
      SystemParameterRepository systemParameterRepository,
      SystemRepository systemRepository,
      ParameterService parameterService,
      ModelMapper mapper) {
    this.gameSystemService = gameSystemService;
    this.parameterModifierRepository = parameterModifierRepository;
    this.systemParameterRepository = systemParameterRepository;
    this.systemRepository = systemRepository;
    this.parameterService = parameterService;
    this.mapper = mapper;
  }

  @Transactional
  public List<SystemParameterModifierDto> getParameterModifiersDtoBySystem(Integer systemId) {
    return parameterModifierRepository.findByParameter_GameSystem_Id(systemId).stream()
        .map(parameterModifier -> mapper.map(parameterModifier, SystemParameterModifierDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemParameterModifierDto getParameterModifierDtoById(
      Integer systemId, Integer parameterModifierId) {
    return mapper.map(
        parameterModifierRepository.findByIdAndParameter_GameSystem_Id(
            systemId, parameterModifierId),
        SystemParameterModifierDto.class);
  }

  @Transactional
  public SystemParameterModifierDto createParameterModifier(
      Integer systemId, CreateParameterModifierPostRequest request) {
    if (parameterModifierRepository.existsByName(request.getName())) {
      throw new ConflictDataException("Modifier with that name already exists");
    }
    SystemParameter systemParameter =
        parameterService.findByIdAndSystemId(request.getParameterId(), systemId);
    ParameterModifier parameterModifier = new ParameterModifier();
    parameterModifier.setName(request.getName());
    parameterModifier.setValue(request.getValue());
    parameterModifier.setParameter(systemParameter);
    parameterModifierRepository.save(parameterModifier);
    return mapper.map(parameterModifier, SystemParameterModifierDto.class);
  }

  @Transactional
  public SystemParameterModifierDto changeParameterModifier(
      Integer parameterModifierId, Integer systemId, ChangeParameterModifierPutRequest request) {
    ParameterModifier parameterModifier =
        parameterModifierRepository
            .findById(parameterModifierId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format("No modifier with id - %d", parameterModifierId)));
    parameterModifier.setName(
        Objects.requireNonNullElse(request.getName(), parameterModifier.getName()));
    parameterModifier.setValue(
        Objects.requireNonNullElse(request.getValue(), parameterModifier.getValue()));
    if (request.getParameterId() != null) {
      SystemParameter newSystemParam =
          parameterService.findByIdAndSystemId(request.getParameterId(), systemId);
      parameterModifier.setParameter(newSystemParam);
    }
    parameterModifierRepository.save(parameterModifier);
    return mapper.map(parameterModifier, SystemParameterModifierDto.class);
  }

  @Transactional
  public SystemParameterModifierDto deleteParameterModifier(
      Integer parameterModifierId, Integer systemId) {
    ParameterModifier parameterModifier =
        parameterModifierRepository
            .findByIdAndParameter_GameSystem_Id(parameterModifierId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format("No modifier with id - %d", parameterModifierId)));
    parameterModifierRepository.delete(parameterModifier);
    return mapper.map(parameterModifier, SystemParameterModifierDto.class);
  }
}
