package com.example.moviemanager.application;

import com.example.moviemanager.command.AddMovieCommand;
import com.example.moviemanager.command.CommandHistory;
import com.example.moviemanager.command.RemoveMovieCommand;
import com.example.moviemanager.decorator.MovieRecommendationService;
import com.example.moviemanager.dip.MovieExporterPort;
import com.example.moviemanager.dip.MovieRepositoryPort;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.exception.DuplicateMovieException;
import com.example.moviemanager.exception.InvalidMovieDataException;
import com.example.moviemanager.exception.MovieNotFoundException;
import com.example.moviemanager.strategy.MovieSortStrategy;

import java.util.List;
import java.util.UUID;

// Zasada: SRP — serwis realizuje przypadki użycia katalogu, a szczegóły przechowywania pozostawia portowi.
// Zasada: DIP — zależności to interfejsy, nie klasy infrastruktury.
// Zasada: nazwy są znaczące — metody nazywają przypadki użycia, nie techniczne szczegóły.
public class MovieManagementService {
    private final MovieRepositoryPort repository;
    private final MovieRecommendationService recommendations;
    private final MovieExporterPort exporter;
    private final CommandHistory commandHistory = new CommandHistory();

    public MovieManagementService(MovieRepositoryPort repository,
                                  MovieRecommendationService recommendations,
                                  MovieExporterPort exporter) {
        this.repository = repository;
        this.recommendations = recommendations;
        this.exporter = exporter;
    }

    public List<Movie> listMovies() { return repository.findAll(); }

    public Movie addMovie(Movie movie) {
        validateUniqueTitleAndYear(movie);
        commandHistory.execute(new AddMovieCommand(repository, movie));
        return movie;
    }

    private void validateUniqueTitleAndYear(Movie candidate) {
        boolean duplicate = repository.findAll().stream().anyMatch(existing ->
                existing.title().equalsIgnoreCase(candidate.title())
                        && existing.releaseYear() == candidate.releaseYear());
        if (duplicate) throw new DuplicateMovieException("Film o tym tytule i roku już istnieje.");
    }

    public List<Movie> searchByTitle(String phrase) {
        if (phrase == null || phrase.isBlank()) throw new InvalidMovieDataException("Fraza nie może być pusta.");
        return repository.findAll().stream()
                .filter(movie -> movie.title().toLowerCase().contains(phrase.trim().toLowerCase()))
                .toList();
    }

    public List<Movie> sortedMovies(MovieSortStrategy strategy) {
        return strategy.sort(repository.findAll());
    }

    public List<Movie> recommendMovies(String genre) {
        if (genre == null || genre.isBlank()) throw new InvalidMovieDataException("Gatunek jest wymagany.");
        return recommendations.recommend(repository.findAll(), genre.trim());
    }

    public void deleteMovie(UUID id) {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Nie znaleziono filmu: " + id));
        commandHistory.execute(new RemoveMovieCommand(repository, movie));
    }

    public Movie findMovie(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Nie znaleziono filmu: " + id));
    }

    public String undoLastCommand() { return commandHistory.undoLast(); }
    public void clearCommandHistory() { commandHistory.clear(); }
    public String exportCatalog() { return exporter.export(repository.findAll()); }
}
