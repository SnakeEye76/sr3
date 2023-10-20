package de.rpg.business.creation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.business.attribute.AttributProzessor;
import de.rpg.character.ErrorTyp;
import de.rpg.character.ValidationError;
import de.rpg.character.ausruestung.Ausruestung;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;

public class CreationProzessorUtil{
	
	private CreationProzessorUtil() {
		//empty Util C'tor
	}
	
	static String getDisplay(PrioSelectedEvent event, CharakterErschaffung character) {
		return getDisplay(event.getTyp(), character);
	}

	static String getDisplay(PrioTyp typ, CharakterErschaffung character) {
		switch(typ) {
			case RASSE:
				break;
			case MAGIE:
				break;
			case ATTRIBUTE:
				int allAttributPoints = character.getAttributes().values().stream().mapToInt(Integer::valueOf).sum();
				int attrRest = character.getPrioritaeten().get(PrioTyp.ATTRIBUTE).getAttributPunkte() - allAttributPoints;
				return String.valueOf(attrRest);
			case FERTIGKEITEN:
				int maxFertigkeiten = character.getPrioritaeten().get(PrioTyp.FERTIGKEITEN).getFertigkeitPunkte();
				return AttributProzessor.berechneFertigkeitspunkte(character, maxFertigkeiten);
			case RESSOURCEN:
				int allRessourcen = character.getAusruestung().stream().mapToInt(Ausruestung::getKosten).sum();
				int ressRest = character.getPrioritaeten().get(PrioTyp.RESSOURCEN).getRessourcen() - allRessourcen;
				return String.valueOf(ressRest);
		}
		return null;
	}

	static Set<ValidationError> validierePrioritaeten(CharakterErschaffung character) {
		Set<ValidationError> errors = new HashSet<>();
		Map<Prioritaet, List<Entry<PrioTyp, Prioritaet>>> grouped = character.getPrioritaeten().entrySet().stream().collect(Collectors.groupingBy(Entry::getValue));
		for(List<Entry<PrioTyp, Prioritaet>> group : grouped.values()) {
			if(group.size() > 1) {
				errors.add(ValidationError.builder()
						.typ(ErrorTyp.PRIO)
						.beschreibung(new StringBuilder("").toString())
						.build());
			}
		}
		return errors;
	}
}
