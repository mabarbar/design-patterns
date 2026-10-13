package com.example.moviemanager.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.decorator.MovieRecommendationService;
import com.example.moviemanager.dip.MovieExporterPort;
import com.example.moviemanager.dip.MovieRepositoryPort;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;
import com.example.moviemanager.exception.DuplicateMovieException;
import com.example.moviemanager.exception.MovieNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieManagementServiceTest {
    private MovieRepositoryPort repository;
    private MovieRecommendationService recommendations;
    private MovieExporterPort exporter;
    private MovieManagementService service;

    @BeforeEach void setUp() {
        repository = mock(MovieRepositoryPort.class);
        recommendations = mock(MovieRecommendationService.class);
        exporter = movies -> "exported";
        service = new MovieManagementService(repository, recommendations, exporter);
    }

    private Movie movie(String title, int year) {
        return new MovieBuilder().title(title).releaseYear(year).genre("Drama")
                .rating(8.0).type(MovieType.FEATURE_FILM).build();
    }

    @Test void addMovieSavesThroughRepository() {
        Movie movie = movie("Arrival", 2016);
        when(repository.findAll()).thenReturn(List.of());
        service.addMovie(movie);
        verify(repository).save(movie);
    }

    @Test void rejectsDuplicateTitleAndYear() {
        Movie original = movie("Arrival", 2016);
        when(repository.findAll()).thenReturn(List.of(original));
        assertThrows(DuplicateMovieException.class, () -> service.addMovie(movie("arrival", 2016)));
    }

    @Test void searchesTitleWithoutCaseSensitivity() {
        when(repository.findAll()).thenReturn(List.of(movie("Arrival", 2016), movie("Heat", 1995)));
        assertEquals(1, service.searchByTitle("arr").size());
    }

    @Test void deleteMissingMovieThrowsDomainException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> service.deleteMovie(java.util.UUID.randomUUID()));
    }

    @Test void undoingAddDeletesThatMovie() {
        Movie movie = movie("Arrival", 2016);
        when(repository.findAll()).thenReturn(List.of());
        service.addMovie(movie);
        service.undoLastCommand();
        verify(repository).delete(movie.id());
    }
}
