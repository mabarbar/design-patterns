package com.example.moviemanager.command;

import java.util.ArrayDeque;
import java.util.Deque;

// Wzorzec: Command — historia trzyma wykonane polecenia na stosie i cofa ostatnie.
public class CommandHistory {
    private final Deque<MovieCommand> history = new ArrayDeque<>();

    public void execute(MovieCommand command) {
        command.execute();
        history.push(command);
    }

    public String undoLast() {
        if (history.isEmpty()) throw new IllegalStateException("Brak operacji do cofnięcia.");
        MovieCommand command = history.pop();
        command.undo();
        return command.description();
    }

    public void clear() { history.clear(); }
}
