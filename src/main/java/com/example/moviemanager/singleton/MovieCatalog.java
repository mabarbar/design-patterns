package com.example.moviemanager.singleton;

import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.exception.DuplicateMovieException;
import com.example.moviemanager.exception.MovieNotFoundException;
import com.example.moviemanager.iterator.MovieCatalogIterator;
import com.example.moviemanager.memento.CatalogMemento;
import com.example.moviemanager.observer.CatalogEvent;
import com.example.moviemanager.observer.CatalogObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Wzorzec: Singleton — prywatny konstruktor i jedna instancja wspólnego katalogu.
public final class MovieCatalog implements Iterable<Movie> {
    private static final MovieCatalog INSTANCE = new MovieCatalog();
    private final Map<UUID, Movie> movies = new LinkedHashMap<>();
    private final List<CatalogObserver> observers = new ArrayList<>();

    private MovieCatalog() { }

    public static MovieCatalog getInstance() {
        return INSTANCE;
    }

    public synchronized void addObserver(CatalogObserver observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    public synchronized void add(Movie movie) {
        if (movies.containsKey(movie.id())) {
            throw new DuplicateMovieException("Film o tym UUID już istnieje.");
        }
        movies.put(movie.id(), movie);
        notifyObservers(CatalogEvent.ADDED, movie);
    }

    public synchronized void remove(UUID id) {
        Movie removed = movies.remove(id);
        if (removed == null) throw new MovieNotFoundException("Nie znaleziono filmu: " + id);
        notifyObservers(CatalogEvent.REMOVED, removed);
    }

    public synchronized List<Movie> snapshotMovies() {
        return new ArrayList<>(movies.values());
    }

    // Wzorzec: Memento — Originator tworzy zapis swojego stanu.
    public synchronized CatalogMemento saveMemento() {
        return new CatalogMemento(snapshotMovies());
    }

    // Wzorzec: Memento — odtwarza dane zapisane w migawce.
    public synchronized void restoreMemento(CatalogMemento memento) {
        movies.clear();
        for (Movie movie : memento.savedMovies()) movies.put(movie.id(), movie);
        notifyObservers(CatalogEvent.RESTORED, null);
    }

    // Wzorzec: Iterator — katalog wystawia własny iterator po kopii bieżących wartości.
    @Override
    public synchronized Iterator<Movie> iterator() {
        return new MovieCatalogIterator(snapshotMovies());
    }

    private void notifyObservers(CatalogEvent event, Movie movie) {
        for (CatalogObserver observer : List.copyOf(observers)) {
            observer.onCatalogChanged(event, movie);
        }
    }
}
