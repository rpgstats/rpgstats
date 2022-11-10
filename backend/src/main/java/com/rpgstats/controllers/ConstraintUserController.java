package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.AttributeConstraintDto;
import com.rpgstats.messages.DTO.SystemItemDto;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.ConstraintService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/game-systems")
public class ConstraintUserController {
    ConstraintService constraintService;
    AuthService authService;

    public ConstraintUserController(ConstraintService constraintService, AuthService authService) {
        this.constraintService = constraintService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/constraints")
    public AttributeConstraintDto createConstraint(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateConstraintPostRequest request) {
        return constraintService.createConstraint(authService.getIdFromJwt(jwt), systemId, request);
    }

    @PutMapping("/{systemId}/constraints/{constraintId}")
    public AttributeConstraintDto changeConstraint(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer constraintId, @Valid @RequestBody ChangeConstraintPutRequest request) {
        return constraintService.changeConstraint(authService.getIdFromJwt(jwt), constraintId, systemId, request);
    }

    @DeleteMapping("/{systemId}/constraints/{constraintId}")
    public void deleteConstraint(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer constraintId) {
        constraintService.deleteConstraint(authService.getIdFromJwt(jwt), constraintId, systemId);
    }
}
