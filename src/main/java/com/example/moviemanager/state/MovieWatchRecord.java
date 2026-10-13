package com.example.moviemanager.state;

import java.util.UUID;

// Wzorzec: State — rekord deleguje zmianę statusu do obiektu reprezentującego aktualny stan
public class MovieWatchRecord {
    private final UUID movieId;
    private WatchState state = new PlannedState();

    public MovieWatchRecord(UUID movieId) { this.movieId = movieId; }

    public UUID movieId() { return movieId; }
    public String status() { return state.label(); }
    public void start() { state = state.startWatching(); }
    public void finish() { state = state.finishWatching(); }
}
