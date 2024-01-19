package compteur;

import java.util.concurrent.atomic.AtomicInteger;

public class CompteurRunnable implements Runnable {

	private String nom;
	private int max;

	private static AtomicInteger ordre = new AtomicInteger();

	@Override
	public void run() {
		for(int i = 0; i < max; ++i) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(nom + " : " + i);
		}
		System.out.println(nom + " a fini de compter en position " + ordre.incrementAndGet());
	}

	public CompteurRunnable(String nom, int max) {
		this.nom = nom;
		this.max = max;
	}

	public String getNom() {
		return nom;
	}

}
