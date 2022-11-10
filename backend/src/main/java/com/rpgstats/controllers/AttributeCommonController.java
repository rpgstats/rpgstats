package com.rpgstats.controllers;

import com.rpgstats.messages.DTO.SystemAttributeDto;
import com.rpgstats.services.AttributeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class AttributeCommonController {
    AttributeService attributeService;

    public AttributeCommonController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/{systemId}/attributes")
    public List<SystemAttributeDto> getAttributes(@PathVariable Integer systemId) {
        return attributeService.getAttributesBySystem(systemId);
    }

    @GetMapping(params = {"/{systemId}/attributes/{attributeId}"})
    public SystemAttributeDto getAttribute(@PathVariable Integer systemId, @PathVariable Integer attributeId) {
        return attributeService.getAttribute(systemId, attributeId);
    }
}