package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemParameter;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeParameterPutRequest;
import com.rpgstats.messages.CreateParameterPostRequest;
import com.rpgstats.messages.DTO.SystemParameterDto;
import com.rpgstats.repositories.SystemParameterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterService {
  SystemParameterRepository parameterRepository;
  GameSystemService gameSystemService;
  ModelMapper mapper;

  public ParameterService(
      SystemParameterRepository parameterRepository,
      GameSystemService gameSystemService,
      ModelMapper mapper) {
    this.parameterRepository = parameterRepository;
    this.gameSystemService = gameSystemService;
    this.mapper = mapper;
  }

  @Transactional
  public List<SystemParameterDto> getParametersDtoBySystem(Integer systemId) {
    return parameterRepository.findByGameSystem_Id(systemId).stream()
        .map(parameter -> mapper.map(parameter, SystemParameterDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemParameterDto getParameterDtoById(Integer systemId, Integer parameterId) {
    return mapper.map(
        parameterRepository
            .findByIdAndGameSystem_Id(parameterId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "No parameter with id - %d in system with id - %d",
                            parameterId, systemId))),
        SystemParameterDto.class);
  }

  @Transactional
  public SystemParameterDto createParameter(Integer systemId, CreateParameterPostRequest request) {
    GameSystem system = gameSystemService.getSystemById(systemId);
    SystemParameter parameter = new SystemParameter();
    parameter.setName(request.getName());
    parameter.setMaxValue(request.getMaxValue());
    parameter.setMinValue(request.getMinValue());
    parameter.setCreatedAt(Instant.now());
    parameter.setGameSystem(system);
    parameterRepository.save(parameter);
    return mapper.map(parameter, SystemParameterDto.class);
  }

  @Transactional
  public SystemParameterDto changeParameter(
      Integer parameterId, Integer systemId, ChangeParameterPutRequest request) {
    SystemParameter parameter =
        parameterRepository.findByIdAndGameSystem_Id(parameterId, systemId).orElseThrow();
    parameter.setName(request.getName());
    parameter.setMaxValue(request.getMaxValue());
    parameter.setMinValue(request.getMinValue());
    parameterRepository.save(parameter);
    return mapper.map(parameter, SystemParameterDto.class);
  }

  @Transactional
  public SystemParameterDto deleteParameter(Integer parameterId, Integer systemId) {
    SystemParameter parameter =
        parameterRepository
            .findByIdAndGameSystem_Id(parameterId, systemId)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format(
                            "Parameter not found by id - %d in system with id - %d",
                            parameterId, systemId)));
    parameterRepository.delete(parameter);
    return mapper.map(parameter, SystemParameterDto.class);
  }

  protected SystemParameter findByIdAndSystemId(Integer parameterId, Integer systemId) {
    return parameterRepository
        .findByIdAndGameSystem_Id(parameterId, systemId)
        .orElseThrow(
            () ->
                new ItemNotFoundException(
                    String.format(
                        "Parameter not found by id - %d in system with id - %d",
                        parameterId, systemId)));
  }
}
