package de.rpg.business.creation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.MagieSelectedEvent;

@Component
@SessionScope
public class MagieSelectedEventListener {

	private final EvetProzessorErgebnisSender sender;
	
	public MagieSelectedEventListener(
			EvetProzessorErgebnisSender sender) {
		super();
		this.sender = sender;
	}

	@EventListener
	public void prozessiere(MagieSelectedEvent event) {
		if(event.getMagie() != event.getCharacter().getMagie()) {
			event.getCharacter().setMagie(event.getMagie());
		}
	}
}
