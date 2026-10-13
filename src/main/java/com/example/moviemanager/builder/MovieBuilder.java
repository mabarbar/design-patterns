package com.example.moviemanager.builder;

import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;

// Wzorzec: Builder — umożliwia stopniowe tworzenie obiektu Movie z różnymi właściwościami.
public class MovieBuilder {
    private String title;
    private int releaseYear;
    private String genre;
    private double rating;
    private MovieType type;

    public MovieBuilder title(String title) { this.title = title; return this; }
    public MovieBuilder releaseYear(int releaseYear) { this.releaseYear = releaseYear; return this; }
    public MovieBuilder genre(String genre) { this.genre = genre; return this; }
    public MovieBuilder rating(double rating) { this.rating = rating; return this; }
    public MovieBuilder type(MovieType type) { this.type = type; return this; }

    public String title() { return title; }
    public int releaseYear() { return releaseYear; }
    public String genre() { return genre; }
    public double rating() { return rating; }
    public MovieType type() { return type; }

    public Movie build() {
        return new Movie(this);
    }
}
