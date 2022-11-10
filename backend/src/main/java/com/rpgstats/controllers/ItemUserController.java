package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.messages.DTO.SystemItemDto;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.ItemService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/game-systems")
public class ItemUserController {
    ItemService itemService;
    AuthService authService;

    public ItemUserController(ItemService itemService, AuthService authService) {
        this.itemService = itemService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/items")
    public SystemItemDto createItem(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateItemPostRequest request) {
        return itemService.createItem(authService.getIdFromJwt(jwt), systemId, request);
    }

    @PutMapping("/{systemId}/items/{itemId}")
    public SystemItemDto changeItem(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer itemId, @Valid @RequestBody ChangeItemPutRequest request) {
        return itemService.changeItem(authService.getIdFromJwt(jwt), itemId, systemId, request);
    }

    @DeleteMapping("/{systemId}/items/{itemId}")
    public SystemItemDto deleteItem(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer itemId) {
        return itemService.deleteItem(authService.getIdFromJwt(jwt), itemId, systemId);
    }
}
