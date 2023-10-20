package de.rpg.api.events;

import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.Magie;
import de.rpg.view.ChangeRequest;
import lombok.Getter;

@Getter
public class MagieSelectedEvent extends CreationEvent {
	
	private final Magie magie;

	public MagieSelectedEvent(ChangeRequest request, CharakterErschaffung character) {
		super(character);
		magie = Magie.valueOf(request.getValue());
	}

}
