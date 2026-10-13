package com.example.moviemanager.command;

import com.example.moviemanager.dip.MovieRepositoryPort;
import com.example.moviemanager.domain.Movie;

// Wzorzec: Command — zachowuje usunięty obiekt, aby móc go wstawić ponownie.
public class RemoveMovieCommand implements MovieCommand {
    private final MovieRepositoryPort repository;
    private final Movie movie;

    public RemoveMovieCommand(MovieRepositoryPort repository, Movie movie) {
        this.repository = repository;
        this.movie = movie;
    }

    @Override
    public void execute() { repository.delete(movie.id()); }

    @Override
    public void undo() { repository.save(movie); }

    @Override
    public String description() { return "Usunięcie: " + movie.title(); }
}
