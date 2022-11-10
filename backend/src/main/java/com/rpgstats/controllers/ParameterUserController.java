package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.SystemParameterDto;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.ParameterService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("user/game-systems")
public class ParameterUserController {
    Logger log = LoggerFactory.getLogger(ParameterUserController.class);
    ParameterService parameterService;
    AuthService authService;

    public ParameterUserController(ParameterService parameterService, AuthService authService) {
        this.parameterService = parameterService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/parameters")
    public SystemParameterDto createParameter(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateParameterPostRequest request) {
        return parameterService.createParameter(authService.getIdFromJwt(jwt), systemId, request);

    }

    @PutMapping("/{systemId}/parameters/{parameterId}")
    public SystemParameterDto changeParameter(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterId, @Valid @RequestBody ChangeParameterPutRequest request) {
        return parameterService.changeParameter(authService.getIdFromJwt(jwt), parameterId, systemId, request);
    }

    @DeleteMapping("/{systemId}/parameters/{parameterId}")
    public SystemParameterDto deleteParameter(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterId) {
        return parameterService.deleteParameter(authService.getIdFromJwt(jwt), parameterId, systemId);
    }
}
