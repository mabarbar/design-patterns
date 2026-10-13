package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Zasada: DIP — logika aplikacyjna korzysta z kontraktu, a nie z katalogu w pamięci
public interface MovieRepositoryPort {
    List<Movie> findAll();
    Optional<Movie> findById(UUID id);
    void save(Movie movie);
    void delete(UUID id);
}
