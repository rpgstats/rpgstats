package com.rpgstats.controllers;

import com.rpgstats.messages.GameSystemDto;
import com.rpgstats.messages.SystemParameterDto;
import com.rpgstats.services.GameSystemService;
import com.rpgstats.services.ParameterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class ParameterCommonController {
    ParameterService parameterService;

    public ParameterCommonController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @GetMapping("/{systemId}/parameters")
    public List<SystemParameterDto> getParameters(@PathVariable Integer systemId) {
        return parameterService.getParametersBySystem(systemId);
    }

    @GetMapping(params = {"/{systemId}/parameters/{parameterId}"})
    public SystemParameterDto getParameter(@PathVariable Integer systemId, @PathVariable Integer parameterId) {
        return parameterService.getParameter(systemId, parameterId);
    }
}
