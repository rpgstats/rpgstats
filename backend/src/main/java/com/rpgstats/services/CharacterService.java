package com.rpgstats.services;

import com.rpgstats.entity.Character;
import com.rpgstats.entity.Session;
import com.rpgstats.entity.User;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.CreateCharacterPostRequest;
import com.rpgstats.messages.DTO.UserCharacterDto;
import com.rpgstats.messages.UpdateCharacterPutRequest;
import com.rpgstats.repositories.CharacterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

  CharacterRepository characterRepository;

  SessionService sessionService;
  ModelMapper modelMapper;

  @Transactional
  public List<UserCharacterDto> getUserCharacters(User user) {
    return characterRepository.findByUser_Id(user.getId()).stream()
        .map(x -> modelMapper.map(x, UserCharacterDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public UserCharacterDto createUserCharacter(
      User user, CreateCharacterPostRequest createCharacterPostRequest) {
    Session session =
        sessionService.getUserSessionByIdAndUserId(
            createCharacterPostRequest.getSessionId(), user.getId());
    if (characterRepository.existsBySession_IdAndName(
        session.getId(), createCharacterPostRequest.getName())) {
      throw new ConflictDataException("Name already in use");
    }
    Character character = new Character();
    character.setUser(user);
    character.setSession(session);
    character.setDescription(createCharacterPostRequest.getDescription());
    character.setName(createCharacterPostRequest.getName());
    characterRepository.save(character);
    return modelMapper.map(character, UserCharacterDto.class);
  }

  @Transactional
  public UserCharacterDto getUserCharacterDTObyId(User user, Integer id) {
    return modelMapper.map(getUserCharacterById(user.getId(), id), UserCharacterDto.class);
  }

  @Transactional
  public UserCharacterDto updateUserCharacter(
      User user, Integer id, UpdateCharacterPutRequest updateCharacterPutRequest) {
    Character character = getUserCharacterById(user.getId(), id);
    if (characterRepository.existsBySession_IdAndName(
        character.getSession().getId(), updateCharacterPutRequest.getName())) {
      throw new ConflictDataException("Name already in use");
    }
    character.setName(updateCharacterPutRequest.getName());
    character.setDescription(updateCharacterPutRequest.getDescription());
    return modelMapper.map(character, UserCharacterDto.class);
  }

  @Transactional
  public void deleteUserCharacter(User user, Integer id) {
    characterRepository.delete(getUserCharacterById(user.getId(), id));
  }

  @Transactional
  public Character getUserCharacterById(Integer userId, Integer id) {
    return characterRepository
        .findByIdAndUser_Id(id, userId)
        .orElseThrow(() -> new ItemNotFoundException("Not found"));
  }
}
