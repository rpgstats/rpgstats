package com.rpgstats.services;

import com.rpgstats.entity.*;
import com.rpgstats.exceptions.ConflictDataException;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.messages.CreateSessionPostRequest;
import com.rpgstats.messages.DTO.CharacterDto;
import com.rpgstats.messages.DTO.SessionDto;
import com.rpgstats.repositories.SessionRepository;
import com.rpgstats.repositories.UsersSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

  final SessionRepository sessionRepository;
  final UsersSessionRepository usersSessionRepository;

  final CharacterService characterService;

  final GameSystemService gameSystemService;
  final ModelMapper modelMapper;

  public SessionService(
      SessionRepository sessionRepository,
      UsersSessionRepository usersSessionRepository,
      CharacterService characterService,
      GameSystemService gameSystemService,
      ModelMapper modelMapper) {
    this.sessionRepository = sessionRepository;
    this.usersSessionRepository = usersSessionRepository;
    this.characterService = characterService;
    this.gameSystemService = gameSystemService;
    this.modelMapper = modelMapper;
  }

  @Transactional
  public void leaveSession(User user, Integer sessionId) {
    UsersSession usersSession = getSessionById(user.getId(), sessionId);
    usersSessionRepository.delete(usersSession);
  }

  @Transactional
  public List<SessionDto> findSessionsByName(String name) {
    return sessionRepository.findByNameLikeIgnoreCase(name).stream()
        .map(x -> modelMapper.map(x, SessionDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public SessionDto getSessionDtoById(Integer id) {
    return modelMapper.map(
        sessionRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        String.format("Session not found " + "by id - %d", id))),
        SessionDto.class);
  }

  @Transactional
  public SessionDto joinSession(User user, Integer sessionId) {
    if (usersSessionRepository.existsById_UserIdAndId_SessionId(user.getId(), sessionId)) {
      throw new ConflictDataException(
          String.format("You already are in session with id -%d", sessionId));
    }
    Session session = getSessionById(sessionId);
    UsersSession usersSession = new UsersSession();
    usersSession.setSession(session);
    usersSession.setUser(user);
    usersSessionRepository.save(usersSession);
    return modelMapper.map(session, SessionDto.class);
  }

  @Transactional
  public List<SessionDto> findUserSessions(User user) {
    return usersSessionRepository.findById_UserId(user.getId()).stream()
        .map(x -> modelMapper.map(x.getSession(), SessionDto.class))
        .collect(Collectors.toList());
  }

  private Session getSessionById(Integer id) {
    return sessionRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ItemNotFoundException(String.format("Session not found " + "by id - %d", id)));
  }

  private UsersSession getSessionById(Integer userId, Integer sessionId) {
    return usersSessionRepository
        .findById_UserIdAndId_SessionId(userId, sessionId)
        .orElseThrow(
            () ->
                new ItemNotFoundException(
                    String.format("Session not found " + "by id - %d", sessionId)));
  }

  @Transactional
  public SessionDto createSession(User user, CreateSessionPostRequest createSessionPostRequest) {
    GameSystem gameSystem = gameSystemService.getSystemById(createSessionPostRequest.getSystemId());
    Session session = modelMapper.map(createSessionPostRequest, Session.class);
    session.setCreator(user);
    session.setCreatedAt(Instant.now());
    session.setGameSystem(gameSystem);
    sessionRepository.save(session);
    if (createSessionPostRequest.getCreatorAsPlayer()) {
      UsersSession usersSession = new UsersSession();
      usersSession.setSession(session);
      usersSession.setUser(user);
      usersSessionRepository.save(usersSession);
    }
    return modelMapper.map(session, SessionDto.class);
  }

  @Transactional
  public SessionDto updateSession(
      User user, UpdateSessionPutRequest updateSessionPutRequest, Integer id) {
    Session session = getUserSessionByIdAndUserId(id, user.getId());
    session.setName(updateSessionPutRequest.getName());
    session.setDescription(updateSessionPutRequest.getDescription());
    session.setCreatorAsPlayer(updateSessionPutRequest.getCreatorAsPlayer());
    session.setMaxNumberOfPlayers(updateSessionPutRequest.getMaxNumberOfPlayers());
    return modelMapper.map(session, SessionDto.class);
  }

  @Transactional
  public void deleteSession(User user, Integer id) {
    Session session = getUserSessionByIdAndUserId(id, user.getId());
    sessionRepository.delete(session);
  }

  @Transactional
  public boolean existById(Integer id) {
    return sessionRepository.existsById(id);
  }

  @Transactional
  public boolean existByIdAndOwnerId(Integer id, Integer ownerId) {
    return sessionRepository.existsByIdAndCreator_Id(id, ownerId);
  }

  @Transactional
  public Session getUserSessionByIdAndUserId(Integer id, Integer ownerId) {
    return usersSessionRepository
        .findById_UserIdAndId_SessionId(ownerId, id)
        .orElseThrow(() -> new ItemNotFoundException("No such user session"))
        .getSession();
  }

  public List<CharacterDto> findSessionCharactersDto(Integer sessionId) {
    return characterService.findSessionCharacters(sessionId).stream()
        .map(x -> modelMapper.map(x, CharacterDto.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public CharacterDto getSessionCharacterDto(Integer sessionId, Integer characterId) {
    return modelMapper.map(
        characterService.getSessionCharacter(sessionId, characterId), CharacterDto.class);
  }

  public void deleteSessionCharacter(User user, Integer sessionId, Integer characterId) {
    characterService.leaveSessionUserCharacter(user, sessionId, characterId);
  }
}
