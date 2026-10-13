package com.example.moviemanager.adapter;

import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;
import com.example.moviemanager.exception.InvalidMovieDataException;
import com.example.moviemanager.factory.MovieFactory;

// Wzorzec: Adapter — LegacyMovieAdapter przekształca dane ze "starego" LegacyMovieRecord na aktualny Movie.
public class LegacyMovieAdapter {
    public Movie adapt(LegacyMovieRecord legacyRecord) {
        if (legacyRecord == null) throw new InvalidMovieDataException("Rekord źródłowy jest wymagany.");
        MovieType type = mapType(legacyRecord.categoryName());
        MovieBuilder builder = new MovieBuilder()
                .title(legacyRecord.name())
                .releaseYear(legacyRecord.yearReleased())
                .genre(legacyRecord.categoryName())
                .rating(7.0);
        return MovieFactory.create(builder, type);
    }

    private MovieType mapType(String categoryName) {
        if (categoryName == null) throw new InvalidMovieDataException("Kategoria jest wymagana.");
        return switch (categoryName.trim().toLowerCase()) {
            case "documentary", "dokument" -> MovieType.DOCUMENTARY;
            case "animation", "animacja" -> MovieType.ANIMATION;
            case "feature film", "fabularny" -> MovieType.FEATURE_FILM;
            default -> throw new InvalidMovieDataException("Nieznana kategoria: " + categoryName);
        };
    }
}
