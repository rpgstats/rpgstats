package com.rpgstats.controllers;

import com.rpgstats.messages.CreateCharacterPostRequest;
import com.rpgstats.messages.DTO.UserCharacterDto;
import com.rpgstats.messages.ErrorResponse;
import com.rpgstats.messages.UpdateCharacterPutRequest;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.CharacterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/characters")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
public class UserCharacterController {
  CharacterService characterService;

  AuthService authService;

  public UserCharacterController(CharacterService characterService, AuthService authService) {
    this.characterService = characterService;
    this.authService = authService;
  }

  @ApiResponse(responseCode = "200")
  @GetMapping()
  public List<UserCharacterDto> getUserCharacters(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
    return characterService.getUserCharacters(authService.getUserFromJwt(jwt));
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "404", description = "Not found",content = {
          @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
  })
  @PostMapping()
  public UserCharacterDto createUserCharacter(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid CreateCharacterPostRequest createCharacterPostRequest) {
    return characterService.createUserCharacter(
        authService.getUserFromJwt(jwt), createCharacterPostRequest);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "404", description = "Not found",content = {
          @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "409", description = "Conflict",content = {
          @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
  })
  @GetMapping("/{characterId}")
  public UserCharacterDto getUserCharacterById(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    return characterService.getUserCharacterDTObyId(authService.getUserFromJwt(jwt), characterId);
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
  @PutMapping("/{characterId}")
  public UserCharacterDto updateUserCharacterById(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @RequestBody UpdateCharacterPutRequest updateCharacterPutRequest) {
    return characterService.updateUserCharacter(
        authService.getUserFromJwt(jwt), characterId, updateCharacterPutRequest);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "404", description = "Not found",content = {
          @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
  })
  @DeleteMapping("/{characterId}")
  public void updateUserCharacterById(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    characterService.deleteUserCharacter(authService.getUserFromJwt(jwt), characterId);
  }
}
