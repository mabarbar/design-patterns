package com.example.moviemanager.factory;

import org.junit.jupiter.api.Test;
import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;
import com.example.moviemanager.domain.FeatureFilmMovie;
import com.example.moviemanager.domain.DocumentaryMovie;
import com.example.moviemanager.domain.AnimatedMovie;

import static org.junit.jupiter.api.Assertions.*;

class MovieFactoryTest {
    private Movie create(MovieType type) {
        return MovieFactory.create(new MovieBuilder().title("Test movie").releaseYear(2020)
                .genre("Drama").rating(8.0), type);
    }

    @Test void createsFeatureFilm() { assertInstanceOf(FeatureFilmMovie.class, create(MovieType.FEATURE_FILM)); }
    @Test void createsDocumentary() { assertInstanceOf(DocumentaryMovie.class, create(MovieType.DOCUMENTARY)); }
    @Test void createsAnimation() { assertInstanceOf(AnimatedMovie.class, create(MovieType.ANIMATION)); }
    @Test void preservesBuilderValues() { assertEquals("Test movie", create(MovieType.FEATURE_FILM).title()); }
    @Test void rejectsMissingType() {
        assertThrows(IllegalArgumentException.class, () -> MovieFactory.create(new MovieBuilder(), null));
    }
}
