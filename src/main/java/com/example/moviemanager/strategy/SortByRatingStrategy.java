package com.example.moviemanager.strategy;

import com.example.moviemanager.domain.Movie;
import java.util.Comparator;
import java.util.List;

// Wzorzec: Strategy — sortowanie malejąco według oceny.
public class SortByRatingStrategy implements MovieSortStrategy {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return movies.stream().sorted(Comparator.comparingDouble(Movie::rating).reversed()).toList();
    }
}
