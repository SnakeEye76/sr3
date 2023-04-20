package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;

public interface Event {
	
	<T extends EventProzessor> T accept(T prozessor, Character character);

}
