package de.rpg.business.attribute;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.CreationAttributeChangeEvent;
import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.character.ErrorTyp;
import de.rpg.character.Fertigkeit;
import de.rpg.character.RassenCharakteristik;
import de.rpg.character.ValidationError;
import de.rpg.character.ausruestung.Ausruestung;
import de.rpg.erschaffung.AttributTyp;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.view.AttributIds;
import de.rpg.view.CreationIds;
import de.rpg.view.Option;

@SessionScope
@Service
public class AttributProzessor {
	
	private final EvetProzessorErgebnisSender sender;
	
	public AttributProzessor(EvetProzessorErgebnisSender sender) {
		super();
		this.sender = sender;
	}

	@EventListener(CreationAttributeChangeEvent.class)
	public void prozessiere(CreationAttributeChangeEvent event) {
		event.getCharacter().getAttributes().put(event.getAttribute(), event.getNewValue());
		Set<ValidationError> validationErrors = validiereAttribute(event.getCharacter());
		sender.sende(GenerischesProzessErgebnis.builder()
				.id(AttributIds.getDisplayFor(event.getId()))
				.value(event.getCharacter().getModified(event.getAttribute()).toString())
				.errors(validationErrors)
				.build());
		sender.sende(GenerischesProzessErgebnis.builder()
				.id(CreationIds.ATTRIBUTS_PRIO_DISPLAY)
				.value(berechneAttributspunkte(event.getCharacter(), event.getCharacter().getPrioritaeten().get(PrioTyp.ATTRIBUTE).getAttributPunkte()))
				.build());
	}

	@EventListener(PrioSelectedEvent.class)
	public void prozessiere(PrioSelectedEvent event) {
		event.getCharacter().getPrioritaeten().put(event.getTyp(), event.getPrioritaet());
		Set<ValidationError> validiereAttribute = validiereAttribute(event.getCharacter(), event.getPrioritaet());
		if(PrioTyp.ATTRIBUTE == event.getTyp()) {
			sender.sende(GenerischesProzessErgebnis.builder()
					.id(CreationIds.ATTRIBUTS_PRIO_DISPLAY)
					.value(berechneAttributspunkte(event.getCharacter(), event.getPrioritaet().getAttributPunkte()))
					.errors(validiereAttribute)
					.build());
		} else if(PrioTyp.FERTIGKEITEN == event.getTyp()) {
			sender.sende(GenerischesProzessErgebnis.builder()
					.id(CreationIds.FERTIGKEITEN_PRIO_DISPLAY)
					.value(berechneFertigkeitspunkte(event.getCharacter(), event.getPrioritaet().getFertigkeitPunkte()))
					.errors(validiereAttribute)
					.build());
		} else if(PrioTyp.RESSOURCEN == event.getTyp()) {
			sender.sende(GenerischesProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.value(berechneRessourcen(event.getCharacter(), event.getPrioritaet().getRessourcen()))
					.errors(validiereAttribute)
					.build());
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
			sender.sendeView(ViewProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.modelAndView(mav)
					.build());
		} else if(PrioTyp.RASSE == event.getTyp()) {
			ModelAndView mav = new ModelAndView("fragments/dropdown :: dropdown");
			mav.addObject("dropdownId", CreationIds.RASSE_AUSWAHL);
			mav.addObject("dropdownValue", event.getPrioritaet().getRassen().isEmpty() ? 
						RassenCharakteristik.MENSCH.getName() :  
							event.getPrioritaet().getRassen().iterator().next().getName());
			mav.addObject("optionNullText", "Rasse auswählen");
			mav.addObject("mitNullOptoin", false);
			mav.addObject("optionen", event.getPrioritaet().getRassen()
					.stream()
					.map(art -> Option.builder()
							.value(art.toString())
							.text(art.getName()).build())
					.toList());
			sender.sendeView(ViewProzessErgebnis.builder()
					.id(CreationIds.RESSOURCEN_PRIO_DISPLAY)
					.modelAndView(mav)
					.build());
		}
	}
	
	public static Set<ValidationError> validiereAttribute(CharakterErschaffung character){
		return validiereAttribute(character, null);
	}
	
	public static String berechneRessourcen(CharakterErschaffung character, int maxRessourcen) {
		int summe = character.getAusruestung().stream().map(Ausruestung::getKosten).mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxRessourcen).toString();
	}
	
	public static String berechneFertigkeitspunkte(CharakterErschaffung character, int maxFertigkeiten) {
		int summe = character.getFertigkeiten().stream().map(Fertigkeit::getWert).mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxFertigkeiten).toString();
	}
	
	public static String berechneAttributspunkte(CharakterErschaffung character, int maxAttribute) {
		int summe = character.getAttributes().values().stream().mapToInt(Integer::valueOf).sum();
		return new StringBuilder().append(summe).append(" / ").append(maxAttribute).toString();
	}

	private static Set<ValidationError> validiereAttribute(CharakterErschaffung character, Prioritaet prio){
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
}
