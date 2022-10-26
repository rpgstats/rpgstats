package com.rpgstats.controllers;

import com.rpgstats.messages.GameSystemDto;
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
        return gameSystemService.getSystemsByName(name);
    }

    @GetMapping(params = {"ownerId"})
    public List<GameSystemDto> getSystemsByOwnerId(@RequestParam Integer ownerId) {
        return gameSystemService.getSystemsByOwnerId(ownerId);
    }

    @GetMapping("/{id}")
    public GameSystemDto getSystemById(@PathVariable Integer id) {
        return gameSystemService.getSystemById(id);
    }
}
