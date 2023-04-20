package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.erschaffung.Magie;
import de.rpg.view.ChangeRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MagieSelectedEvent implements CreationEvent {
	
	private final Magie magie;

	public MagieSelectedEvent(ChangeRequest request) {
		magie = Magie.valueOf(request.getValue());
	}

	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}

}
