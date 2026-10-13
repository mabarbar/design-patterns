package com.example.moviemanager.dip;

// Zasada: DIP — obserwator zależy od kontraktu powiadamiania, nie od System.out
public interface MovieNotificationPort {
    void publish(String message);
}
