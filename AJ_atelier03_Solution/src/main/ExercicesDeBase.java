package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class ExercicesDeBase {

    /**
     * La liste de base de toutes les transactions.
     */
    private List<Transaction> transactions;

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        System.out.println("Les transactions " + transactions);
        ExercicesDeBase main = new ExercicesDeBase(transactions);
        main.run();
    }



    /**
     * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point de l'énoncé
     *
     * @param transactions la liste des transactions
     */
    public ExercicesDeBase(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Exécute chaque point de l'énoncé
     */
    public void run() {
        this.predicats1();
        this.predicats2();
        this.predicats3();
        this.map1();
        this.map2();
        this.map3();
        this.sort1();
        this.sort2();
        this.reduce1();
        this.reduce2();
    }

    private void predicats1() {
        System.out.println("predicats1");
        var s = transactions
                .stream()
                .filter(
                        t -> t.getYear() == 2011
                );
        System.out.println("juste la collection non fonctionnelle" + s);
        s.forEach(System.out::println);
    }

    private void predicats2() {

        System.out.println("predicats2");
        var s = transactions
                .stream()
                .filter(
                        t -> t.getValue() > 600
                );

        s.forEach(System.out::println);
    }


    private void predicats3() {

        System.out.println("predicats3");
        var s = transactions
                .stream()
                .filter(
                        t -> t.getTrader().getName().equals("Raoul")
                );

        s.forEach(System.out::println);
    }

    private void map1() {
        System.out.println("map1");
        List<String> transac2 = transactions
                .stream()
                .map(
                        trans -> trans.getTrader().getCity()
                )
                .distinct()
                .collect(
                        toList()
                );
        transac2.forEach(System.out::println);
    }

    private void map2() {
        System.out.println("map2");
        List<Trader> transac3 = transactions
                .stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(
                        trader -> trader.getCity().equals("Cambridge")
                )
                .collect(toList());
        transac3.forEach(System.out::println);
    }

    private void map3() {
        System.out.println("map3");
        var transac3 = transactions
                .stream()
                .map(Transaction::getTrader)
                .distinct()
                .map(Trader::getName)
                .collect(Collectors.joining(", "));
        System.out.println(transac3);
    }


    private void sort1() {
        System.out.println("sort1");
        var transcTriees = transactions
                .stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        transcTriees.forEach(System.out::println);
    }

    private void sort2() {
        System.out.println("sort2");
        var nomsTries = transactions
                .stream()
                .map(Transaction::getTrader)
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .map(Trader::getName)
                .collect(Collectors.joining(", "));
        System.out.println(nomsTries);
    }


    private void reduce1() {
        System.out.println("reduce1");
        Integer transac7 = transactions
                .stream()
                .map(
                        Transaction::getValue
                )
                .reduce(
                        0, Integer::max
                );
        System.out.println(transac7);
    }

    private void reduce2() {
        System.out.println("reduce2");
        Transaction transNeutre = new Transaction(null, 20000, Integer.MAX_VALUE);
        Transaction transac8 = transactions
                .stream()
                .reduce(
                        transNeutre, (t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2
                );
    //version alternative    
  /*  	Transaction transac8  = transactions.stream()
				.collect(Collectors.minBy(Comparator.comparing(Transaction ::getValue)))
				.get();*/
        System.out.println(transac8);
    }

}