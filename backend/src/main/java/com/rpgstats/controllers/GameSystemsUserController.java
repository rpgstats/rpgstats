package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.*;
import com.rpgstats.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Game-System User Endpoints")
@RequestMapping("user/game-systems")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
public class GameSystemsUserController {
  final GameSystemService gameSystemService;
  final TagService tagService;
  final ParameterService parameterService;
  final ModifierService modifierService;
  final ItemService itemService;

  final ConstraintService constraintService;
  final AttributeService attributeService;
  AuthService authService;

  public GameSystemsUserController(
      GameSystemService gameSystemService,
      TagService tagService,
      ParameterService parameterService,
      ModifierService modifierService,
      ItemService itemService,
      ConstraintService constraintService,
      AttributeService attributeService,
      AuthService authService) {
    this.gameSystemService = gameSystemService;
    this.tagService = tagService;
    this.parameterService = parameterService;
    this.modifierService = modifierService;
    this.itemService = itemService;
    this.constraintService = constraintService;
    this.attributeService = attributeService;
    this.authService = authService;
  }

  @Autowired
  public void setAuthService(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping()
  @Operation(summary = "Create new game system")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "409",
      description = "Conflict",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public GameSystemDto createSystem(
      @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody GameSystemPostRequest gameSystemPostRequest) {
    return gameSystemService.createSystem(authService.getIdFromJwt(jwt), gameSystemPostRequest);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Change game system parameters")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public GameSystemDto changeSystem(
      @RequestBody GameSystemPutRequest gameSystemPutRequest, @PathVariable Integer id) {
    return gameSystemService.changeSystem(id, gameSystemPutRequest);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete game system")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public void deleteSystem(@PathVariable Integer id) {
    gameSystemService.deleteSystem(id);
  }

  @PostMapping("/{systemId}/parameters")
  @Operation(summary = "Create new parameter")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  public SystemParameterDto createParameter(
      @PathVariable Integer systemId, @Valid @RequestBody CreateParameterPostRequest request) {
    return parameterService.createParameter(systemId, request);
  }

  @PutMapping("/{systemId}/parameters/{parameterId}")
  @Operation(summary = "Change parameter")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Parameter not found",
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
  public SystemParameterDto changeParameter(
      @PathVariable Integer systemId,
      @PathVariable Integer parameterId,
      @Valid @RequestBody ChangeParameterPutRequest request) {
    return parameterService.changeParameter(parameterId, systemId, request);
  }

  @DeleteMapping("/{systemId}/parameters/{parameterId}")
  @Operation(summary = "Delete parameter")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "Parameter not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemParameterDto deleteParameter(
      @PathVariable Integer systemId, @PathVariable Integer parameterId) {
    return parameterService.deleteParameter(parameterId, systemId);
  }

  @PostMapping("/{systemId}/modifiers")
  @Operation(summary = "Create new modifier")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  public SystemParameterModifierDto createParameterModifier(
      @PathVariable Integer systemId,
      @Valid @RequestBody CreateParameterModifierPostRequest request) {
    return modifierService.createParameterModifier(systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Change modifier")
  @ApiResponse(
      responseCode = "404",
      description = "Modifier not found",
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
  @PutMapping("/{systemId}/modifiers/{modifierId}")
  public SystemParameterModifierDto changeParameterModifier(
      @PathVariable Integer systemId,
      @PathVariable Integer parameterModifierId,
      @Valid @RequestBody ChangeParameterModifierPutRequest request) {
    return modifierService.changeParameterModifier(parameterModifierId, systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Delete modifier")
  @ApiResponse(
      responseCode = "404",
      description = "Modifier not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{systemId}/modifiers/{modifierId}")
  public SystemParameterModifierDto deleteParameterModifier(
      @PathVariable Integer systemId, @PathVariable Integer parameterModifierId) {
    return modifierService.deleteParameterModifier(parameterModifierId, systemId);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Create new item")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  @PostMapping("/{systemId}/items")
  public SystemItemDto createItem(
      @PathVariable Integer systemId, @Valid @RequestBody CreateItemPostRequest request) {
    return itemService.createItem(systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Change item")
  @ApiResponse(
      responseCode = "404",
      description = "Item not found",
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
  @PutMapping("/{systemId}/items/{itemId}")
  public SystemItemDto changeItem(
      @PathVariable Integer systemId,
      @PathVariable Integer itemId,
      @Valid @RequestBody ChangeItemPutRequest request) {
    return itemService.changeItem(itemId, systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Delete item")
  @ApiResponse(
      responseCode = "404",
      description = "Item not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{systemId}/items/{itemId}")
  public SystemItemDto deleteItem(@PathVariable Integer systemId, @PathVariable Integer itemId) {

    return itemService.deleteItem(itemId, systemId);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Create new constraint")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  @PostMapping("/{systemId}/constraints")
  public AttributeConstraintDto createConstraint(
      @PathVariable Integer systemId, @Valid @RequestBody CreateConstraintPostRequest request) {
    return constraintService.createConstraint(systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Change constraint")
  @ApiResponse(
      responseCode = "404",
      description = "Constraint not found",
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
  @PutMapping("/{systemId}/constraints/{constraintId}")
  public AttributeConstraintDto changeConstraint(
      @PathVariable Integer systemId,
      @PathVariable Integer constraintId,
      @Valid @RequestBody ChangeConstraintPutRequest request) {
    return constraintService.changeConstraint(constraintId, systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Delete constraint")
  @ApiResponse(
      responseCode = "404",
      description = "Constraint not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{systemId}/constraints/{constraintId}")
  public void deleteConstraint(@PathVariable Integer systemId, @PathVariable Integer constraintId) {

    constraintService.deleteConstraint(constraintId, systemId);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Create new attribute")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  @PostMapping("/{systemId}/attributes")
  public SystemAttributeDto createAttribute(
      @PathVariable Integer systemId, @Valid @RequestBody CreateAttributePostRequest request) {
    return attributeService.createAttribute(systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Change attribute")
  @ApiResponse(
      responseCode = "404",
      description = "Attribute not found",
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
  @PutMapping("/{systemId}/attributes/{attributeId}")
  public SystemAttributeDto changeAttribute(
      @PathVariable Integer systemId,
      @PathVariable Integer attributeId,
      @Valid @RequestBody ChangeAttributePutRequest request) {
    return attributeService.changeAttribute(attributeId, systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Delete attribute")
  @ApiResponse(
      responseCode = "404",
      description = "Attribute not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{systemId}/attributes/{attributeId}")
  public SystemAttributeDto deleteAttribute(
      @PathVariable Integer systemId, @PathVariable Integer attributeId) {
    return attributeService.deleteAttribute(attributeId, systemId);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Create new tag")
  @ApiResponse(
      responseCode = "404",
      description = "System not found",
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
  @PostMapping("/{systemId}/tags")
  public SystemTagDto createTag(
      @PathVariable Integer systemId, @Valid @RequestBody CreateTagPostRequest request) {
    return tagService.createTag(systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Change tag")
  @ApiResponse(
      responseCode = "404",
      description = "Tag not found",
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
  @PutMapping("/{systemId}/tags/{tagId}")
  public SystemTagDto changeTag(
      @PathVariable Integer systemId,
      @PathVariable Integer tagId,
      @Valid @RequestBody ChangeTagPutRequest request) {
    return tagService.changeTag(tagId, systemId, request);
  }

  @ApiResponse(responseCode = "200")
  @Operation(summary = "Delete tag")
  @ApiResponse(
      responseCode = "404",
      description = "Tag not found",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  @DeleteMapping("/{systemId}/tags/{tagId}")
  public SystemTagDto deleteTag(@PathVariable Integer systemId, @PathVariable Integer tagId) {

    return tagService.deleteTag(tagId, systemId);
  }
}
