package tri_fusion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		int[] table = new Random().ints(100, 1, 1000).toArray();
		LocalDateTime start = LocalDateTime.now();
		Sorter sorter = new Sorter(table, 8);
		sorter.start();
		try {
			sorter.join();
		} catch (InterruptedException ignore) { }
		System.out.println("\n" + ChronoUnit.MILLIS.between(start, LocalDateTime.now()));

		for (int i = 0; i < table.length-1; i++) {
			if(table[i] > table[i+1]){
				System.out.println("Table non triée correctement");
				return;
			}
		}
		System.out.println("Table triée correctement");
	}

}
