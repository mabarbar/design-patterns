package com.example.moviemanager.state;

import com.example.moviemanager.exception.InvalidStateTransitionException;

// Wzorzec: State — w stanie PLANOWANY można rozpocząć oglądanie, ale nie można go zakończyć
public class PlannedState implements WatchState {
    @Override
    public WatchState startWatching() { return new WatchingState(); }

    @Override
    public WatchState finishWatching() {
        throw new InvalidStateTransitionException("Najpierw rozpocznij oglądanie filmu.");
    }

    @Override
    public String label() { return "PLANOWANY"; }
}
