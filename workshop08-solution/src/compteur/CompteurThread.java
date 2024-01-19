package compteur;

public class CompteurThread extends Thread {

    private final String nom;
    private final int max;

    //Cette variable de classe permet de retenir quel CompteurThread
    //a fini de compter le premier.
    private static CompteurThread gagnant;

    public CompteurThread(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

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
        System.out.println(nom + " a fini de compter");
        synchronized (CompteurThread.class) {
            if(gagnant == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(nom + " est le(la) gagnant(e)");
                gagnant = this;
            }
        }
    }

    public static CompteurThread getGagnant() {
        return gagnant;
    }
}
