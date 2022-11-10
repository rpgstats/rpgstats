package com.rpgstats.controllers;

import com.rpgstats.messages.DTO.AttributeConstraintDto;
import com.rpgstats.services.ConstraintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class ConstraintCommonController {
    ConstraintService constraintService;

    public ConstraintCommonController(ConstraintService constraintService) {
        this.constraintService = constraintService;
    }

    @GetMapping("/{systemId}/constraints")
    public List<AttributeConstraintDto> getTags(@PathVariable Integer systemId) {
        return constraintService.getConstraintsBySystem(systemId);
    }

    @GetMapping("/{systemId}/constraints/{constraintId}")
    public AttributeConstraintDto getTag(@PathVariable Integer systemId, @PathVariable Integer constraintId) {
        return constraintService.getConstraint(systemId, constraintId);
    }
}
