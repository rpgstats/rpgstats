package com.rpgstats.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.ParameterModifier;
import com.rpgstats.messages.ChangeParameterModifierPutRequest;
import com.rpgstats.messages.CreateParameterModifierPostRequest;
import com.rpgstats.messages.SystemParameterModifierDto;
import com.rpgstats.repositories.ParameterModifierRepository;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterModifierService {// TODO: добавить exception

    Logger log = LoggerFactory.getLogger(ParameterModifierService.class);

    ParameterModifierRepository parameterModifierRepository;
    SystemRepository systemRepository;
    ModelMapper mapper;

    public ParameterModifierService(ParameterModifierRepository parameterModifierRepository, SystemRepository systemRepository, ModelMapper mapper) {
        this.parameterModifierRepository = parameterModifierRepository;
        this.systemRepository = systemRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<SystemParameterModifierDto> getParameterModifiersBySystem(Integer systemId) {
        return parameterModifierRepository.findByGameSystem_Id(systemId).stream().map(parameterModifier -> mapper.map(parameterModifier, SystemParameterModifierDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public SystemParameterModifierDto getParameterModifier(Integer systemId, Integer parameterModifierId) {
        return mapper.map(parameterModifierRepository.findByGameSystem_IdAndId(systemId, parameterModifierId), SystemParameterModifierDto.class);
    }

    @Transactional
    public SystemParameterModifierDto createParameterModifier(Integer userId, Integer systemId, CreateParameterModifierPostRequest request){
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow();
        ParameterModifier parameterModifier = new ParameterModifier();
        parameterModifier.setName(request.getName());
        parameterModifier.setValue(request.getValue());
        //parameterModifier.SystemParameter();
        parameterModifier.setGameSystem(system);
        parameterModifierRepository.save(parameterModifier);
        return mapper.map(parameterModifier, SystemParameterModifierDto.class);
    }

    @Transactional
    public SystemParameterModifierDto changeParameterModifier(Integer userId, Integer parameterModifierId, Integer systemId,  ChangeParameterModifierPutRequest request){
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow();
        ParameterModifier parameterModifier = parameterModifierRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(parameterModifierId, systemId, userId).orElseThrow();
        parameterModifier.setName(request.getName());
        parameterModifier.setValue(request.getValue());
        //parameterModifier.SystemParameter();
        parameterModifier.setGameSystem(system);
        parameterModifierRepository.save(parameterModifier);
        return mapper.map(parameterModifier, SystemParameterModifierDto.class);
    }

    @Transactional
    public SystemParameterModifierDto deleteParameterModifier(Integer userId, Integer parameterModifierId, Integer systemId){
        ParameterModifier parameterModifier = parameterModifierRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(parameterModifierId, systemId, userId).orElseThrow();
        parameterModifierRepository.delete(parameterModifier);
        return mapper.map(parameterModifier, SystemParameterModifierDto.class);
    }

}
