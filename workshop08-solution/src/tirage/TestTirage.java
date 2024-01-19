package tirage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestTirage {

	public static void main(String[] args) {

		// Test partie 1
		Tirage tirage = new Tirage("premier");
		for (int i = 0; i < 100; i++) {
			Participant participant = new Participant("nom " + i, "prenom " + i, "mail " + i);
			tirage.ajouter(participant);
		}
		Participant participantPasDansLeTirage = new Participant("PasDansTirage", "PasDansTirage", "PasDansTirage");

		Participant[] arrayParticipants = (Participant[]) tirage.getParticipants().toArray(new Participant[0]);
		System.out.println(tirage.ajouterGroupe(arrayParticipants[0], arrayParticipants[1], arrayParticipants[2])+" --> attendu : true");
		System.out.println(tirage.ajouterGroupe(arrayParticipants[10], arrayParticipants[11], arrayParticipants[2], arrayParticipants[12])+" --> attendu : true");
		System.out.println(tirage.ajouterGroupe(arrayParticipants[0])+" --> attendu : false");
		System.out.println(tirage.ajouterGroupe(arrayParticipants[0],arrayParticipants[0])+" --> attendu : false");
		System.out.println(tirage.ajouterGroupe(participantPasDansLeTirage)+" --> attendu : false");

		// Test partie 2
		tirage.preparerTirage();
		Thread tirageThread = tirage.creerThread();
		Thread tirageThread2 = tirage.creerThread();
		Thread tirageThread3 = tirage.creerThread();
		tirageThread.start();
		tirageThread2.start();
		tirageThread3.start();

		try {
			tirageThread.join();
			tirageThread2.join();
			tirageThread3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tirage.getResultats().forEach((key, value) -> System.out.println(((Participant) key).getMail() + " offre à " + ((Participant) value).getMail()));
		if(estValide(tirage, tirage.getResultats())) {
			System.out.println("Ce tirage est valide");
		} else {
			System.out.println("Ce tirage n'est pas valide : certains participants ont été tirés plusieurs fois, ou certains participants tirés ont un groupe en commun avec le participants qui les a tirés.");
		}

	}

	private static boolean estValide(Tirage tirage, Map<Participant, Participant> resultats) {
		HashSet<Participant> dejaVus = new HashSet<>();
		Set<Groupe> groupes = tirage.getGroupes();

		for(Map.Entry<Participant, Participant>e : resultats.entrySet()) {
			//doublons?
			if(dejaVus.contains(e.getValue())) return false;
			dejaVus.add(e.getValue());

			//cachuète dans un même groupe ?
			Set<Groupe> groupesK = groupes.stream().filter(g -> g.contient(e.getKey().getMail())).collect(Collectors.toSet());
			Set<Groupe> groupesV = groupes.stream().filter(g -> g.contient(e.getValue().getMail())).collect(Collectors.toSet());
			for (Groupe g : groupesV) {
				if(groupesK.contains(g)) return false;
			}
		}
		return true;
	}
}
