package com.example.moviemanager.state;

// Wzorzec: State — zachowanie zmienia się razem z obiektem reprezentującym aktualny stan.
public interface WatchState {
    WatchState startWatching();
    WatchState finishWatching();
    String label();
}
