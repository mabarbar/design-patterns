package com.example.moviemanager.observer;

import com.example.moviemanager.domain.Movie;

// Wzorzec: Observer — obserwator otrzymuje zdarzenie bez wywoływania go przez kod biznesowy
public interface CatalogObserver {
    void onCatalogChanged(CatalogEvent event, Movie movie);
}
