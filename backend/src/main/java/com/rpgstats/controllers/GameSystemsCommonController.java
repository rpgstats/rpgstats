package com.rpgstats.controllers;

import com.rpgstats.messages.DTO.*;
import com.rpgstats.messages.ErrorResponse;
import com.rpgstats.services.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class GameSystemsCommonController {
  GameSystemService gameSystemService;
  ParameterService parameterService;

  ModifierService modifierService;

  ConstraintService constraintService;

  AttributeService attributeService;

  TagService tagService;
  ItemService itemService;

    public GameSystemsCommonController(GameSystemService gameSystemService, ParameterService parameterService,
                                       ModifierService modifierService, ConstraintService constraintService,
                                       AttributeService attributeService, TagService tagService,
                                       ItemService itemService) {
        this.gameSystemService = gameSystemService;
        this.parameterService = parameterService;
        this.modifierService = modifierService;
        this.constraintService = constraintService;
        this.attributeService = attributeService;
        this.tagService = tagService;
        this.itemService = itemService;
    }

    @GetMapping(
      value = "/search",
      params = {"name"})
  public List<GameSystemDto> findSystemsByName(@RequestParam String name) {
    return gameSystemService.getSystemsByName(name);
  }

  @GetMapping("/{id}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public GameSystemDto getSystemById(@PathVariable Integer id) {
    return gameSystemService.getSystemDtoById(id);
  }

  @GetMapping("/{systemId}/tags")
  public List<SystemTagDto> getTags(@PathVariable Integer systemId) {
    return tagService.getTagsBySystem(systemId);
  }

  @GetMapping("/{systemId}/tags/{tagId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemTagDto getTagById(@PathVariable Integer systemId, @PathVariable Integer tagId) {
    return tagService.getTagDtoById(systemId, tagId);
  }

  @GetMapping("/{systemId}/parameters")
  public List<SystemParameterDto> getParameters(@PathVariable Integer systemId) {
    return parameterService.getParametersDtoBySystem(systemId);
  }

  @GetMapping("/game-systems/{systemId}/parameters/{parameterId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemParameterDto getParameter(
      @PathVariable Integer systemId, @PathVariable Integer parameterId) {
    return parameterService.getParameterDtoById(systemId, parameterId);
  }

  @GetMapping("/{systemId}/modifiers")
  @ApiResponse(responseCode = "200")
  public List<SystemParameterModifierDto> getParameterModifiers(@PathVariable Integer systemId) {
    return modifierService.getParameterModifiersDtoBySystem(systemId);
  }

  @GetMapping("/{systemId}/modifiers/{modifierId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemParameterModifierDto getParameterModifier(
      @PathVariable Integer systemId, @PathVariable Integer parameterModifierId) {
    return modifierService.getParameterModifierDtoById(systemId, parameterModifierId);
  }

  @GetMapping("/{systemId}/items")
  public List<SystemItemDto> getItems(@PathVariable Integer systemId) {
    return itemService.getItemsDtoBySystem(systemId);
  }

  @GetMapping("/{systemId}/items/{itemId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemItemDto getItem(@PathVariable Integer systemId, @PathVariable Integer itemId) {
    return itemService.getItemDtoById(systemId, itemId);
  }

  @GetMapping("/{systemId}/constraints")
  public List<AttributeConstraintDto> getConstraints(@PathVariable Integer systemId) {
    return constraintService.getConstraintsBySystem(systemId);
  }

  @GetMapping("/{systemId}/constraints/{constraintId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public AttributeConstraintDto getConstraint(
      @PathVariable Integer systemId, @PathVariable Integer constraintId) {
    return constraintService.getConstraintDtoById(systemId, constraintId);
  }

  @GetMapping("/{systemId}/attributes")
  public List<SystemAttributeDto> getAttributes(@PathVariable Integer systemId) {
    return attributeService.getAttributesBySystem(systemId);
  }

  @GetMapping("/{systemId}/attributes/{attributeId}")
  @ApiResponse(responseCode = "200")
  @ApiResponse(
      responseCode = "404",
      description = "The item was Not Found.",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorResponse.class))
      })
  public SystemAttributeDto getAttribute(
      @PathVariable Integer systemId, @PathVariable Integer attributeId) {
    return attributeService.getAttributeDtoById(systemId, attributeId);
  }
}
