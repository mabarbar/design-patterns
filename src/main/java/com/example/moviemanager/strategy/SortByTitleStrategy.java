package com.example.moviemanager.strategy;

import com.example.moviemanager.domain.Movie;
import java.util.Comparator;
import java.util.List;

// Wzorzec: Strategy — jeden z niezależnych algorytmów sortowania.
public class SortByTitleStrategy implements MovieSortStrategy {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return movies.stream().sorted(Comparator.comparing(Movie::title, String.CASE_INSENSITIVE_ORDER)).toList();
    }
}
