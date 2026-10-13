# Movie Manager Balanced
## Menu

1. Lista filmów z wyborem sortowania.
2. Dodanie filmu.
3. Wyszukiwanie po fragmencie tytułu.
4. Rekomendacje według gatunku.
5. Status oglądania: `start`, `finish`, `status`.
6. Usunięcie filmu.
7. Cofnięcie ostatniego dodania/usunięcia.
8. Zapisanie migawki katalogu.
9. Przywrócenie migawki.
10. Import rekordu w starym formacie.
11. Utworzenie prototypu filmu (kopia nie jest automatycznie dodawana do katalogu).
12. Utworzenie zagnieżdżonej kolekcji Composite.
13. Eksport katalogu do tekstu rozdzielonego średnikami.
14. Demonstracja interfejsów funkcyjnych i przetwarzania 3 kolekcji strumieniami.
0. Wyjście.

## Wzorce konstrukcyjne i strukturalne

1. **Builder** — `MovieBuilder` i `MovieFactory` tworzą obiekty `Movie` za pomocą .build, przydatne przy wielu polach zamiast np konstruktora
2. **Factory** — `MovieFactory` tworzy różne typy filmów, np. `Animated`, `Documentary` na podstawie enum `MovieType`
3. **Singleton** — `MovieManagementService` jest singletonem, tworzy tylko jedną instancję w całej aplikacji, zarządza katalogiem i historią poleceń
4. **Prototype** — `MoviePrototype`, umożliwia klonowanie filmów bez znajomości szczegółów implementacji
5. **Adapter** — `LegacyMovieAdapter` umożliwia import filmów w "starym" formacie, konwertując je na nowy model
6. **Composite** — `MovieCollection` tworzy kolekcje filmów, pierwszy film tworzony jest w MovieLeaf, a pozostałe w podkolekcji
7. **Decorator** — `MovieRecommendationService` Dekorator deleguje pierwszą część pracy do GenreMovieRecommendationService. Bazowy serwis wybiera filmy o pasującym gatunku i ocenie co najmniej 6,0. Dekorator otrzymuje wyniki i sortuje je od najwyższej oceny. Zwraca maksymalnie pięć filmów.

## Wzorce czynnościowe

1. **Command** — `AddMovieCommand`, `CommandHistory` i `RemoveMovieCommand` implementują wzorzec Command, umożliwiając cofanie operacji dodawania i usuwania filmów
2. **Iterator** — `MovieIterator` umożliwia iterowanie po kolekcji filmów, ukrywając szczegóły implementacji kolekcji
3. **Mediator** — `MovieManagementService` pełni rolę Mediatora, zarządza interakcjami między różnymi komponentami, takimi jak sortowanie, zapis migawki, wyszukiwanie i cofanie operacji
4. **Memento** — `CatalogMemento` umożliwia zapisanie i przywrócenie stanu katalogu filmów za pomoca migawki
5. **Observer** — `ConsoleCatalogObserver` umożliwiaja powiadamianie o zmianach w katalogu filmów
6. **State** — implementuje wzorzec State, umożliwiając zmianę stanu oglądania filmu (np. `PlannedState`, `WatchingState`, `WatchedState`) i odpowiednie zachowanie w zależności od stanu - np. nie można zakończyć filmu, który nie został rozpoczęty
7. **Strategy** — `MovieSortStrategy` umożliwia wybór różnych strategii sortowania filmów np. według tytułu, roku lub oceny


## SOLID

1. **DIP — odwrócenie zależności.** `MovieManagementService` zależy od `MovieRepositoryPort`, `MovieRecommendationService` i `MovieExporterPort`. Trzy łańcuchy: interfejs → klasa abstrakcyjna → konkretna implementacja: `MovieRepositoryPort` → `AbstractMovieRepository` → `InMemoryMovieRepository`; `MovieNotificationPort` → `AbstractMovieNotificationService` → `ConsoleMovieNotificationService`; `MovieExporterPort` → `AbstractMovieExporter` → `TextMovieExporter`.
2. **Nazewnictwo klas, metod** Nazwy odzwierciedlają odpowiedzialność, np. `MovieManagementService`, `CatalogMemento`, `RemoveMovieCommand`.
3. **Metody do ok. 20 linii.** wyjątkiem celowym jest `BadMovieManagerExample`.
4. **Jedna odpowiedzialność.**
5. **Jeden poziom abstrakcji.** Metody wyższego poziomu delegują szczegóły do mniejszych metod/klas, np. serwis wybiera strategię zamiast samodzielnie implementować kilka sortowań.
6. **Maksymalnie trzy argumenty.** Konstrukcje wielopolowe używają Buildera, serwisy i komendy dostają niewiele zależności.
7. **Wyjątki zamiast kodów błędów.** `InvalidMovieDataException`, `DuplicateMovieException`, `MovieNotFoundException` i `InvalidStateTransitionException`

## Programowanie funkcyjne

`functional/MoviePredicate`, `MovieFormatter` i `MovieAction` są trzema własnymi interfejsami funkcyjnymi. `FunctionalDemoService` przekazuje do nich lambdy i referencję do metody. Strumienie są zastosowane na:

- `List<Movie>` — filtr i formatowanie filmów z oceną co najmniej 8;
- `Set<String>` — unikalne, posortowane nazwy gatunków;
- `Map<String, Long>` — zliczenie filmów w każdym gatunku.

## Trzy aspekty Spring AOP

- `ExecutionLoggingAspect` — `@Before`, rejestruje nazwę wywołanej metody serwisu.
- `ExecutionTimingAspect` — `@Around`, mierzy czas działania metody.
- `ErrorLoggingAspect` — `@AfterThrowing`, rejestruje wyjątki.

## Testy jednostkowe

- `MovieTest`
- `MovieFactoryTest`
- `MovieCollectionTest`
- `MovieManagementServiceTest`

## Zła klasa


 `example/bad/BadMovieManagerExample` jest celowo niepoprawną klasą ok. 100 linii - nie ma wpływu na działanie programu
