package de.rpg.business.attribute;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import de.rpg.api.events.CreationAttributeChangeEvent;
import de.rpg.api.events.CreationFertigkeitChangeEvent;
import de.rpg.api.events.Event;
import de.rpg.api.events.FertigkeitHinzugefuegt;
import de.rpg.api.events.MagieSelectedEvent;
import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.api.events.RasseSelectedEvent;
import de.rpg.business.EventProzessor;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.ProzessErgebnis;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.character.Ausruestung;
import de.rpg.character.Character;
import de.rpg.character.ErrorTyp;
import de.rpg.character.Fertigkeit;
import de.rpg.character.ValidationError;
import de.rpg.erschaffung.AttributTyp;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.view.AttributIds;
import de.rpg.view.CreationIds;
import de.rpg.view.Option;

@SessionScope
@Service
public class AttributProzessor implements EventProzessor {
	
	private Stream<ProzessErgebnis> ergebnis;

	@Override
	public Stream<ProzessErgebnis> prozessiere(Event event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}
	
	public Stream<ProzessErgebnis> prozessiere(CreationAttributeChangeEvent event, Character character) {
		character.getAttributes().put(event.getAttribute(), event.getNewValue());
		Set<ValidationError> validationErrors = validiereAttribute(character);
		ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
				.id(AttributIds.getDisplayFor(event.getId()))
				.value(character.getModified(event.getAttribute()).toString())
				.errors(validationErrors)
				.build(),
				GenerischesProzessErgebnis.builder()
				.id(CreationIds.ATTRIBUTS_PRIO_DISPLAY)
				.value(berechneAttributspunkte(character, character.getPrioritaeten().get(PrioTyp.ATTRIBUTE).getAttributPunkte()))
				.build());
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> getErgebnisse() {
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(PrioSelectedEvent event, Character character) {
		character.getPrioritaeten().put(event.getTyp(), event.getPrioritaet());
		Set<ValidationError> validiereAttribute = validiereAttribute(character, event.getPrioritaet());
		if(PrioTyp.ATTRIBUTE == event.getTyp()) {
			ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
					.id(CreationIds.ATTRIBUTS_PRIO_DISPLAY)
					.value(berechneAttributspunkte(character, event.getPrioritaet().getAttributPunkte()))
					.errors(validiereAttribute)
					.build());
			return ergebnis;
		} else if(PrioTyp.FERTIGKEITEN == event.getTyp()) {
			ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
					.id(CreationIds.FERTIGKEITEN_PRIO_DISPLAY)
					.value(berechneFertigkeitspunkte(character, event.getPrioritaet().getFertigkeitPunkte()))
					.errors(validiereAttribute)
					.build());
			return ergebnis;
		} else if(PrioTyp.RESSOURCEN == event.getTyp()) {
			ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.value(berechneRessourcen(character, event.getPrioritaet().getRessourcen()))
					.errors(validiereAttribute)
					.build());
			return ergebnis;
		} else if(PrioTyp.MAGIE == event.getTyp()) {
			ModelAndView mav = new ModelAndView("fragments/dropdown :: dropdown");
			mav.addObject("dropdownId", CreationIds.MAGIE_AUSWAHL);
			mav.addObject("dropdownValue", event.getPrioritaet().getMagie().iterator().next().getName());
			mav.addObject("optionNullText", "Magie-Art auswählen");
			mav.addObject("mitNullOptoin", false);
			mav.addObject("optionen", event.getPrioritaet().getMagie()
					.stream()
					.map(art -> Option.builder()
							.value(art.toString())
							.text(art.getName()).build())
					.toList());
			ergebnis = Stream.of(ViewProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.modelAndView(mav)
					.build());
		} else if(PrioTyp.RASSE == event.getTyp()) {
			ModelAndView mav = new ModelAndView("fragments/dropdown :: dropdown");
			mav.addObject("dropdownId", CreationIds.RASSE_AUSWAHL);
			mav.addObject("dropdownValue", event.getPrioritaet().getRassen().iterator().next().getName());
			mav.addObject("optionNullText", "Rasse auswählen");
			mav.addObject("mitNullOptoin", false);
			mav.addObject("optionen", event.getPrioritaet().getRassen()
					.stream()
					.map(art -> Option.builder()
							.value(art.toString())
							.text(art.getName()).build())
					.toList());
			ergebnis = Stream.of(ViewProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.modelAndView(mav)
					.build());
		}
		
		return ergebnis;
	}
	
	public static Set<ValidationError> validiereAttribute(Character character){
		return validiereAttribute(character, null);
	}
	
	public static String berechneRessourcen(Character character, int maxRessourcen) {
		int summe = character.getAusruestung().stream().map(Ausruestung::getKosten).mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxRessourcen).toString();
	}
	
	public static String berechneFertigkeitspunkte(Character character, int maxFertigkeiten) {
		int summe = character.getFertigkeiten().stream().map(Fertigkeit::getWert).mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxFertigkeiten).toString();
	}
	
	public static String berechneAttributspunkte(Character character, int maxAttribute) {
		int summe = character.getAttributes().values().stream().mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxAttribute).toString();
	}

	private static Set<ValidationError> validiereAttribute(Character character, Prioritaet prio){
		Set<ValidationError> errors = new HashSet<>();
		int summe = 0;
		for(Entry<AttributTyp, Integer> attribute : character.getAttributes().entrySet()) {
			if(attribute.getValue() > 6) {
				errors.add(ValidationError.builder()
						.typ(ErrorTyp.ATTRIBUTE)
						.beschreibung(new StringBuilder("Attribut ")
								.append(attribute.getKey().getLabel())
								.append(" ist zu hoch. Ohne Rassenmodifikation dürfen nur 6 Punkte verteilt sein.")
								.toString()).build());
			} else if(attribute.getValue() < 1) {
				errors.add(ValidationError.builder()
						.typ(ErrorTyp.ATTRIBUTE)
						.beschreibung(new StringBuilder("Attribut ")
								.append(attribute.getKey().getLabel())
								.append(" ist zu niedrig. Kein Attribut darf ohne Rassenmodifikation kleiner als 1 sein.")
								.toString()).build());
			}
			summe += attribute.getValue();
		}
		if(prio == null && character.getPrioritaeten().get(PrioTyp.ATTRIBUTE) == null) {
			errors.add(ValidationError.builder()
					.typ(ErrorTyp.ATTRIBUTE)
					.beschreibung(new StringBuilder("Noch keine ATtributs-Priorität ausgewählt").toString()).build());
		} else if(prio == null && character.getPrioritaeten().get(PrioTyp.ATTRIBUTE) == null) {
			errors.add(ValidationError.builder()
					.typ(ErrorTyp.PRIO)
					.beschreibung("Es sind noch nicht alle Prioritäten ausgewählt worden.").build());
		} else if(prio == null) {
			prio = character.getPrioritaeten().get(PrioTyp.ATTRIBUTE);
		} else if(summe > prio.getAttributPunkte()) {
			errors.add(ValidationError.builder()
					.typ(ErrorTyp.ATTRIBUTE)
					.beschreibung(new StringBuilder("Attribute zu hoch! Es wurden ")
							.append(summe).append(" Attributspunkte ausgegeben. Aber es stehen nur ")
							.append(prio.getAttributPunkte()).append(" zur Verfügung.").toString()).build());
		}
		return errors;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(RasseSelectedEvent event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(MagieSelectedEvent event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(FertigkeitHinzugefuegt event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(CreationFertigkeitChangeEvent event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}
}
