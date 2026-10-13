package com.example.moviemanager.domain;

import com.example.moviemanager.builder.MovieBuilder;

// Wzorzec: Factory — konkretna reprezentacja filmu fabularnego tworzona przez MovieFactory.
public final class FeatureFilmMovie extends Movie {
    public FeatureFilmMovie(MovieBuilder builder) { super(builder, MovieType.FEATURE_FILM); }

    @Override
    public Movie clonePrototype() { return new FeatureFilmMovie(copyBuilder()); }
}
