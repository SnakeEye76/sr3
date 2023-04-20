package de.rpg.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import de.rpg.api.events.Event;
import de.rpg.character.Character;

@SessionScope
@Service
public class HauptEventProzessor {

	private final List<EventProzessor> prozessoren;

	/**
	 * @param prozessoren
	 */
	public HauptEventProzessor(List<EventProzessor> prozessoren) {
		super();
		this.prozessoren = prozessoren;
	}
	
	public List<ProzessErgebnis> prozessEvent(Event event, Character character) {
		return prozessoren.stream()
				.map(prozessor -> event.accept(prozessor, character))
				.flatMap(EventProzessor::getErgebnisse)
				.toList();
	}
}
