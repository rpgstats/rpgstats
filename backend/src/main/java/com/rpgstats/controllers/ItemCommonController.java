package com.rpgstats.controllers;

import com.rpgstats.messages.GameSystemDto;
import com.rpgstats.messages.SystemItemDto;
import com.rpgstats.services.GameSystemService;
import com.rpgstats.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-systems")
public class ItemCommonController {
    ItemService itemService;

    public ItemCommonController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{systemId}/items")
    public List<SystemItemDto> getItems(@PathVariable Integer systemId) {
        return itemService.getItemsBySystem(systemId);
    }

    @GetMapping(params = {"/{systemId}/items/{itemId}"})
    public SystemItemDto getItem(@PathVariable Integer systemId, @PathVariable Integer itemId) {
        return itemService.getItem(systemId, itemId);
    }
}
