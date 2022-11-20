package com.rpgstats.controllers;

import com.rpgstats.messages.DTO.UserCharacterDto;
import com.rpgstats.messages.ErrorResponse;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.CharacterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
public class CharacterController {
  CharacterService characterService;

  AuthService authService;

  @Autowired
  public void setCharacterService(CharacterService characterService) {
    this.characterService = characterService;
  }

  @Autowired
  public void setAuthService(AuthService authService) {
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
}
