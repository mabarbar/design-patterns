package com.example.moviemanager.memento;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Wzorzec: Memento — przechowuje niezmienną kopię stanu katalogu, bez metod jego edycji.
public final class CatalogMemento {
    private final List<Movie> savedMovies;

    public CatalogMemento(List<Movie> movies) {
        this.savedMovies = List.copyOf(movies);
    }

    public List<Movie> savedMovies() {
        return List.copyOf(savedMovies);
    }
}
