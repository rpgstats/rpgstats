package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.CharacterSlotDto;
import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.messages.DTO.SystemTagDto;
import com.rpgstats.messages.DTO.UserCharacterDto;
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
  @GetMapping("/{characterId}")
  public UserCharacterDto getUserCharacterById(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    return characterService.getUserCharacterDTObyId(authService.getUserFromJwt(jwt), characterId);
  }

  @ApiResponse(responseCode = "200")
  @GetMapping()
  public List<UserCharacterDto> getUserCharacters(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt) {
    return characterService.getUserCharacters(authService.getUserFromJwt(jwt));
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
  @PostMapping()
  public UserCharacterDto createUserCharacter(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid CreateCharacterPostRequest createCharacterPostRequest) {
    return characterService.createUserCharacter(
        authService.getUserFromJwt(jwt), createCharacterPostRequest);
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
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = {
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

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @PostMapping("/{characterId}/copy")
  public UserCharacterDto copyUserCharacter(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    return characterService.copyUserCharacter(authService.getUserFromJwt(jwt), characterId);
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
  @GetMapping("/{characterId}/slots")
  public List<CharacterSlotDto> getCharacterSlots(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    return characterService.getCharacterSlotsDto(authService.getUserFromJwt(jwt), characterId);
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
  @PostMapping("/{characterId}/slots")
  public CharacterSlotDto createCharacterSlot(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid CreateCharacterSlotPostRequest createCharacterSlotPostRequest,
      @PathVariable Integer characterId) {
    return characterService.createCharacterSlot(
        authService.getUserFromJwt(jwt), characterId, createCharacterSlotPostRequest);
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
  @GetMapping("/{characterId}/slots/{slotId}")
  public CharacterSlotDto getCharacterSlot(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId) {
    return characterService.getSlotDtoById(authService.getUserFromJwt(jwt), characterId, slotId);
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
  @PutMapping("/{characterId}/slots/{slotId}")
  public CharacterSlotDto updateCharacterSlot(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @RequestBody @Valid UpdateCharacterSlotPutRequest updateCharacterSlotPutRequest,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId) {
    return characterService.updateSlot(
        authService.getUserFromJwt(jwt), characterId, slotId, updateCharacterSlotPutRequest);
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
  @DeleteMapping("/{characterId}/slots/{slotId}")
  public void deleteCharacterSlot(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId) {
    characterService.deleteSlot(authService.getUserFromJwt(jwt), characterId, slotId);
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
  @GetMapping("/{characterId}/slots/{slotId}/tags")
  public List<SystemTagDto> getCharacterSlotTags(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId) {
    return characterService.getSlotTagsDto(authService.getUserFromJwt(jwt), characterId, slotId);
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
  @PostMapping("/{characterId}/slots/{slotId}/tags")
  public SystemTagDto addCharacterSlotTag(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody AddSlotTagPostRequest addSlotTagPostRequest,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId) {
    return characterService.addCharacterSlotTag(
        authService.getUserFromJwt(jwt), addSlotTagPostRequest, characterId, slotId);
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
  @DeleteMapping("/{characterId}/slots/{slotId}/tags/{tagId}")
  public void deleteCharacterSlotTag(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @PathVariable Integer slotId,
      @PathVariable Integer tagId) {
    characterService.deleteCharacterSlotTag(
        authService.getUserFromJwt(jwt), characterId, slotId, tagId);
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
  @GetMapping("/{characterId}/attributes")
  public List<SystemAttributeDto> getCharacterAttributes(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId) {
    return characterService.getCharacterAttributes(authService.getUserFromJwt(jwt), characterId);
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
  @PostMapping("/{characterId}/attributes")
  public SystemAttributeDto addCharacterAttribute(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody AddCharacterAttributePostRequest addCharacterAttributePostRequest,
      @PathVariable Integer characterId) {
    return characterService.addCharacterAttribute(
        authService.getUserFromJwt(jwt), addCharacterAttributePostRequest, characterId);
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
  @DeleteMapping("/{characterId}/attributes/{attributeId}")
  public void deleteCharacterAttribute(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @PathVariable Integer characterId,
      @PathVariable Integer attributeId) {
    characterService.deleteCharacterAttribute(
        authService.getUserFromJwt(jwt), characterId, attributeId);
  }
}
