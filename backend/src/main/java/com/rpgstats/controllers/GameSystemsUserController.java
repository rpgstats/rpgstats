package com.rpgstats.controllers;

import com.rpgstats.messages.GameSystemDto;
import com.rpgstats.messages.GameSystemPostRequest;
import com.rpgstats.messages.GameSystemPutRequest;
import com.rpgstats.security.RpgStatsUserDetail;
import com.rpgstats.security.RpgStatsUserDetailsService;
import com.rpgstats.services.AuthService;
import com.rpgstats.services.GameSystemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("user/game-systems")
public class GameSystemsUserController {
    GameSystemService gameSystemService;
    AuthService authService;

    public GameSystemsUserController(GameSystemService gameSystemService, AuthService authService) {
        this.gameSystemService = gameSystemService;
        this.authService = authService;
    }

    @PostMapping()
    public GameSystemDto createSystem(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody GameSystemPostRequest gameSystemPostRequest) {
        return gameSystemService.createSystem(authService.getIdFromJwt(jwt), gameSystemPostRequest);
    }

    @PutMapping("/{id}")
    public GameSystemDto changeSystem(@AuthenticationPrincipal Jwt jwt, @RequestBody GameSystemPutRequest gameSystemPutRequest, @PathVariable Integer id) {
        return gameSystemService.changeSystem(authService.getIdFromJwt(jwt), id, gameSystemPutRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteSystem(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer id) {
        gameSystemService.deleteSystem(authService.getIdFromJwt(jwt), id);
    }

}
