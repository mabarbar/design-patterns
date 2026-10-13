package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.exception.MovieNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Zasada: DIP — klasa abstrakcyjna realizuje wspólne operacje
public abstract class AbstractMovieRepository implements MovieRepositoryPort {
    @Override
    public final List<Movie> findAll() {
        return List.copyOf(loadAll());
    }

    @Override
    public final Optional<Movie> findById(UUID id) {
        Objects.requireNonNull(id, "Id filmu jest wymagane.");
        return findAll().stream().filter(movie -> movie.id().equals(id)).findFirst();
    }

    @Override
    public final void save(Movie movie) {
        persist(Objects.requireNonNull(movie, "Film jest wymagany."));
    }

    @Override
    public final void delete(UUID id) {
        if (findById(id).isEmpty()) {
            throw new MovieNotFoundException("Nie znaleziono filmu: " + id);
        }
        removeRecord(id);
    }

    protected abstract List<Movie> loadAll();
    protected abstract void persist(Movie movie);
    protected abstract void removeRecord(UUID id);
}
