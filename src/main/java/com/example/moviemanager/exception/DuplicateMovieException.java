package com.example.moviemanager.exception;

// Zasada: typowany wyjątek rozróżnia konflikt dodawania filmu od błędnych danych.
public class DuplicateMovieException extends IllegalStateException {
    public DuplicateMovieException(String message) { super(message); }
}
