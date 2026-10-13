package com.example.moviemanager.strategy;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Wzorzec: Strategy — wspólny kontrakt wymiennych algorytmów sortowania.
public interface MovieSortStrategy {
    List<Movie> sort(List<Movie> movies);
}
