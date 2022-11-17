package com.rpgstats.services;

import com.rpgstats.entity.Character;
import com.rpgstats.entity.*;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.CharacterSlotDto;
import com.rpgstats.messages.DTO.SystemTagDto;
import com.rpgstats.messages.DTO.UserCharacterDto;
import com.rpgstats.repositories.CharacterRepository;
import com.rpgstats.repositories.CharacterSlotRepository;
import com.rpgstats.repositories.CharacterSlotsTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

  CharacterRepository characterRepository;

  CharacterSlotRepository characterSlotRepository;

  SessionService sessionService;

  ItemService itemService;
  ModelMapper modelMapper;

  TagService tagService;

  CharacterSlotsTagRepository characterSlotsTagRepository;

  public CharacterService(
      CharacterRepository characterRepository,
      CharacterSlotRepository characterSlotRepository,
      SessionService sessionService,
      ItemService itemService,
      ModelMapper modelMapper,
      TagService tagService,
      CharacterSlotsTagRepository characterSlotsTagRepository) {
    this.characterRepository = characterRepository;
    this.characterSlotRepository = characterSlotRepository;
    this.sessionService = sessionService;
    this.itemService = itemService;
    this.modelMapper = modelMapper;
    this.tagService = tagService;
    this.characterSlotsTagRepository = characterSlotsTagRepository;
  }

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
  public UserCharacterDto copyUserCharacter(User user, Integer id) {
    Character character = getUserCharacterById(user.getId(), id);
    Character copyChar = new Character();
    copyChar.setUser(user);
    copyChar.setDescription(character.getDescription());
    copyChar.setName(character.getName() + "copy");
    copyChar.setSession(character.getSession());
    copyChar.setSystem(character.getSystem());
    characterRepository.save(copyChar);
    return modelMapper.map(copyChar, UserCharacterDto.class);
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

  @Transactional
  public CharacterSlot getCharacterSlotById(Integer characterId, Integer slotId) {
    return characterSlotRepository
        .findByIdAndCharacter_Id(slotId, characterId)
        .orElseThrow(() -> new ItemNotFoundException("Not found"));
  }

  @Transactional
  public List<CharacterSlotDto> getCharacterSlotsDto(User user, Integer characterId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    return characterSlotRepository.findByCharacter_Id(character.getId()).stream()
        .map(x -> modelMapper.map(x, CharacterSlotDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public CharacterSlotDto createCharacterSlot(
      User user,
      Integer characterId,
      CreateCharacterSlotPostRequest createCharacterSlotPostRequest) {
    Character character = getUserCharacterById(user.getId(), characterId);
    SystemItem item =
        itemService.getItemById(
            character.getSystem().getId(), createCharacterSlotPostRequest.getItemId());
    CharacterSlot slot = new CharacterSlot();
    slot.setCharacter(character);
    slot.setName(createCharacterSlotPostRequest.getName());
    slot.setItem(item);
    slot.setIconUrl(createCharacterSlotPostRequest.getIconUrl());
    slot.setIsWhitelisted(createCharacterSlotPostRequest.getIsWhitelisted());
    characterSlotRepository.save(slot);
    return modelMapper.map(slot, CharacterSlotDto.class);
  }

  @Transactional
  public CharacterSlotDto getSlotDtoById(User user, Integer characterId, Integer slotId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterSlot slot = getCharacterSlotById(character.getId(), slotId);
    return modelMapper.map(slot, CharacterSlotDto.class);
  }

  @Transactional
  public CharacterSlotDto updateSlot(
      User user,
      Integer characterId,
      Integer slotId,
      UpdateCharacterSlotPutRequest updateCharacterSlotPutRequest) {
    Character character = getUserCharacterById(user.getId(), characterId);
    SystemItem item =
        itemService.getItemById(
            character.getSystem().getId(), updateCharacterSlotPutRequest.getItemId());
    CharacterSlot slot = getCharacterSlotById(characterId, slotId);
    slot.setName(updateCharacterSlotPutRequest.getName());
    slot.setIsWhitelisted(updateCharacterSlotPutRequest.getIsWhitelisted());
    slot.setIconUrl(updateCharacterSlotPutRequest.getIconUrl());
    slot.setItem(item);
    characterSlotRepository.save(slot);
    return modelMapper.map(slot, CharacterSlotDto.class);
  }

  @Transactional
  public void deleteSlot(User user, Integer characterId, Integer slotId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterSlot slot = getCharacterSlotById(character.getId(), slotId);
    characterSlotRepository.delete(slot);
  }

  @Transactional
  public List<SystemTagDto> getSlotTagsDto(User user, Integer characterId, Integer slotId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterSlot slot = getCharacterSlotById(character.getId(), slotId);
    return characterSlotsTagRepository.findById_SlotId(slot.getId()).stream()
        .map(x -> x.getTag())
        .map(x -> modelMapper.map(x, SystemTagDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemTagDto addCharacterSlotTag(
      User user, AddSlotTagPostRequest addSlotTagPostRequest, Integer characterId, Integer slotId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterSlot slot = getCharacterSlotById(character.getId(), slotId);
    SystemTag systemTag =
        tagService.getTagById(character.getSystem().getId(), addSlotTagPostRequest.getId());
    CharacterSlotsTag characterSlotTag = new CharacterSlotsTag();
    characterSlotTag.setTag(systemTag);
    characterSlotTag.setSlot(slot);
    characterSlotsTagRepository.save(characterSlotTag);
    return modelMapper.map(systemTag, SystemTagDto.class);
  }
}
