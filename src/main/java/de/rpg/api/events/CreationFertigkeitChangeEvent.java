package de.rpg.api.events;

import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.view.ChangeRequest;
import lombok.Getter;

@Getter
public class CreationFertigkeitChangeEvent extends CreationEvent{
	
	private final Long fertId;
	private final int newValue;
	private final String id;

	public CreationFertigkeitChangeEvent(ChangeRequest request, CharakterErschaffung character) {
		super(character);
		this.id = request.getId();
		this.fertId = Long.parseLong(id.replace("-fert-input", ""));
		this.newValue = Integer.parseInt(request.getValue());
	}
}
