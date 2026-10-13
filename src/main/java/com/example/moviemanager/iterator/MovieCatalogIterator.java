package com.example.moviemanager.iterator;

import com.example.moviemanager.domain.Movie;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// Wzorzec: Iterator — ukrywa szczegóły przechowywania i udostępnia przechodzenie po elementach.
public class MovieCatalogIterator implements Iterator<Movie> {
    private final List<Movie> movies;
    private int index;

    public MovieCatalogIterator(List<Movie> movies) {
        this.movies = List.copyOf(movies);
    }

    @Override
    public boolean hasNext() {
        return index < movies.size();
    }

    @Override
    public Movie next() {
        if (!hasNext()) throw new NoSuchElementException("Brak kolejnego filmu.");
        return movies.get(index++);
    }
}
