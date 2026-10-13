package com.example.moviemanager.console;

import org.springframework.boot.CommandLineRunner;
import com.example.moviemanager.adapter.LegacyMovieAdapter;
import com.example.moviemanager.adapter.LegacyMovieRecord;
import com.example.moviemanager.builder.MovieBuilder;
import com.example.moviemanager.composite.MovieCollection;
import com.example.moviemanager.composite.MovieLeaf;
import com.example.moviemanager.domain.Movie;
import com.example.moviemanager.domain.MovieType;
import com.example.moviemanager.functional.FunctionalDemoService;
import com.example.moviemanager.mediator.MovieManagerMediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

// Zasada: SRP — konsola odpowiada za wejście/wyjście i deleguje przypadki użycia do Mediatora.
public class MovieManagerConsole implements CommandLineRunner {
    private final MovieManagerMediator mediator;
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public MovieManagerConsole(MovieManagerMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void run(String... args) throws IOException {
        addSampleMoviesWhenEmpty();
        while (true) {
            printMenu();
            String choice = input.readLine();
            if (choice == null || choice.equals("0")) return;
            handleChoice(choice.trim());
        }
    }

    private void addSampleMoviesWhenEmpty() {
        if (!mediator.allMovies().isEmpty()) return;
        mediator.addMovie(createMovie(new MovieBuilder().title("The Shawshank Redemption").releaseYear(1994)
                .genre("Drama").rating(9.3), MovieType.FEATURE_FILM));
        mediator.addMovie(createMovie(new MovieBuilder().title("Interstellar").releaseYear(2014)
                .genre("Sci-Fi").rating(8.7), MovieType.FEATURE_FILM));
        mediator.addMovie(createMovie(new MovieBuilder().title("Free Solo").releaseYear(2018)
                .genre("Documentary").rating(8.1), MovieType.DOCUMENTARY));
    }

    private Movie createMovie(MovieBuilder builder, MovieType type) {
        // Wzorzec: Builder — ten scenariusz buduje Movie bezpośrednio, etapami.
        return builder.type(type).build();
    }

    private void printMenu() {
        System.out.println("\n=== MOVIE MANAGER ===");
        System.out.println("1 Lista  2 Dodaj  3 Szukaj  4 Rekomendacje  5 Status oglądania");
        System.out.println("6 Usuń  7 Cofnij  8 Zapisz migawkę  9 Przywróć migawkę");
        System.out.println("10 Import starego rekordu  11 Prototyp  12 Kolekcja Composite");
        System.out.println("13 Eksport  14 Demo strumieni  0 Koniec");
        System.out.print("Wybór: ");
    }

    // Zasada: krótkie metody — obsługa błędu i wybór operacji mają osobne metody.
    private void handleChoice(String choice) {
        try {
            dispatchChoice(choice);
        } catch (RuntimeException exception) {
            System.out.println("Błąd: " + exception.getMessage());
        }
    }

    private void dispatchChoice(String choice) {
        switch (choice) {
            case "1" -> showMovies();
            case "2" -> addMovie();
            case "3" -> searchMovies();
            case "4" -> showRecommendations();
            case "5" -> changeWatchStatus();
            case "6" -> deleteMovie();
            case "7" -> System.out.println(mediator.undoLastCommand());
            case "8" -> saveSnapshot();
            case "9" -> restoreSnapshot();
            case "10" -> importLegacyMovie();
            case "11" -> showPrototype();
            case "12" -> showComposite();
            case "13" -> System.out.println(mediator.exportCatalog());
            case "14" -> new FunctionalDemoService().demonstrate(mediator.allMovies());
            default -> System.out.println("Nieznana opcja.");
        }
    }

    private void showMovies() throws RuntimeException {
        String criteria = readLine("Sortowanie (title/year/rating): ");
        mediator.sortedMovies(criteria).forEach(System.out::println);
    }

    private void addMovie() {
        String title = readLine("Tytuł: ");
        int year = Integer.parseInt(readLine("Rok: "));
        String genre = readLine("Gatunek: ");
        double rating = Double.parseDouble(readLine("Ocena 0-10: "));
        MovieType type = readMovieType();
        MovieBuilder builder = new MovieBuilder().title(title).releaseYear(year).genre(genre).rating(rating);
        Movie movie = createMovie(builder, type);
        mediator.addMovie(movie);
        System.out.println("Dodano: " + movie);
    }

    private MovieType readMovieType() {
        System.out.println("Typ: 1 film fabularny, 2 dokument, 3 animacja");
        return switch (readLine("Typ: ")) {
            case "1" -> MovieType.FEATURE_FILM;
            case "2" -> MovieType.DOCUMENTARY;
            case "3" -> MovieType.ANIMATION;
            default -> throw new IllegalArgumentException("Nieznany typ filmu.");
        };
    }

    private void searchMovies() {
        mediator.searchByTitle(readLine("Fragment tytułu: ")).forEach(System.out::println);
    }

    private void showRecommendations() {
        mediator.recommendationsFor(readLine("Gatunek: ")).forEach(System.out::println);
    }

    private void changeWatchStatus() {
        UUID id = UUID.fromString(readLine("UUID filmu: "));
        String action = readLine("Operacja (start/finish/status): ");
        runWatchAction(id, action);
    }

    private void runWatchAction(UUID id, String action) {
        switch (action.toLowerCase()) {
            case "start" -> mediator.startWatching(id);
            case "finish" -> mediator.finishWatching(id);
            case "status" -> System.out.println(mediator.watchStatus(id));
            default -> throw new IllegalArgumentException("Użyj start, finish albo status.");
        }
        if (!action.equalsIgnoreCase("status")) System.out.println(mediator.watchStatus(id));
    }

    private void deleteMovie() {
        UUID id = UUID.fromString(readLine("UUID filmu do usunięcia: "));
        mediator.deleteMovie(id);
    }

    private void saveSnapshot() {
        mediator.saveSnapshot();
        System.out.println("Migawka zapisana.");
    }

    private void restoreSnapshot() {
        mediator.restoreSnapshot();
        System.out.println("Migawka przywrócona.");
    }

    private void importLegacyMovie() {
        LegacyMovieRecord record = new LegacyMovieRecord(
                readLine("Stara nazwa filmu: "),
                Integer.parseInt(readLine("Rok premiery: ")),
                readLine("Kategoria (documentary/animation/feature film): "));
        Movie movie = new LegacyMovieAdapter().adapt(record);
        mediator.addMovie(movie);
        System.out.println("Zaimportowano: " + movie);
    }

    private void showPrototype() {
        UUID id = UUID.fromString(readLine("UUID filmu do skopiowania: "));
        Movie prototype = mediator.findMovie(id);
        System.out.println("Oryginał: " + prototype);
        System.out.println("Kopia:    " + prototype.clonePrototype());
    }

    private void showComposite() {
        List<Movie> movies = mediator.allMovies();
        MovieCollection root = new MovieCollection("Katalog");
        MovieCollection featured = new MovieCollection("Wybrane filmy");
        if (!movies.isEmpty()) root.add(new MovieLeaf(movies.getFirst()));
        movies.stream().skip(1).map(MovieLeaf::new).forEach(featured::add);
        root.add(featured);
        System.out.println(root.name() + ": " + root.movies().size() + " filmów");
        root.movies().forEach(movie -> System.out.println(" - " + movie.title()));
    }

    private String readLine(String prompt) {
        try {
            System.out.print(prompt);
            return input.readLine();
        } catch (IOException exception) {
            throw new IllegalStateException("Nie można odczytać wejścia konsoli.", exception);
        }
    }
}
