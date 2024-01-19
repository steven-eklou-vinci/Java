package main;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.DelayedOperations.*;

public class ParallelStreams {

    private static List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

    public static void main(String[] args) {
        System.out.println("Temps d'exécution en série : " + serialMap());
        System.out.println("Temps d'exécution en parallèle : " + parallelMap());
        System.out.printf("Temps d'exécution lorsque l'on filtre avant : \n     en série : %d\n     en parallèle : %d\n",
                serialFilteredBefore(), parallelFilteredBefore());
        System.out.printf("Temps d'exécution lorsque l'on filtre après : \n     en série : %d\n     en parallèle : %d\n",
                serialFilteredAfter(), parallelFilteredAfter());
        System.out.println("Temps d'exécution lorsque les multiple de 10 sont très lents : " + randomMap());
    }

    /**
     * Exécute les trois fonctions de multiplication de l'interface DelayedOperations sur un stream
     * l'une après l'autre et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution des 3 opérations.
     */
    private static long serialMap() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream().map(DelayedOperations::fastMult2).collect(Collectors.toList()));
            System.out.println(numbers.stream().map(DelayedOperations::slowMult2).collect(Collectors.toList()));
            System.out.println(numbers.stream().map(DelayedOperations::ultraSlowMult2).collect(Collectors.toList()));
        });
    }

    /**
     * Exécute les trois fonctions de multiplication de l'interface DelayedOperations sur un stream
     * en parallèle et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution des 3 opérations.
     */
    private static long parallelMap() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream().parallel().map(DelayedOperations::fastMult2).collect(Collectors.toList()));
            System.out.println(numbers.stream().parallel().map(DelayedOperations::slowMult2).collect(Collectors.toList()));
            System.out.println(numbers.stream().parallel().map(DelayedOperations::ultraSlowMult2).collect(Collectors.toList()));
        });
    }

    /**
     * Exécute la fonction ultraSlowMult2 de DelayedOperations sur un stream filtré
     * et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution
     */
    private static long serialFilteredBefore() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream()
                    .filter(i -> i % 2 == 0)
                    .map(DelayedOperations::ultraSlowMult2)
                    .collect(Collectors.toList()));
        });
    }

    /**
     * Exécute la fonction ultraSlowMult2 de DelayedOperations sur un stream, puis le filtre
     * et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution
     */
    private static long serialFilteredAfter() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream()
                    .map(DelayedOperations::ultraSlowMult2)
                    .filter(i -> i % 4 == 0)
                    .collect(Collectors.toList()));
        });
    }

    /**
     * Exécute la fonction ultraSlowMult2 de DelayedOperations sur un stream parallèle filtré
     * et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution
     */
    private static long parallelFilteredBefore() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream()
                    .parallel()
                    .filter(i -> i % 2 == 0)
                    .map(DelayedOperations::ultraSlowMult2)
                    .collect(Collectors.toList()));
        });
    }

    /**
     * Exécute la fonction ultraSlowMult2 de DelayedOperations sur un stream parallèle, puis le filtre
     * et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution
     */
    private static long parallelFilteredAfter() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream()
                    .parallel()
                    .map(DelayedOperations::ultraSlowMult2)
                    .filter(i -> i % 4 == 0)
                    .collect(Collectors.toList()));
        });
    }

    /**
     * Exécute la fonction randomlySlowMult2 de DelayedOperations sur un stream parallèle
     * et affiche le temps que ce processus à pris pour s'exécuter.
     * @return le temps d'exécution
     */
    private static long randomMap() {
        return runAndRecordTime( () -> {
            System.out.println(numbers.stream()
                    .parallel()
                    .map(DelayedOperations::randomlySlowMult2)
                    .collect(Collectors.toList()));
        });
    }
}
