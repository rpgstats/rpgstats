package com.rpgstats.controllers;

import com.rpgstats.messages.DTO.GameSystemDto;
import com.rpgstats.services.GameSystemService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    @Parameters(@Parameter(name = "ownerId",required = true,in = ParameterIn.QUERY))
    @GetMapping(params = {"ownerId"})
    public List<GameSystemDto> getSystemsByOwnerId(@RequestParam Integer ownerId) {
        return gameSystemService.getSystemsByOwnerId(ownerId);
    }

    @GetMapping("/{id}")
    public GameSystemDto getSystemById(@PathVariable Integer id) {
        return gameSystemService.getSystemById(id);
    }
}
