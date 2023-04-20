package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.view.ChangeRequest;
import lombok.Getter;

@Getter
public class CreationFertigkeitChangeEvent implements CreationEvent{
	
	private final Long fertId;
	private final int newValue;
	private final String id;

	public CreationFertigkeitChangeEvent(ChangeRequest request) {
		this.id = request.getId();
		this.fertId = Long.parseLong(id.replace("-fert-input", ""));
		this.newValue = Integer.parseInt(request.getValue());
	}

	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}

}
