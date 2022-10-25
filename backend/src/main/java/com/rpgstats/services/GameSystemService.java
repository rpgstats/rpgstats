package com.rpgstats.services;

import com.rpgstats.model.GameSystemDto;
import com.rpgstats.model.GameSystemPostRequest;
import com.rpgstats.model.GameSystemPutRequest;
import com.rpgstats.repositories.SystemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GameSystemService {
    SystemRepository systemRepository;

    public GameSystemService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    @Transactional
    public List<GameSystemDto> getSystemsByName(String name) {
        return List.of();
    }

    @Transactional
    public List<GameSystemDto> getSystemsByOwnerId(Integer ownerId) {
        return List.of();
    }

    @Transactional
    public GameSystemDto getSystemById(Integer id) {
        return null;
    }

    @Transactional
    public GameSystemDto createSystem(Integer userId, GameSystemPostRequest postRequest) {
        return null;
    }

    @Transactional
    public GameSystemDto changeSystem(Integer userId, GameSystemPutRequest putRequest) {
        return null;
    }

    @Transactional
    public void deleteSystem(Integer userId, Integer systemId) {

    }
}
