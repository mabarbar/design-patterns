package com.example.moviemanager.exception;

public class InvalidStateTransitionException extends IllegalStateException {
    public InvalidStateTransitionException(String message) { super(message); }
}
