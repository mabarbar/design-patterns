package com.example.moviemanager.dip;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Zasada: DIP — aplikacja żąda eksportu przez kontrakt, nie zna formatu docelowego.
public interface MovieExporterPort {
    String export(List<Movie> movies);
}
