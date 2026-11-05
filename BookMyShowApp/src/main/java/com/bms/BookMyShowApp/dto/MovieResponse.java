package com.bms.BookMyShowApp.dto;

import com.bms.BookMyShowApp.entity.Movie;

public record MovieResponse(Long id, String title, String language, String genre, Integer durationMinutes,
                            String certification, String description, String posterUrl, String trailerUrl) {

    public static MovieResponse from (Movie movie){
        return new MovieResponse(movie.getId(), movie.getTitle(), movie.getLanguage(), movie.getGenre(),
                movie.getDurationMinutes(), movie.getCertificate(), movie.getDescription(), movie.getPosterUrl(),
                movie.getTrailerUrl());
    }
}
