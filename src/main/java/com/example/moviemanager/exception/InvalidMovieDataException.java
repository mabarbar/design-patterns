package com.example.moviemanager.exception;

// Zasada: wyjątki opisują konkretny problem zamiast zwracania umownego kodu błędu.
public class InvalidMovieDataException extends IllegalArgumentException {
    public InvalidMovieDataException(String message) { super(message); }
}
