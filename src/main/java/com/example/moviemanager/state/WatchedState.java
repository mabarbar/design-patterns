package com.example.moviemanager.state;

import com.example.moviemanager.exception.InvalidStateTransitionException;

// Wzorzec: State — obejrzany film nie pozwala na powtórzenie tych samych przejść
public class WatchedState implements WatchState {
    @Override
    public WatchState startWatching() {
        throw new InvalidStateTransitionException("Film jest już oznaczony jako obejrzany.");
    }

    @Override
    public WatchState finishWatching() {
        throw new InvalidStateTransitionException("Film został już obejrzany.");
    }

    @Override
    public String label() { return "OBEJRZANY"; }
}
