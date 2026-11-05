package com.bms.BookMyShowApp.controller;

import com.bms.BookMyShowApp.dto.ShowResponse;
import com.bms.BookMyShowApp.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final CatalogService catalogService;

    public ShowController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<ShowResponse> shows(@RequestParam String city, @RequestParam LocalDate date){
        return catalogService.shows(city, date);
    }

    // UPDATED ENDPOINT
    @GetMapping("/{showId}/seats")
    public List<String> getAvailableSeats(@PathVariable Long showId) {
        // Direct Repository nahi, Service call karo
        return catalogService.getAvailableSeats(showId);
    }
}