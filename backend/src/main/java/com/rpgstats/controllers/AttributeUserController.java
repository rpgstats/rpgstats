package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.AttributeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/game-systems")
public class AttributeUserController {
    Logger log = LoggerFactory.getLogger(AttributeUserController.class);
    AttributeService attributeService;
    AuthService authService;

    public AttributeUserController(AttributeService attributeService, AuthService authService) {
        this.attributeService = attributeService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/attributes")
    public SystemAttributeDto createAttribute(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateAttributePostRequest request) {
        return attributeService.createAttribute(authService.getIdFromJwt(jwt), systemId, request);

    }

    @PutMapping("/{systemId}/attributes/{attributeId}")
    public SystemAttributeDto changeAttribute(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer attributeId, @Valid @RequestBody ChangeAttributePutRequest request) {
        return attributeService.changeAttribute(authService.getIdFromJwt(jwt), attributeId, systemId, request);
    }

    @DeleteMapping("/{systemId}/attributes/{attributeId}")
    public SystemAttributeDto deleteAttribute(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer attributeId) {
        return attributeService.deleteAttribute(authService.getIdFromJwt(jwt), attributeId, systemId);
    }
}
