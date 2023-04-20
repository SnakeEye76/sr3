package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.erschaffung.RassenModifikatoren;
import de.rpg.view.ChangeRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class RasseSelectedEvent implements CreationEvent{
	
	private final RassenModifikatoren rasse;
	
	public RasseSelectedEvent(ChangeRequest request) {
		rasse = RassenModifikatoren.valueOf(request.getValue());
	}

	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}
}
