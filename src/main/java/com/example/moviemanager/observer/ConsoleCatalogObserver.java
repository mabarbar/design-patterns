package com.example.moviemanager.observer;

import com.example.moviemanager.dip.MovieNotificationPort;
import com.example.moviemanager.domain.Movie;

// Wzorzec: Observer — reaguje na zdarzenia katalogu i przekazuje tekst przez interfejs
public class ConsoleCatalogObserver implements CatalogObserver {
    private final MovieNotificationPort notificationPort;

    public ConsoleCatalogObserver(MovieNotificationPort notificationPort) {
        this.notificationPort = notificationPort;
    }

    @Override
    public void onCatalogChanged(CatalogEvent event, Movie movie) {
        String title = movie == null ? "zawartość katalogu" : movie.title();
        notificationPort.publish(event + ": " + title);
    }
}
