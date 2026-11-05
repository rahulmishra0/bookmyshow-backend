package com.bms.BookMyShowApp.dto;

import com.bms.BookMyShowApp.entity.Show;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowResponse(
        Long id,
        MovieResponse movie,
        TheatreResponse theatre,
        LocalDateTime startAt,
        LocalDateTime endAt,
        BigDecimal ticketPrice,
        int totalSeats,
        int availableSeats // 👈 Labels hata diye
) {
    public static ShowResponse from(Show show){
        return new ShowResponse(
                show.getId(),
                MovieResponse.from(show.getMovie()),
                TheatreResponse.from(show.getTheatre()),
                show.getStartAt(),
                show.getEndAt(),
                show.getTicketPrice(),
                show.getTotalSeats(),
                show.getAvailableSeats() // 👈 FIX: Ab dynamic available seats dikhegi
        );
    }
}