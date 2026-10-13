package com.example.moviemanager.factory;

import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.domain.AnimatedMovie;
import com.example.moviemanager.domain.DocumentaryMovie;
import com.example.moviemanager.domain.FeatureFilmMovie;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;
import com.example.moviemanager.exception.InvalidMovieDataException;

// Wzorzec: Factory — wybiera konkretną klasę filmu na podstawie typu, ukrywając konstruktor
public final class MovieFactory {
    private MovieFactory() { }

    public static Movie create(MovieBuilder builder, MovieType type) {
        if (builder == null || type == null) {
            throw new InvalidMovieDataException("Builder i typ filmu są wymagane.");
        }
        return switch (type) {
            case FEATURE_FILM -> new FeatureFilmMovie(builder);
            case DOCUMENTARY -> new DocumentaryMovie(builder);
            case ANIMATION -> new AnimatedMovie(builder);
        };
    }
}
