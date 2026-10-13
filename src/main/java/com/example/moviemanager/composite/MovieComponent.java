package com.example.moviemanager.composite;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Wzorzec: Composite — pojedynczy film i kolekcja udostępniają wspólny kontrakt
public interface MovieComponent {
    String name();
    List<Movie> movies();
}
