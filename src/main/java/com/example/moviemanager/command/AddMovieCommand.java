package com.example.moviemanager.command;

import com.example.moviemanager.dip.MovieRepositoryPort;
import com.example.moviemanager.domain.Movie;

// Wzorzec: Command — zapisuje dane potrzebne do wykonania i cofnięcia dodania filmu.
public class AddMovieCommand implements MovieCommand {
    private final MovieRepositoryPort repository;
    private final Movie movie;

    public AddMovieCommand(MovieRepositoryPort repository, Movie movie) {
        this.repository = repository;
        this.movie = movie;
    }

    @Override
    public void execute() { repository.save(movie); }

    @Override
    public void undo() { repository.delete(movie.id()); }

    @Override
    public String description() { return "Dodanie: " + movie.title(); }
}
