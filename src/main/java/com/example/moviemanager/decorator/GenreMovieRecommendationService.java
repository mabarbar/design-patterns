package com.example.moviemanager.decorator;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Podstawowy rekomendator filtruje po gatunku i minimalnej ocenie.
public class GenreMovieRecommendationService implements MovieRecommendationService {
    @Override
    public List<Movie> recommend(List<Movie> movies, String genre) {
        return movies.stream()
                .filter(movie -> movie.genre().equalsIgnoreCase(genre))
                .filter(movie -> movie.rating() >= 6.0)
                .toList();
    }
}
