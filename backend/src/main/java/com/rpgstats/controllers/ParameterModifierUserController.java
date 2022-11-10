package com.rpgstats.controllers;

import com.rpgstats.entity.ParameterModifier;
import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.SystemParameterModifierDto;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.ParameterModifierService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user/game-systems")
public class ParameterModifierUserController {
    Logger log = LoggerFactory.getLogger(ParameterModifierUserController.class);
    ParameterModifierService parameterModifierService;
    AuthService authService;

    public ParameterModifierUserController(ParameterModifierService parameterModifierService, AuthService authService) {
        this.parameterModifierService = parameterModifierService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/modifiers")
    public SystemParameterModifierDto createParameterModifier(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateParameterModifierPostRequest request) {
        return parameterModifierService.createParameterModifier(authService.getIdFromJwt(jwt), systemId, request);

    }

    @PutMapping("/{systemId}/modifiers/{modifierId}")
    public SystemParameterModifierDto changeParameterModifier(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterModifierId, @Valid @RequestBody ChangeParameterModifierPutRequest request) {
        return parameterModifierService.changeParameterModifier(authService.getIdFromJwt(jwt), parameterModifierId, systemId, request);
    }

    @DeleteMapping("/{systemId}/modifiers/{modifierId}")
    public SystemParameterModifierDto deleteParameterModifier(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterModifierId) {
        return parameterModifierService.deleteParameterModifier(authService.getIdFromJwt(jwt), parameterModifierId, systemId);
    }
}
