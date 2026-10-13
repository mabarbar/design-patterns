package com.example.moviemanager.dip;

public class ConsoleMovieNotificationService extends AbstractMovieNotificationService {
    @Override
    protected void deliver(String message) {
        System.out.println("[KATALOG] " + message);
    }
}
