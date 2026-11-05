package com.bms.BookMyShowApp.repository;

import com.bms.BookMyShowApp.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    List<Theatre> findBYCityIgnoreCaseOrderByName(String city);

    Optional<Theatre> findByNameAndCity(String name, String city);
}
