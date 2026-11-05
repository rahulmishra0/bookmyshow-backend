package com.bms.BookMyShowApp.repository;

import com.bms.BookMyShowApp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByActiveTrueOrderByTitle();

    Optional<Movie> findByTitle(String title);
}
