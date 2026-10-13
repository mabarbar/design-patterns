package com.example.moviemanager.decorator;

import com.example.moviemanager.domain.Movie;
import java.util.List;

// Wzorzec: Decorator — wspólny interfejs bazowego rekomendatora i dekoratora.
public interface MovieRecommendationService {
    List<Movie> recommend(List<Movie> movies, String genre);
}
