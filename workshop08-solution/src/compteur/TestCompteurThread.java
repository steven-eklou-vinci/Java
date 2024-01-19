package compteur;

public class TestCompteurThread {

	public static void main(String[] args) {
		CompteurThread[] compteurs = { new CompteurThread("Bolt", 10), new CompteurThread("Jakson", 10), new CompteurThread("Robert", 10), new CompteurThread("Stéphanie", 10) };

		for(int i = 0; i < compteurs.length; i++) {
			compteurs[i].start();
		}

		for(int i = 0; i < compteurs.length; i++) {
			try {
				compteurs[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Le gagnant est " + CompteurThread.getGagnant().getNom());
	}

}
