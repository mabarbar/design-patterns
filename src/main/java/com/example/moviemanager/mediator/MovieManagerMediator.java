package com.example.moviemanager.mediator;

import com.example.moviemanager.application.MovieManagementService;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.memento.CatalogMemento;
import com.example.moviemanager.singleton.MovieCatalog;
import com.example.moviemanager.state.MovieWatchService;
import com.example.moviemanager.strategy.MovieSortStrategy;
import com.example.moviemanager.strategy.SortByRatingStrategy;
import com.example.moviemanager.strategy.SortByTitleStrategy;
import com.example.moviemanager.strategy.SortByYearStrategy;

import java.util.List;
import java.util.UUID;

// Wzorzec: Mediator — konsola rozmawia z jednym koordynatorem zamiast łączyć wiele podsystemów
public class MovieManagerMediator {
    private final MovieManagementService service;
    private final MovieCatalog catalog;
    private final MovieWatchService watchService;
    private CatalogMemento savedMemento;

    public MovieManagerMediator(MovieManagementService service, MovieCatalog catalog,
                                MovieWatchService watchService) {
        this.service = service;
        this.catalog = catalog;
        this.watchService = watchService;
    }

    public List<Movie> allMovies() {
        return service.listMovies();
    }

    public Movie addMovie(Movie movie) {
        return service.addMovie(movie);
    }

    public List<Movie> searchByTitle(String phrase) {
        return service.searchByTitle(phrase);
    }

    public List<Movie> recommendationsFor(String genre) {
        return service.recommendMovies(genre);
    }

    public void deleteMovie(UUID id) {
        service.deleteMovie(id);
    }

    public Movie findMovie(UUID id) {
        return service.findMovie(id);
    }

    public String undoLastCommand() {
        return service.undoLastCommand();
    }

    public String exportCatalog() {
        return service.exportCatalog();
    }

    // Zasada: jeden poziom abstrakcji — Mediator wybiera strategię, a strategia realizuje sortowanie.
    public List<Movie> sortedMovies(String criteria) {
        return service.sortedMovies(strategyFor(criteria));
    }

    private MovieSortStrategy strategyFor(String criteria) {
        return switch (criteria.toLowerCase()) {
            case "title" -> new SortByTitleStrategy();
            case "year" -> new SortByYearStrategy();
            case "rating" -> new SortByRatingStrategy();
            default -> throw new IllegalArgumentException("Użyj: title, year albo rating.");
        };
    }

    public void saveSnapshot() {
        savedMemento = catalog.saveMemento();
    }

    public void restoreSnapshot() {
        if (savedMemento == null) throw new IllegalStateException("Najpierw zapisz migawkę.");
        catalog.restoreMemento(savedMemento);
        service.clearCommandHistory();
    }

    public void startWatching(UUID id) {
        service.findMovie(id);
        watchService.start(id);
    }

    public void finishWatching(UUID id) {
        service.findMovie(id);
        watchService.finish(id);
    }

    public String watchStatus(UUID id) {
        service.findMovie(id);
        return watchService.status(id);
    }
}
