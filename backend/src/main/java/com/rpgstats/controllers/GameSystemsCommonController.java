package com.rpgstats.controllers;

import com.rpgstats.model.GameSystemDto;
import com.rpgstats.services.GameSystemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class GameSystemsCommonController {
    GameSystemService gameSystemService;

    public GameSystemsCommonController(GameSystemService gameSystemService) {
        this.gameSystemService = gameSystemService;
    }

    @GetMapping(params = {"name"})
    public List<GameSystemDto> getSystemsByName(@RequestParam String name) {
        return null;
    }

    @GetMapping(params = {"ownerId"})
    public List<GameSystemDto> getSystemsByOwnerId(@PathVariable Integer ownerId) {
        return null;
    }

    @GetMapping("/{id}")
    public List<GameSystemDto> getSystemById(@PathVariable Integer id) {
        return null;
    }
}
