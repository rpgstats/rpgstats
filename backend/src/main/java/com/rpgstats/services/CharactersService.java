package com.rpgstats.services;

import com.rpgstats.entity.Character;
import com.rpgstats.entity.Session;
import com.rpgstats.entity.User;
import com.rpgstats.messages.ChangeCharacterPutRequest;
import com.rpgstats.messages.CreateCharacterPostRequest;
import com.rpgstats.messages.DTO.CharacterDto;
import com.rpgstats.repositories.CharacterRepository;
import com.rpgstats.repositories.SessionRepository;
import com.rpgstats.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class CharactersService {
  CharacterRepository characterRepository;
  SessionRepository sessionRepository;
  UserRepository userRepository;
  ModelMapper mapper;

  public CharactersService(
      CharacterRepository characterRepository,
      SessionRepository sessionRepository,
      ModelMapper mapper,
      UserRepository userRepository) {
    this.characterRepository = characterRepository;
    this.sessionRepository = sessionRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @Transactional
  public List<CharacterDto> getCharactersByUser(Integer userId) {
    return characterRepository.findByOwner_Id(userId).stream()
        .map(item -> mapper.map(item, CharacterDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public CharacterDto getCharacter(Integer userId, Integer characterId) {
    return mapper.map(
        characterRepository.findByOwner_IdAndId(userId, characterId), CharacterDto.class);
  }

  @Transactional
  public CharacterDto createCharacter(Integer userId, CreateCharacterPostRequest request) {
    Session session =
        sessionRepository.findByIdAndCreatorId_Id(request.getSessionId(), userId).orElseThrow();
    User user = userRepository.findById(userId).orElseThrow();
    Character character = new Character();
    character.setName(request.getName());
    character.setDescription(request.getDescription());
    character.setSession(session);
    character.setOwner(user);
    characterRepository.save(character);
    return mapper.map(character, CharacterDto.class);
  }

  @Transactional
  public CharacterDto copyCharacter(Integer userId, Integer characterId) {
    Character character = characterRepository.findById(characterId).orElseThrow();
    User user = userRepository.findById(userId).orElseThrow();
    Character copy = new Character();
    copy.setName(character.getName());
    copy.setSession(character.getSession());
    copy.setOwner(user);
    copy.setDescription(character.getDescription());
    characterRepository.save(copy);
    return mapper.map(copy, CharacterDto.class);
  }

  @Transactional
  public CharacterDto changeCharacter(
      Integer userId, Integer characterId, ChangeCharacterPutRequest request) {
    Character character =
        characterRepository.findByOwner_IdAndId(userId, characterId).orElseThrow();
    character.setName(request.getName());
    character.setDescription(request.getDescription());
    characterRepository.save(character);
    return mapper.map(character, CharacterDto.class);
  }

  @Transactional
  public CharacterDto deleteCharacter(Integer userId, Integer characterId) {
    Character character =
        characterRepository.findByOwner_IdAndId(userId, characterId).orElseThrow();
    characterRepository.delete(character);
    return mapper.map(character, CharacterDto.class);
  }
}
