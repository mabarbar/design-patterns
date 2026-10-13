package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;
import java.util.List;
import java.util.stream.Collectors;

public class TextMovieExporter extends AbstractMovieExporter {
    @Override
    protected String format(List<Movie> movies) {
        return movies.stream()
                .map(movie -> "%s;%d;%s;%.1f;%s".formatted(
                        movie.title(), movie.releaseYear(), movie.genre(), movie.rating(), movie.type()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
