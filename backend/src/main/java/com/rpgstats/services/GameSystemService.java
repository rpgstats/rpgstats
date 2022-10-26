package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.messages.GameSystemDto;
import com.rpgstats.messages.GameSystemPostRequest;
import com.rpgstats.messages.GameSystemPutRequest;
import com.rpgstats.messages.ItemNotFoundException;
import com.rpgstats.repositories.SystemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameSystemService {
    SystemRepository systemRepository;

    UserService userService;
    ModelMapper modelMapper;

    public GameSystemService(SystemRepository systemRepository, UserService userService, ModelMapper modelMapper) {
        this.systemRepository = systemRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<GameSystemDto> getSystemsByName(String name) {
        return systemRepository.findByNameLikeIgnoreCase(name).stream().map(u -> modelMapper.map(u, GameSystemDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public List<GameSystemDto> getSystemsByOwnerId(Integer ownerId) {
        return systemRepository.findByOwner_Id(ownerId).stream().map(u -> modelMapper.map(u, GameSystemDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public GameSystemDto getSystemById(Integer id) {
        return modelMapper.map(systemRepository.findById(id).orElseThrow(), GameSystemDto.class);
    }

    @Transactional
    public GameSystemDto createSystem(Integer userId, GameSystemPostRequest postRequest) {
        GameSystem gameSystem = modelMapper.map(postRequest, GameSystem.class);
        gameSystem.setCreatedAt(Instant.now());
        gameSystem.setParentGameSystem(systemRepository.findById(postRequest.getParentSystem()).orElseThrow(() -> new ItemNotFoundException("Parent system not found with id - " + postRequest.getParentSystem())));
        gameSystem.setOwner(userService.getUserById(userId));
        systemRepository.save(gameSystem);
        return modelMapper.map(gameSystem, GameSystemDto.class);
    }

    @Transactional
    public GameSystemDto changeSystem(Integer userId, Integer systemId, GameSystemPutRequest putRequest) {
        GameSystem gameSystem = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow(() -> new ItemNotFoundException("System not found by id - " + systemId));
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
    public void deleteSystem(Integer userId, Integer systemId) {
        GameSystem gameSystem = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow(() -> new ItemNotFoundException("System not found by id - " + systemId));
        systemRepository.delete(gameSystem);

    }
}
