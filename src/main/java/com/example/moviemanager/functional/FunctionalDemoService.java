package com.example.moviemanager.functional;

import com.example.moviemanager.domain.Movie;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

// Programowanie funkcyjne — trzy własne interfejsy implementowane lambdami i referencją do metody.
public class FunctionalDemoService {
    public void demonstrate(List<Movie> movies) {
        showHighlyRatedMovies(movies);
        showUniqueGenres(movies);
        showGenreCounts(movies);
    }

    private void showHighlyRatedMovies(List<Movie> movies) {
        MoviePredicate predicate = movie -> movie.rating() >= 8.0;
        MovieFormatter formatter = movie -> "%s (%.1f/10)".formatted(movie.title(), movie.rating());
        MovieAction printer = System.out::println;
        movies.stream().filter(predicate::test).map(formatter::format).forEach(printer::execute);
    }

    private void showUniqueGenres(List<Movie> movies) {
        Set<String> genres = movies.stream().map(Movie::genre)
                .collect(Collectors.toCollection(TreeSet::new));
        genres.stream().map(String::toUpperCase).forEach(System.out::println);
    }

    private void showGenreCounts(List<Movie> movies) {
        Map<String, Long> counts = movies.stream()
                .collect(Collectors.groupingBy(Movie::genre, Collectors.counting()));
        counts.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
