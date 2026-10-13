package com.example.moviemanager.badexample;

import java.util.ArrayList;
import java.util.List;

// CELowo zły przykład do zadania: łamie SRP, nazewnictwo, krótki kod, limit argumentów i abstrakcji.
// Ta klasa nie jest używana przez prawidłową część aplikacji.
public class BadMovieManagerExample {
    private final List<String> x = new ArrayList<>();
    private String y = "";
    private int z = 0;
    private double q = 0.0;

    // Zła metoda: wiele argumentów, walidacja, obliczenia, zapis i prezentacja w jednym miejscu.
    public void doEverything(String a, int b, String c, double d, boolean e, String f, int g) {
        // Tytuł zapisujemy do zmiennej o niejasnej nazwie, bo w tym antyprzykładzie nie dbamy o czytelność.
        if (a == null || a.isBlank()) {
            System.out.println("bad title");
        } else {
            y = a;
        }
        // Weryfikacja roku miesza się z logiką biznesową.
        if (b < 1888 || b > 2100) {
            System.out.println("bad year");
        } else {
            z = b;
        }
        // Weryfikacja gatunku i ustawianie stanu są w jednej metodzie.
        if (c != null && !c.isBlank()) {
            x.add(c);
        }
        // Ocena jest walidowana w tym samym miejscu, w którym wykonujemy zapis i raportowanie.
        if (d >= 0 && d <= 10) {
            q = d;
        }
        // Kolejny warunek reprezentuje niepowiązaną odpowiedzialność.
        if (e) {
            System.out.println("marked watched");
        } else {
            System.out.println("not watched");
        }
        // Zmienna f służy do robienia raportu, choć wcześniej metoda zapisywała już dane filmu.
        if (f != null) {
            System.out.println("report: " + f + " / " + y + " / " + z + " / " + q);
        }
        // Ostatni parametr modyfikuje licznik statystyczny.
        z = z + g;
        // Zapis i wyświetlanie powinny być oddzielnymi zadaniami.
        x.add(y + "-" + z + "-" + q);
        System.out.println("saved");
        System.out.println("count=" + x.size());
        // Długie, powtarzalne komentarze nie naprawiają złego podziału obowiązków.
        // W dobrze napisanym kodzie ta metoda delegowałaby do walidatora, repozytorium i raportera.
        // Tu jednak wszystkie odpowiedzialności celowo pozostają razem.
    }

    // Druga zbyt długa metoda jednocześnie wyszukuje, liczy, modyfikuje dane i wypisuje raport.
    public String anotherBadMethod(String a, String b, String c, int d, int e, int f) {
        String r = "";
        int n = 0;
        for (String s : x) {
            if (s.contains(a) || s.contains(b) || s.contains(c)) {
                n++;
                r = r + s + ";";
            }
            if (s.length() > d) {
                r = r + "LONG:" + s;
            }
            if (n > e) {
                System.out.println("limit reached");
            }
            // Przypadkowa modyfikacja danych w trakcie generowania odpowiedzi.
            if (f > 0 && s.startsWith("A")) {
                x.add("copy-" + s);
            }
        }
        System.out.println("found=" + n);
        System.out.println("output=" + r);
        return r;
    }

    // Mieszanie raportu, statystyk i zmiany danych w kolejnej metodzie.
    public void doMore(String a, String b, String c, String d, String e) {
        System.out.println("values: " + a + b + c + d + e);
        x.add(a);
        x.add(b);
        y = c;
        try {
            z = Integer.parseInt(d);
            q = Double.parseDouble(e);
        } catch (Exception exception) {
            System.out.println("something went wrong");
        }
        for (int i = 0; i < x.size(); i++) {
            System.out.println(i + "=" + x.get(i));
        }
        System.out.println("total=" + z + q);
    }
}
