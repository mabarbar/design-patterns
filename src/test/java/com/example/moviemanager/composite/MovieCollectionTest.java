package com.example.moviemanager.composite;

import org.junit.jupiter.api.Test;
import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;

import static org.junit.jupiter.api.Assertions.*;

class MovieCollectionTest {
    private Movie movie(String title) {
        return new MovieBuilder().title(title).releaseYear(2020).genre("Drama")
                .rating(7.0).type(MovieType.FEATURE_FILM).build();
    }

    @Test void leafReturnsSingleMovie() { assertEquals(1, new MovieLeaf(movie("A")).movies().size()); }

    @Test void collectionCountsDirectChildren() {
        MovieCollection collection = new MovieCollection("Watchlist");
        collection.add(new MovieLeaf(movie("A")));
        collection.add(new MovieLeaf(movie("B")));
        assertEquals(2, collection.movies().size());
    }

    @Test void nestedCollectionIsFlattened() {
        MovieCollection root = new MovieCollection("Root");
        MovieCollection nested = new MovieCollection("Nested");
        nested.add(new MovieLeaf(movie("A")));
        root.add(nested);
        assertEquals("A", root.movies().getFirst().title());
    }

    @Test void exposesCollectionName() { assertEquals("Favorites", new MovieCollection("Favorites").name()); }

    @Test void removingChildRemovesMovieFromCollection() {
        MovieCollection collection = new MovieCollection("List");
        MovieLeaf leaf = new MovieLeaf(movie("A"));
        collection.add(leaf);
        collection.remove(leaf);
        assertTrue(collection.movies().isEmpty());
    }
}
