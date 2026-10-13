package com.example.moviemanager.adapter;

// Model imitujący dane z dawnego systemu: nazwy pól różnią się od modelu Movie/string zamiast enum
public record LegacyMovieRecord(String name, int yearReleased, String categoryName) { }
