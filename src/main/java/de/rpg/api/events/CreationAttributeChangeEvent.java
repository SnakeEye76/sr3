package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.erschaffung.AttributTyp;
import de.rpg.view.ChangeRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class CreationAttributeChangeEvent implements CreationEvent{

	private final AttributTyp attribute;
	private final int newValue;
	private final String id;
	
	public CreationAttributeChangeEvent(ChangeRequest request) {
		this.attribute = AttributTyp.byId(request.getId());
		this.newValue = Integer.parseInt(request.getValue());
		this.id = request.getId();
	}

	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}
}
