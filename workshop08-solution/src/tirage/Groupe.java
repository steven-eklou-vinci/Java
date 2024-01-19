package tirage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Groupe {
	
	private Set<Participant> participants = new HashSet<>();

	public boolean ajouter(Participant participant) {
		return this.participants.add(participant);
	}

	public boolean supprimer(Participant participant) {
		return this.participants.remove(participant);
	}

	public boolean contient(String mail) {
		return participants.stream().anyMatch(m -> m.getMail().equals(mail));
	}

	public Set<Participant> getMembres() {
		return Collections.unmodifiableSet(this.participants);
	}

}