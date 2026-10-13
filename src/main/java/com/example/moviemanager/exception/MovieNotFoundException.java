package com.example.moviemanager.exception;

// Zasada: brak rekordu jest sygnalizowany wyjątkiem, a nie pustym kodem/identyfikatorem.
public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) { super(message); }
}
