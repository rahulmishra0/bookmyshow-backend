package com.bms.BookMyShowApp.controller;

import com.bms.BookMyShowApp.dto.TheatreResponse;
import com.bms.BookMyShowApp.service.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theaters")
public class TheatreController {

    private final CatalogService catalogService;

    public TheatreController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<TheatreResponse> theaters(@RequestParam String city){
        return catalogService.theaters(city);
    }
}
