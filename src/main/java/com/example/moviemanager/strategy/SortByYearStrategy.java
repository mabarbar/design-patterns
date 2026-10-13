package com.example.moviemanager.strategy;

import com.example.moviemanager.domain.Movie;
import java.util.Comparator;
import java.util.List;

// Wzorzec: Strategy — sortowanie rosnąco według roku premiery.
public class SortByYearStrategy implements MovieSortStrategy {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return movies.stream().sorted(Comparator.comparingInt(Movie::releaseYear)).toList();
    }
}
