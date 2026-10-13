package com.example.moviemanager.functional;

import com.example.moviemanager.domain.Movie;

@FunctionalInterface
public interface MovieFormatter {
    String format(Movie movie);
}
