package com.example.moviemanager.domain;

import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.exception.InvalidMovieDataException;
import com.example.moviemanager.prototype.MoviePrototype;

import java.util.Objects;
import java.util.UUID;

public class Movie implements MoviePrototype {
    private final UUID id;
    private final String title;
    private final int releaseYear;
    private final String genre;
    private final double rating;
    private final MovieType type;

    // Zasada: SRP — Movie przechowuje i waliduje dane domenowe jednego filmu.
    public Movie(MovieBuilder builder) {
        this(builder, Objects.requireNonNull(builder, "Builder jest wymagany.").type());
    }

    protected Movie(MovieBuilder builder, MovieType type) {
        Objects.requireNonNull(builder, "Builder jest wymagany.");
        this.id = UUID.randomUUID();
        this.title = requireText(builder.title(), "Tytuł nie może być pusty.");
        this.genre = requireText(builder.genre(), "Gatunek nie może być pusty.");
        this.releaseYear = validateYear(builder.releaseYear());
        this.rating = validateRating(builder.rating());
        this.type = Objects.requireNonNull(type, "Typ filmu jest wymagany.");
    }

    private String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidMovieDataException(message);
        }
        return value.trim();
    }

    private int validateYear(int year) {
        if (year < 1888 || year > 2100) {
            throw new InvalidMovieDataException("Rok premiery musi mieścić się w zakresie 1888–2100.");
        }
        return year;
    }

    private double validateRating(double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 10.0) {
            throw new InvalidMovieDataException("Ocena musi mieścić się w zakresie 0–10.");
        }
        return value;
    }

    public UUID id() { return id; }
    public String title() { return title; }
    public int releaseYear() { return releaseYear; }
    public String genre() { return genre; }
    public double rating() { return rating; }
    public MovieType type() { return type; }

    protected MovieBuilder copyBuilder() {
        return new MovieBuilder().title(title).releaseYear(releaseYear)
                .genre(genre).rating(rating).type(type);
    }

    // Wzorzec: Prototype — kopiuje dane do nowego obiektu z nowym UUID.
    @Override
    public Movie clonePrototype() {
        return new Movie(copyBuilder());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Movie movie)) return false;
        return id.equals(movie.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return "%s (%d) | %s | %.1f/10 | %s | %s".formatted(
                title, releaseYear, genre, rating, type, id);
    }
}
