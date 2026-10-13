package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;
import java.util.List;
import java.util.Objects;

// Zasada: krótkie metody — kontrola argumentu i formatowanie mają osobne role.
public abstract class AbstractMovieExporter implements MovieExporterPort {
    @Override
    public final String export(List<Movie> movies) {
        Objects.requireNonNull(movies, "Lista filmów jest wymagana.");
        return format(List.copyOf(movies));
    }

    protected abstract String format(List<Movie> movies);
}
