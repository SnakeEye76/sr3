package de.rpg.business;

import java.util.stream.Stream;

import de.rpg.api.events.CreationAttributeChangeEvent;
import de.rpg.api.events.CreationFertigkeitChangeEvent;
import de.rpg.api.events.Event;
import de.rpg.api.events.FertigkeitHinzugefuegt;
import de.rpg.api.events.MagieSelectedEvent;
import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.api.events.RasseSelectedEvent;
import de.rpg.character.Character;

public interface EventProzessor {
	
	Stream<ProzessErgebnis> getErgebnisse();

	Stream<ProzessErgebnis> prozessiere(Event event, Character character);
	
	Stream<ProzessErgebnis> prozessiere(CreationAttributeChangeEvent event, Character character);
	Stream<ProzessErgebnis> prozessiere(PrioSelectedEvent event, Character character);

	Stream<ProzessErgebnis> prozessiere(RasseSelectedEvent event, Character character);

	Stream<ProzessErgebnis> prozessiere(MagieSelectedEvent event, Character character);

	Stream<ProzessErgebnis> prozessiere(FertigkeitHinzugefuegt event, Character character);

	Stream<ProzessErgebnis> prozessiere(CreationFertigkeitChangeEvent event, Character character);
}
