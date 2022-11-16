package com.rpgstats.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Characters User Endpoints")
@RequestMapping("user/characters")
@ApiResponse(responseCode = "401", description = "Unathorized", content = @Content)
@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
public class UserCharactersController {


}
