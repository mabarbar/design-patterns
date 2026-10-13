package com.example.moviemanager.functional;

import com.example.moviemanager.domain.Movie;

@FunctionalInterface
public interface MoviePredicate {
    boolean test(Movie movie);
}
