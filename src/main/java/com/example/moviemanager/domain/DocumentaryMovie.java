package com.example.moviemanager.domain;

import com.example.moviemanager.builder.MovieBuilder;

// Wzorzec: Factory — konkretna reprezentacja filmu dokumentalnego.
public final class DocumentaryMovie extends Movie {
    public DocumentaryMovie(MovieBuilder builder) { super(builder, MovieType.DOCUMENTARY); }

    @Override
    public Movie clonePrototype() { return new DocumentaryMovie(copyBuilder()); }
}
