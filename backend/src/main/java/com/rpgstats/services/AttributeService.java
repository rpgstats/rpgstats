package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemAttribute;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.ChangeAttributePutRequest;
import com.rpgstats.messages.CreateAttributePostRequest;
import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.repositories.SystemAttributeRepository;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeService {

    SystemAttributeRepository attributeRepository;
    SystemRepository systemRepository;
    ModelMapper mapper;

    public AttributeService(SystemAttributeRepository attributeRepository, SystemRepository systemRepository, ModelMapper mapper) {
        this.attributeRepository = attributeRepository;
        this.systemRepository = systemRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<SystemAttributeDto> getAttributesBySystem(Integer systemId) {
        return attributeRepository.findByGameSystem_Id(systemId).stream().map(attribute -> mapper.map(attribute, SystemAttributeDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public SystemAttributeDto getAttribute(Integer systemId, Integer attributeId) {
        return mapper.map(attributeRepository.findByGameSystem_IdAndId(systemId, attributeId).
                        orElseThrow(() -> new ItemNotFoundException(String.format("Attribute with id - %d not found in system with id - %d", attributeId, systemId)))
                , SystemAttributeDto.class);
    }

    @Transactional
    public SystemAttributeDto createAttribute(Integer userId, Integer systemId, CreateAttributePostRequest request) {
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).
                orElseThrow(() -> new ItemNotFoundException(String.format("System not found by id - %d", systemId)));
        SystemAttribute attribute = new SystemAttribute();
        attribute.setName(request.getName());
        attribute.setIsPresent(request.getIsPresent());
        attribute.setGameSystem(system);
        attributeRepository.save(attribute);
        return mapper.map(attribute, SystemAttributeDto.class);
    }

    @Transactional
    public SystemAttributeDto changeAttribute(Integer userId, Integer attributeId, Integer systemId, ChangeAttributePutRequest request) {
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).
                orElseThrow(() -> new ItemNotFoundException(String.format("Attribute with id - %d not found in system with id - %d", attributeId, systemId)));
        SystemAttribute attribute = attributeRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(attributeId, systemId, userId).orElseThrow();
        attribute.setName(request.getName());
        attribute.setIsPresent(request.getIsPresent());
        attribute.setGameSystem(system);
        attributeRepository.save(attribute);
        return mapper.map(attribute, SystemAttributeDto.class);
    }

    @Transactional
    public SystemAttributeDto deleteAttribute(Integer userId, Integer attributeId, Integer systemId) {
        SystemAttribute attribute = attributeRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(attributeId, systemId, userId).
                orElseThrow(() -> new ItemNotFoundException(String.format("Attribute with id - %d not found in system with id - %d", attributeId, systemId)));
        attributeRepository.delete(attribute);
        return mapper.map(attribute, SystemAttributeDto.class);
    }

    protected SystemAttribute getAttributeById(Integer id) {
        return attributeRepository.findById(id).orElseThrow(()->new ItemNotFoundException(String.format("Attribute not found by id - %d", id)));
    }

}
