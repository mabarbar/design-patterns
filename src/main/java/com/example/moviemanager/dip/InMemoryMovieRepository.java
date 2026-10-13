package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.singleton.MovieCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryMovieRepository extends AbstractMovieRepository {
    private final MovieCatalog catalog;

    public InMemoryMovieRepository(MovieCatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    protected List<Movie> loadAll() {
        List<Movie> movies = new ArrayList<>();
        // Wzorzec: Iterator — for-each korzysta z iteratora
        for (Movie movie : catalog) {
            movies.add(movie);
        }
        return movies;
    }

    @Override
    protected void persist(Movie movie) {
        catalog.add(movie);
    }

    @Override
    protected void removeRecord(UUID id) {
        catalog.remove(id);
    }
}
