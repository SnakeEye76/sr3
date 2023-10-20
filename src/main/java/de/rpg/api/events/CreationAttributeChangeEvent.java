package de.rpg.api.events;

import de.rpg.erschaffung.AttributTyp;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.view.ChangeRequest;
import lombok.Getter;

@Getter
public class CreationAttributeChangeEvent extends CreationEvent{

	private final AttributTyp attribute;
	private final int newValue;
	private final String id;
	
	public CreationAttributeChangeEvent(ChangeRequest request, CharakterErschaffung character) {
		super(character);
		this.attribute = AttributTyp.byId(request.getId());
		this.newValue = Integer.parseInt(request.getValue());
		this.id = request.getId();
	}
}
