package com.rpgstats.controllers;

import com.rpgstats.entity.SystemParameter;
import com.rpgstats.messages.*;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public SystemParameterDto createParameter(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateParameterPostRequest request) {
        return parameterService.createParameter(authService.getIdFromJwt(jwt), systemId, request);

    }

    @PutMapping("/{systemId}/parameters/{parameterId}")
    public SystemParameterDto changeParameter(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterId, @Valid @RequestBody ChangeParameterPutRequest request) {
        return parameterService.changeParameter(authService.getIdFromJwt(jwt), parameterId, systemId, request);
    }

    @DeleteMapping("/{systemId}/parameters/{parameterId}")
    public SystemParameterDto deleteParameter(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer parameterId) {
        return parameterService.deleteParameter(authService.getIdFromJwt(jwt), parameterId, systemId);
    }
}
