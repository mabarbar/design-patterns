package com.example.moviemanager.composite;

import com.example.moviemanager.domain.Movie;

import java.util.ArrayList;
import java.util.List;

// Wzorzec: Composite — kolekcja może zawierać liście oraz kolejne kolekcje
public class MovieCollection implements MovieComponent {
    private final String name;
    private final List<MovieComponent> children = new ArrayList<>();

    public MovieCollection(String name) {
        this.name = name;
    }

    public void add(MovieComponent component) {
        children.add(component);
    }

    public void remove(MovieComponent component) {
        children.remove(component);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<Movie> movies() {
        return children.stream().flatMap(child -> child.movies().stream()).toList();
    }
}
