package com.rpgstats.controllers;

import com.rpgstats.messages.GameSystemDto;
import com.rpgstats.messages.SystemParameterModifierDto;
import com.rpgstats.services.GameSystemService;
import com.rpgstats.services.ParameterModifierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class ParameterModifierCommonController {
    ParameterModifierService parameterModifierService;

    public ParameterModifierCommonController(ParameterModifierService parameterModifierService) {
        this.parameterModifierService = parameterModifierService;
    }

    @GetMapping("/{systemId}/modifiers")
    public List<SystemParameterModifierDto> getParameterModifiers(@PathVariable Integer systemId) {
        return parameterModifierService.getParameterModifiersBySystem(systemId);
    }

    @GetMapping(params = {"/{systemId}/modifiers/{modifierId}"})
    public SystemParameterModifierDto getParameterModifier(@PathVariable Integer systemId, @PathVariable Integer parameterModifierId) {
        return parameterModifierService.getParameterModifier(systemId, parameterModifierId);
    }
}
