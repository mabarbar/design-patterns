package com.example.moviemanager.state;

import com.example.moviemanager.exception.InvalidStateTransitionException;

// Wzorzec: State — w trakcie oglądania można zakończyć seans, ale nie rozpocząć go drugi raz
public class WatchingState implements WatchState {
    @Override
    public WatchState startWatching() {
        throw new InvalidStateTransitionException("Film jest już oznaczony jako oglądany.");
    }

    @Override
    public WatchState finishWatching() { return new WatchedState(); }

    @Override
    public String label() { return "W TRAKCIE"; }
}
