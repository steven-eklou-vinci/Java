package main;

import domaine.Etudiant;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExerciceFichiersAvecSupplier {

    public static void main(String[] args) {

        // Supplier of streams
        Supplier<Stream<String>> linesSupplier = () -> {
            try {
                return Files.lines(Paths.get("etudiants.csv"), Charset.forName("Cp1252"));
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new UncheckedIOException(e1);
            }
        };

        ExerciceFichiersAvecSupplier main = new ExerciceFichiersAvecSupplier(linesSupplier);
        main.run();
    }

    /**
     * Un supplier de lignes de csv. Chaque fois qu'on appelle get() sur cet objet, on obtient un nouveau stream.
     */
    private Supplier<Stream<String>> linesSupplier;


    public ExerciceFichiersAvecSupplier(
            Supplier<Stream<String>> linesSupplier    ) {
        this.linesSupplier = linesSupplier;
    }

    private void run() {
        this.point1();
        this.point2a();
        this.point2b();
    }

    private void point1() {
        System.out.println("1 -> ");
        try (Stream<String> lines = linesSupplier.get()) {
            String student1 = lines.findFirst().get();
            System.out.println(student1);
        } catch (UncheckedIOException e) {
            e.printStackTrace();
        }
    }

    private void point2a() {
        System.out.println("\nEx 2a\n=====");
        try (Stream<String> lines = linesSupplier.get()) {
            List<Etudiant> students = lines
                    .map(line -> new Etudiant(line))
                    .collect(Collectors.toList());
            System.out.println(students);
        } catch (UncheckedIOException e) {
            e.printStackTrace();
        }
    }

    private void point2b() {
        System.out.println("\nEx 2b\n=====");
        try (Stream<String> lines = linesSupplier.get()) {
            List<Etudiant> students = lines
                    .map(line -> line.split(";"))
                    .map(champs -> new Etudiant(champs[3], champs[0]))
                    .collect(Collectors.toList());
            System.out.println(students);
        } catch (UncheckedIOException e) {
            e.printStackTrace();
        }
    }


}
