package com.example.moviemanager.composite;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Wzorzec: Composite
public class MovieLeaf implements MovieComponent {
    private final Movie movie;

    public MovieLeaf(Movie movie) { this.movie = movie; }

    @Override
    public String name() { return movie.title(); }

    @Override
    public List<Movie> movies() { return List.of(movie); }
}
