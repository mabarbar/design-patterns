package com.example.moviemanager.domain;

import com.example.moviemanager.builder.MovieBuilder;

// Wzorzec: Factory — konkretna reprezentacja animacji
public final class AnimatedMovie extends Movie {
    public AnimatedMovie(MovieBuilder builder) {
        super(builder, MovieType.ANIMATION);
    }

    @Override
    public Movie clonePrototype() {
        return new AnimatedMovie(copyBuilder());
    }
}
