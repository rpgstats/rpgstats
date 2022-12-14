package com.rpgstats.controllers;

import com.rpgstats.entity.UpdateSessionPutRequest;
import com.rpgstats.messages.CreateSessionPostRequest;
import com.rpgstats.messages.DTO.CharacterDto;
import com.rpgstats.messages.DTO.SessionDto;
import com.rpgstats.messages.ErrorResponse;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.SessionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Sessions user controller")
@RequestMapping("/user/sessions")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
public class SessionUserController {
  final SessionService sessionService;
  final AuthService authService;

  public SessionUserController(SessionService sessionService, AuthService authService) {
    this.sessionService = sessionService;
    this.authService = authService;
  }

  @ApiResponse(responseCode = "200")
  @GetMapping()
  public List<SessionDto> findUserSessions(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
    return sessionService.findUserSessions(authService.getUserFromJwt(jwt));
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Game system not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @PostMapping()
  public SessionDto createSession(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid CreateSessionPostRequest createSessionPostRequest) {
    return sessionService.createSession(authService.getUserFromJwt(jwt), createSessionPostRequest);
  }

  @ApiResponse(
      responseCode = "404",
      description = "Session not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @PutMapping("/{sessionId}")
  public SessionDto updateSession(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid UpdateSessionPutRequest updateSessionPostRequest,
      @PathVariable Integer sessionId) {
    return sessionService.updateSession(
        authService.getUserFromJwt(jwt), updateSessionPostRequest, sessionId);
  }

  @DeleteMapping("/{sessionId}")
  public void deleteSession(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer sessionId) {
    sessionService.deleteSession(authService.getUserFromJwt(jwt), sessionId);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @GetMapping("/{sessionId}/characters")
  public List<CharacterDto> findSessionCharacters(@PathVariable Integer sessionId) {
    return sessionService.findSessionCharactersDto(sessionId);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @GetMapping("/{sessionId}/characters/{characterId}")
  public CharacterDto findSessionCharacter(
      @PathVariable Integer sessionId, @PathVariable Integer characterId) {
    return sessionService.getSessionCharacterDto(sessionId, characterId);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @ApiResponse(
      responseCode = "409",
      description = "Conflict",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{sessionId}/characters/{characterId}")
  public void deleteSessionCharacter(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer sessionId,
      @PathVariable Integer characterId) {
    sessionService.deleteSessionCharacter(authService.getUserFromJwt(jwt), sessionId, characterId);
  }
}
