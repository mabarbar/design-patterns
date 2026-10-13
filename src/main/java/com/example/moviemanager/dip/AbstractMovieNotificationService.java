package com.example.moviemanager.dip;

// Zasada: pojedyncza odpowiedzialność — walidacja wiadomości jest wspólna dla wszystkich implementacji, a dostarczenie wiadomości jest specyficzne dla konkretnej implementacji
public abstract class AbstractMovieNotificationService implements MovieNotificationPort {
    @Override
    public final void publish(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Treść powiadomienia nie może być pusta.");
        }
        deliver(message.trim());
    }

    protected abstract void deliver(String message);
}
