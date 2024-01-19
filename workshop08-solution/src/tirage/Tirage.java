package tirage;

import java.util.*;
import java.util.stream.Collectors;

public class Tirage {

	private String nom;
	private Set<Participant> participants;
	private Set<Groupe> groupes;
	private final Stack<Participant> participantSansCacahuete = new Stack<>();
	private final Set<Participant> participantsTires = new HashSet<>();

	private Map<Participant, Participant> resultats;

	public Tirage(String nom) {
		super();
		this.nom = nom;
		participants = new HashSet<>();
		groupes = new HashSet<>();
	}

	public String getNom() {
		return this.nom;
	}

	public Set<Participant> getParticipants() {
		return Collections.unmodifiableSet(this.participants);
	}

	public Set<Groupe> getGroupes() {
		return Collections.unmodifiableSet(this.groupes);
	}

	public Map<Participant, Participant> getResultats() {
		return Collections.unmodifiableMap(this.resultats);
	}

	/**
	 * Ajoute les membres en paramètres dans un nouveau groupe. Les membres en
	 * paramètres doivent se trouver dans le tirage (si pas renvoyer false). Il ne
	 * peut y avoir de groupe de 1 membre. Un membre ne peut pas être deux fois dans
	 * le même groupe.
	 */
	public boolean ajouterGroupe(Participant... participants) {
		if(participants.length < 2) return false;
		Groupe g = new Groupe();
		for(Participant p : participants) {
			if(!this.participants.contains(p)) return false;
			if(g.contient(p.getMail())) return false;
			g.ajouter(p);
		}
		groupes.add(g);
		return true;
	}

	/**
	 * ajoute un membre au tirage
	 */
	public boolean ajouter(Participant participant) {
		return participants.add(participant);
	}

	/**
	 * Initialise correctement les structures de données pour préparer au tirage
	 * Efface les données du tirage précédent.
	 */
	public void preparerTirage() {
		for(Participant participant : participants) {
			participantSansCacahuete.push(participant);
		}
		participantsTires.clear();
		resultats = new HashMap<>();
	}

	/**
	 * Crée un thread dont le nom est fourni en paramètre
	 */
	public Thread creerThread() {
		return new TirageThread();
	}

	/**
	 * Classe interne TirageThread : chaque thread effectue le tirage en parallèle.
	 */
	private class TirageThread extends Thread {

		/**
		 * Effectue le tirage d'un participant parmi ceux qui n'ont pas encore été tirés.
		 * Il faut veiller à ne pas tirer de participants des groupes du @participant.
		 * Il faut mettre la paire (@participant, participant tiré) dans la map des résultats.
		 *
		 * @param participant un participant à qui il faut attribuer une cacahuète.
		 * @throws TirageImpossibleException si le tirage est impossible
		 * Conseil : utilisez l'attribut membresTires de TriageImpl
		 */
		private void tirer(Participant participant) throws TirageImpossibleException
		{
			Set<Groupe> groupesInterdits = groupes.stream().filter(groupe -> groupe.contient(participant.getMail())).collect(Collectors.toSet());
			Participant participantTire;
			synchronized (participantsTires) {
				//Trouve les participants possibles à tirer
				List<Participant> participantsTirables = participants.stream()
						.filter(p -> {
							if(groupesInterdits.stream().anyMatch(g -> g.contient(p.getMail()))) return false;
							if(p == participant) return false;
							return !participantsTires.contains(p);
						}).collect(Collectors.toList());
				//Si aucun participant à tirer : exception
				if(participantsTirables.isEmpty()) throw new TirageImpossibleException("Pas de participant à tirer");

				//Choisit un participant au hasard parmi les paticipants possibles
				participantTire = participantsTirables.get((int) (Math.random()*participantsTirables.size()));
				participantsTires.add(participantTire);
			}
			resultats.put(participant, participantTire);
		}

		@Override
		public void run() {
			while (true) {
				Participant participant;
				synchronized (participantSansCacahuete) {
					if(participantSansCacahuete.empty()) {
						System.out.println(nom + " a terminé.");
						return;
					}
					participant = participantSansCacahuete.pop();
				}

				try {
					tirer(participant);
				} catch (TirageImpossibleException t) {
					t.printStackTrace();
				}

			}
		}
	}

}
