package com.example.moviemanager.domain;

import org.junit.jupiter.api.Test;
import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.exception.InvalidMovieDataException;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Movie validMovie() {
        return new MovieBuilder().title("Arrival").releaseYear(2016).genre("Sci-Fi")
                .rating(7.9).type(MovieType.FEATURE_FILM).build();
    }

    @Test void buildsMovieWithExpectedValues() {
        Movie movie = validMovie();
        assertEquals("Arrival", movie.title());
        assertEquals(2016, movie.releaseYear());
        assertEquals("Sci-Fi", movie.genre());
        assertEquals(7.9, movie.rating(), 0.0001);
    }

    @Test void rejectsBlankTitle() {
        assertThrows(InvalidMovieDataException.class, () -> new MovieBuilder().title(" ")
                .releaseYear(2016).genre("Sci-Fi").rating(7.9).type(MovieType.FEATURE_FILM).build());
    }

    @Test void rejectsImpossibleReleaseYear() {
        assertThrows(InvalidMovieDataException.class, () -> new MovieBuilder().title("Arrival")
                .releaseYear(1700).genre("Sci-Fi").rating(7.9).type(MovieType.FEATURE_FILM).build());
    }

    @Test void rejectsRatingAboveTen() {
        assertThrows(InvalidMovieDataException.class, () -> new MovieBuilder().title("Arrival")
                .releaseYear(2016).genre("Sci-Fi").rating(11).type(MovieType.FEATURE_FILM).build());
    }

    @Test void prototypePreservesDataAndUsesNewId() {
        Movie original = validMovie();
        Movie copy = original.clonePrototype();
        assertNotEquals(original.id(), copy.id());
        assertEquals(original.title(), copy.title());
        assertEquals(original.type(), copy.type());
    }
}
