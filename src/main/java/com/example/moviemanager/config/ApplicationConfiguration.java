package com.example.moviemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.example.moviemanager.application.MovieManagementService;
import com.example.moviemanager.console.MovieManagerConsole;
import com.example.moviemanager.decorator.GenreMovieRecommendationService;
import com.example.moviemanager.decorator.MovieRecommendationService;
import com.example.moviemanager.decorator.TopRatedMovieRecommendationDecorator;
import com.example.moviemanager.dip.ConsoleMovieNotificationService;
import com.example.moviemanager.dip.InMemoryMovieRepository;
import com.example.moviemanager.dip.MovieExporterPort;
import com.example.moviemanager.dip.MovieNotificationPort;
import com.example.moviemanager.dip.MovieRepositoryPort;
import com.example.moviemanager.dip.TextMovieExporter;
import com.example.moviemanager.mediator.MovieManagerMediator;
import com.example.moviemanager.observer.CatalogObserver;
import com.example.moviemanager.observer.ConsoleCatalogObserver;
import com.example.moviemanager.singleton.MovieCatalog;
import com.example.moviemanager.state.MovieWatchService;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfiguration {
    // Zasada: DIP — serwisy aplikacyjne otrzymują abstrakcje; konfiguracja wybiera implementacje.
    @Bean
    public MovieCatalog movieCatalog() {
        return MovieCatalog.getInstance();
    }

    @Bean
    public MovieNotificationPort movieNotificationPort() {
        return new ConsoleMovieNotificationService();
    }

    @Bean
    public CatalogObserver catalogObserver(MovieCatalog catalog, MovieNotificationPort notifier) {
        CatalogObserver observer = new ConsoleCatalogObserver(notifier);
        catalog.addObserver(observer);
        return observer;
    }

    @Bean
    public MovieRepositoryPort movieRepositoryPort(MovieCatalog catalog) {
        return new InMemoryMovieRepository(catalog);
    }

    @Bean
    public MovieExporterPort movieExporterPort() {
        return new TextMovieExporter();
    }

    @Bean
    public MovieRecommendationService movieRecommendationService() {
        return new TopRatedMovieRecommendationDecorator(new GenreMovieRecommendationService());
    }

    @Bean
    public MovieManagementService movieManagementService(
            MovieRepositoryPort repository,
            MovieRecommendationService recommendations,
            MovieExporterPort exporter) {
        return new MovieManagementService(repository, recommendations, exporter);
    }

    @Bean
    public MovieWatchService movieWatchService() {
        return new MovieWatchService();
    }

    @Bean
    public MovieManagerMediator movieManagerMediator(
            MovieManagementService service,
            MovieCatalog catalog,
            MovieWatchService watchService) {
        return new MovieManagerMediator(service, catalog, watchService);
    }

    @Bean
    public MovieManagerConsole movieManagerConsole(MovieManagerMediator mediator) {
        return new MovieManagerConsole(mediator);
    }
}
