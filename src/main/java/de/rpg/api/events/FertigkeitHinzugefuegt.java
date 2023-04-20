package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FertigkeitHinzugefuegt implements CreationEvent {
	
	private final FertigkeitSpezifikation fertigkeit;
	
	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}
}
