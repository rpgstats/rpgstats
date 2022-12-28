package com.rpgstats.services;

import com.rpgstats.entity.Character;
import com.rpgstats.entity.*;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.CharacterSlotDto;
import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.messages.DTO.SystemTagDto;
import com.rpgstats.messages.DTO.UserCharacterDto;
import com.rpgstats.repositories.CharacterAttributeRepository;
import com.rpgstats.repositories.CharacterRepository;
import com.rpgstats.repositories.CharacterSlotRepository;
import com.rpgstats.repositories.CharacterSlotsTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

  CharacterRepository characterRepository;

  CharacterSlotRepository characterSlotRepository;

  SessionService sessionService;

  GameSystemService gameSystemService;

  ItemService itemService;
  ModelMapper modelMapper;

  TagService tagService;

  CharacterSlotsTagRepository characterSlotsTagRepository;

  CharacterAttributeRepository characterAttributeRepository;

  AttributeService attributeService;

  @Autowired
  public void setCharacterRepository(CharacterRepository characterRepository) {
    this.characterRepository = characterRepository;
  }

  @Autowired
  public void setCharacterSlotRepository(CharacterSlotRepository characterSlotRepository) {
    this.characterSlotRepository = characterSlotRepository;
  }

  @Autowired
  public void setSessionService(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @Autowired
  public void setGameSystemService(GameSystemService gameSystemService) {
    this.gameSystemService = gameSystemService;
  }

  @Autowired
  public void setItemService(ItemService itemService) {
    this.itemService = itemService;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setTagService(TagService tagService) {
    this.tagService = tagService;
  }

  @Autowired
  public void setCharacterSlotsTagRepository(
      CharacterSlotsTagRepository characterSlotsTagRepository) {
    this.characterSlotsTagRepository = characterSlotsTagRepository;
  }

  @Autowired
  public void setCharacterAttributeRepository(
      CharacterAttributeRepository characterAttributeRepository) {
    this.characterAttributeRepository = characterAttributeRepository;
  }

  @Autowired
  public void setAttributeService(AttributeService attributeService) {
    this.attributeService = attributeService;
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

    GameSystem system = gameSystemService.getSystemById(createCharacterPostRequest.getSystemId());

    if (characterRepository.existsBySession_IdAndName(
        session.getId(), createCharacterPostRequest.getName())) {
      throw new ConflictDataException("Name already in use");
    }
    Character character = new Character();
    character.setUser(user);
    character.setSession(session);
    character.setDescription(createCharacterPostRequest.getDescription());
    character.setName(createCharacterPostRequest.getName());
    character.setSystem(system);
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
        tagService.getTagById(addSlotTagPostRequest.getId(), character.getSystem().getId());
    CharacterSlotsTag characterSlotTag = new CharacterSlotsTag();
    characterSlotTag.setTag(systemTag);
    characterSlotTag.setSlot(slot);
    characterSlotsTagRepository.save(characterSlotTag);
    return modelMapper.map(systemTag, SystemTagDto.class);
  }

  @Transactional
  public void deleteCharacterSlotTag(
      User user, Integer characterId, Integer slotId, Integer tagId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterSlot slot = getCharacterSlotById(character.getId(), slotId);
    CharacterSlotsTag characterSlotsTag =
        characterSlotsTagRepository.findById_SlotIdAndId_TagId(slot.getId(), tagId);
    characterSlotsTagRepository.delete(characterSlotsTag);
  }

  @Transactional
  public List<SystemAttributeDto> getCharacterAttributes(User user, Integer characterId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    return characterAttributeRepository.findById_CharacterId(character.getId()).stream()
        .map(x -> modelMapper.map(x.getAttribute(), SystemAttributeDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SystemAttributeDto addCharacterAttribute(
      User user,
      AddCharacterAttributePostRequest addCharacterAttributePostRequest,
      Integer characterId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    SystemAttribute attribute =
        attributeService.getAttributeById(
            addCharacterAttributePostRequest.getId(), character.getSystem().getId());
    CharacterAttribute characterAttribute = new CharacterAttribute();
    characterAttribute.setAttribute(attribute);
    characterAttribute.setCharacter(character);
    characterAttributeRepository.save(characterAttribute);
    return modelMapper.map(attribute, SystemAttributeDto.class);
  }

  @Transactional
  public void deleteCharacterAttribute(User user, Integer characterId, Integer attributeId) {
    Character character = getUserCharacterById(user.getId(), characterId);
    CharacterAttribute characterAttribute =
        characterAttributeRepository.findById_CharacterIdAndId_AttributeId(
            character.getId(), attributeId);
    characterAttributeRepository.delete(characterAttribute);
  }

  @Transactional
  public List<Character> findSessionCharacters(Integer sessionId) {
    return characterRepository.findBySession_Id(sessionId);
  }

  @Transactional
  public Character getSessionCharacter(Integer sessionId, Integer characterId) {
    return characterRepository
        .findByIdAndSession_Id(characterId, sessionId)
        .orElseThrow(() -> new ItemNotFoundException("Not found"));
  }

  @Transactional
  public void leaveSessionUserCharacter(User user, Integer sessionId, Integer characterId) {
    Character character =
        characterRepository
            .findByIdAndSession_IdAndUser_Id(characterId, sessionId, user.getId())
            .orElseThrow(() -> new ItemNotFoundException("Not found"));
    character.setSession(null);
  }
}
