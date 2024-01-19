package abonnements;

public class Abonnement extends Thread {

    private final String nom;
    private final double prixMensuel;
    private final Compte compte;

    public Abonnement(String nom, double prixMensuel, Compte compte) {
        this.nom = nom;
        this.prixMensuel = prixMensuel;
        this.compte = compte;
    }

    @Override
    public void run() {
        while(true) {

            if (!compte.depenser(new Depense(prixMensuel, "domiciliation pour " + nom)))
                return;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
