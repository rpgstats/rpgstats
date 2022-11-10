package com.rpgstats.controllers;

import com.rpgstats.messages.SystemTagDto;
import com.rpgstats.services.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class TagCommonController {
    TagService tagService;

    public TagCommonController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{systemId}/tags")
    public List<SystemTagDto> getTags(@PathVariable Integer systemId) {
        return tagService.getTagsBySystem(systemId);
    }

    @GetMapping(params = {"/{systemId}/tags/{tagId}"})
    public SystemTagDto getTag(@PathVariable Integer systemId, @PathVariable Integer tagId) {
        return tagService.getTag(systemId, tagId);
    }
}
