package com.example.moviemanager.decorator;

import com.example.moviemanager.domain.Movie;
import java.util.Comparator;
import java.util.List;

// Wzorzec: Decorator — rozszerza rekomendator o ranking i limit bez zmiany jego klasy.
public class TopRatedMovieRecommendationDecorator implements MovieRecommendationService {
    private final MovieRecommendationService wrapped;

    public TopRatedMovieRecommendationDecorator(MovieRecommendationService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public List<Movie> recommend(List<Movie> movies, String genre) {
        return wrapped.recommend(movies, genre).stream()
                .sorted(Comparator.comparingDouble(Movie::rating).reversed())
                .limit(5)
                .toList();
    }
}
