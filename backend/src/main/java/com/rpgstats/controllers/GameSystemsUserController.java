package com.rpgstats.controllers;

import com.rpgstats.model.GameSystemDto;
import com.rpgstats.model.GameSystemPostRequest;
import com.rpgstats.model.GameSystemPutRequest;
import com.rpgstats.services.GameSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user/game-systems")
public class GameSystemsUserController {
    GameSystemService gameSystemService;

    public GameSystemsUserController(GameSystemService gameSystemService) {
        this.gameSystemService = gameSystemService;
    }

    @PostMapping()
    public GameSystemDto createSystem(@Valid @RequestBody GameSystemPostRequest gameSystemPostRequest) {
        return gameSystemService.createSystem(gameSystemPostRequest);
    }

    @PutMapping("/{id}")
    public GameSystemDto changeSystem(@RequestBody GameSystemPutRequest gameSystemPutRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteSystem() {
        return ResponseEntity.ok().build();
    }

}
