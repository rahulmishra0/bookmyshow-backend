package com.bms.BookMyShowApp.service;

import com.bms.BookMyShowApp.dto.MovieResponse;
import com.bms.BookMyShowApp.dto.ShowResponse;
import com.bms.BookMyShowApp.dto.TheatreResponse;
import com.bms.BookMyShowApp.repository.MovieRepository;
import com.bms.BookMyShowApp.repository.ShowRepository;
import com.bms.BookMyShowApp.repository.ShowSeatRepository;
import com.bms.BookMyShowApp.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CatalogService {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository; // 👈 Ye add karna zaroori hai

    public CatalogService(MovieRepository movieRepository,
                          TheatreRepository theatreRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository) { // 👈 Inject karo
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public List<MovieResponse> movies(){
        return movieRepository.findByActiveTrueOrderByTitle().stream().map(MovieResponse::from).toList();
    }

    public List<TheatreResponse> theaters(String city){
        return theatreRepository.findBYCityIgnoreCaseOrderByName(city).stream().map(TheatreResponse::from).toList();
    }

    public List<ShowResponse> shows(String city, LocalDate date){
        LocalDateTime from = date.atStartOfDay();
        return showRepository.findActiveShows(city, from, from.plusDays(1)).stream()
                .map(ShowResponse::from)
                .toList();
    }

    // 🔥 Ye naya method add karo (Ye missing tha!)
    public List<String> getAvailableSeats(Long showId) {
        return showSeatRepository.findAvailableLabels(showId);
    }
}