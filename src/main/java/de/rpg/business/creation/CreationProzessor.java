package de.rpg.business.creation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
import de.rpg.business.FertigkeitenService;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.ProzessErgebnis;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.business.attribute.AttributProzessor;
import de.rpg.character.Ausruestung;
import de.rpg.character.Character;
import de.rpg.character.ErrorTyp;
import de.rpg.character.Fertigkeit;
import de.rpg.character.ValidationError;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.view.CreationIds;

@SessionScope
@Service
public class CreationProzessor implements EventProzessor{
	
	private final FertigkeitenService fertigkeitenService;
	
	private Stream<ProzessErgebnis> ergebnis;

	public CreationProzessor(FertigkeitenService fertigkeitenService) {
		super();
		this.fertigkeitenService = fertigkeitenService;
	}

	@Override
	public Stream<ProzessErgebnis> getErgebnisse() {
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(Event event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(CreationAttributeChangeEvent event, Character character) {
		ergebnis = Stream.empty();
		return ergebnis;
	}

	@Override
	public Stream<ProzessErgebnis> prozessiere(PrioSelectedEvent event, Character character) {
		changeCharacter(event, character);
		Set<ValidationError> attributeErrors = AttributProzessor.validiereAttribute(character);
		Set<ValidationError> allErrors = validierePrioritaeten(character);
		allErrors.addAll(attributeErrors);
		if(PrioTyp.RASSE == event.getTyp() || PrioTyp.MAGIE == event.getTyp()) {
			Stream.of(ViewProzessErgebnis.builder()
					.id(event.getId())
					.modelAndView(null)
					.build());
		}
		
		ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
			.id(CreationIds.getDisplayFor(event.getId()))
			.value(getDisplay(event, character))
			.errors(allErrors)
			.build());
		return ergebnis;
	}

	private void changeCharacter(PrioSelectedEvent event, Character character) {
		character.getPrioritaeten().put(event.getTyp(), event.getPrioritaet());
	}
	
	private String getDisplay(PrioSelectedEvent event, Character character) {
		return getDisplay(event.getTyp(), character);
	}

	private String getDisplay(PrioTyp typ, Character character) {
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

	private Set<ValidationError> validierePrioritaeten(Character character) {
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

	@Override
	public Stream<ProzessErgebnis> prozessiere(RasseSelectedEvent event, Character character) {
		if(event.getRasse() != character.getRasse()) {
			character.setRasse(event.getRasse());
		}
		ergebnis = Stream.empty();
		return ergebnis;
	}
	
	@Override
	public Stream<ProzessErgebnis> prozessiere(MagieSelectedEvent event, Character character) {
		if(event.getMagie() != character.getMagie()) {
			character.setMagie(event.getMagie());
		}
		ergebnis = Stream.empty();
		return ergebnis;
	}
	
	@Override
	@SuppressWarnings("unlikely-arg-type")
	public Stream<ProzessErgebnis> prozessiere(FertigkeitHinzugefuegt event, Character character) {
		if(!character.getFertigkeiten().contains(event.getFertigkeit())) {
			Fertigkeit fertigkeit = fertigkeitenService.save(Fertigkeit.builder().spezifikation(event.getFertigkeit()).wert(0).build());
			character.getFertigkeiten().add(fertigkeit);
			ModelAndView mav = new ModelAndView("fragments/fertigkeitEdit :: fertigkeit-editieren");
			mav.addObject("fertigkeit", event.getFertigkeit().getName());
			mav.addObject("fertigkeitenId", fertigkeit.getId());
			ergebnis = Stream.of(ViewProzessErgebnis.builder()
					.modelAndView(mav)
					.build());
		}
		return ergebnis;
	}
	
	private Fertigkeit getFromCharakter(Character character, Long id) {
		Optional<Fertigkeit> optional = character.getFertigkeiten().stream().filter(fert -> fert.getId().equals(id)).findFirst();
		if(optional.isEmpty()) {
			Fertigkeit fert = fertigkeitenService.findById(id);
			character.getFertigkeiten().add(fert);
			return fert;
		} else {
			return optional.get();
		}
	}
	
	@Override
	public Stream<ProzessErgebnis> prozessiere(CreationFertigkeitChangeEvent event, Character character) {
		Fertigkeit fert = getFromCharakter(character, event.getFertId());
		fert.setWert(event.getNewValue());
		Set<ValidationError> allErrors = validierePrioritaeten(character);
		fertigkeitenService.save(fert);
		ergebnis = Stream.of(GenerischesProzessErgebnis.builder()
				.id(fert.getId().toString() + "-display-aug")
				.value(fert.getWert().toString())
				.build(), 
				GenerischesProzessErgebnis.builder()
				.id(CreationIds.FERTIGKEITEN_PRIO_DISPLAY)
				.value(getDisplay(PrioTyp.FERTIGKEITEN, character))
				.errors(allErrors)
				.build());
		return ergebnis;
	}
}
