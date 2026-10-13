package com.example.moviemanager.command;

// Wzorzec: Command — operacja ma własny obiekt z wykonaniem, cofnięciem i opisem.
public interface MovieCommand {
    void execute();
    void undo();
    String description();
}
