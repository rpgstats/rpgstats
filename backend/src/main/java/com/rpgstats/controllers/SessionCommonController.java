package com.rpgstats.controllers;

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

@RestController
@Tag(name = "Sessions common controller")
@RequestMapping("/sessions/")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
public class SessionCommonController {

  final SessionService sessionService;

  final AuthService authService;

  public SessionCommonController(SessionService sessionService, AuthService authService) {
    this.sessionService = sessionService;
    this.authService = authService;
  }

  //  @ApiResponse(responseCode = "200")
  //  @GetMapping("/search")
  //  public List<SessionDto> findSessionsByName(@RequestParam String name) {
  //    return sessionService.findSessionsByName(name);
  //  }
  //
  //  @ApiResponse(responseCode = "200")
  //  @ApiResponse(
  //      responseCode = "404",
  //      description = "Session not found",
  //      content = {
  //        @Content(
  //            mediaType = MediaType.APPLICATION_JSON_VALUE,
  //            schema = @Schema(implementation = ErrorResponse.class))
  //      })
  //  @GetMapping("/search/{sessionId}")
  //  public SessionDto findSessionById(@PathVariable Integer sessionId) {
  //    return sessionService.getSessionDtoById(sessionId);
  //  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Session not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @ApiResponse(
      responseCode = "409",
      description = "You are already in session",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @PostMapping("/{sessionId}/join")
  public SessionDto joinSession(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer sessionId) {
    return sessionService.joinSession(authService.getUserFromJwt(jwt), sessionId);
  }

  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Session not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @PatchMapping("/{sessionId}/leave")
  public void leaveSession(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer sessionId) {
    sessionService.leaveSession(authService.getUserFromJwt(jwt), sessionId);
  }
}
