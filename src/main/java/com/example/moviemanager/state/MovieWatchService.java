package com.example.moviemanager.state;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Zasada: SRP — usługa zarządza statusami, nie miesza ich z opisem filmu
public class MovieWatchService {
    private final Map<UUID, MovieWatchRecord> records = new HashMap<>();

    public void start(UUID movieId) {
        recordFor(movieId).start();
    }

    public void finish(UUID movieId) {
        recordFor(movieId).finish();
    }

    public String status(UUID movieId) {
        return recordFor(movieId).status();
    }

    private MovieWatchRecord recordFor(UUID movieId) {
        return records.computeIfAbsent(movieId, MovieWatchRecord::new);
    }
}
