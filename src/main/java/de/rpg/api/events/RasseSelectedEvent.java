package de.rpg.api.events;

import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.RassenModifikatoren;
import de.rpg.view.ChangeRequest;
import lombok.Getter;

@Getter
public class RasseSelectedEvent extends CreationEvent{
	
	private final RassenModifikatoren rasse;
	
	public RasseSelectedEvent(ChangeRequest request, CharakterErschaffung character) {
		super(character);
		rasse = RassenModifikatoren.valueOf(request.getValue());
	}
}
