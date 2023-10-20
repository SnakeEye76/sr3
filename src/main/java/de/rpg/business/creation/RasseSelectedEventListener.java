package de.rpg.business.creation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.RasseSelectedEvent;

@Component
@SessionScope
public class RasseSelectedEventListener {

	private final EvetProzessorErgebnisSender sender;
	
	public RasseSelectedEventListener(
			EvetProzessorErgebnisSender sender) {
		super();
		this.sender = sender;
	}

	@EventListener
	public void prozessiere(RasseSelectedEvent event) {
		if(event.getRasse() != event.getCharacter().getRasse()) {
			event.getCharacter().setRasse(event.getRasse());
		}
	}
}
