package com.rpgstats.controllers;

import com.rpgstats.messages.*;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.TagService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public class TagUserController {
    TagService tagService;
    AuthService authService;

    public TagUserController(TagService tagService, AuthService authService) {
        this.tagService = tagService;
        this.authService = authService;
    }

    @PostMapping("/{systemId}/tags")
    public SystemTagDto createTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @Valid @RequestBody CreateTagPostRequest request) {
        return tagService.createTag(authService.getIdFromJwt(jwt), systemId, request);

    }

    @PutMapping("/{systemId}/tags/{tagId}")
    public SystemTagDto changeTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer tagId, @Valid @RequestBody ChangeTagPutRequest request) {
        return tagService.changeTag(authService.getIdFromJwt(jwt), tagId, systemId, request);
    }

    @DeleteMapping("/{systemId}/tags/{tagId}")
    public SystemTagDto deleteTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer systemId, @PathVariable Integer tagId) {
        return tagService.deleteTag(authService.getIdFromJwt(jwt), tagId, systemId);
    }
}
