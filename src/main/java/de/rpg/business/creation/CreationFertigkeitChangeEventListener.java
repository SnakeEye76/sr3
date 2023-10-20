package de.rpg.business.creation;

import java.util.Optional;
import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.CreationFertigkeitChangeEvent;
import de.rpg.business.FertigkeitenService;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.character.Fertigkeit;
import de.rpg.character.ValidationError;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.view.CreationIds;

@Component
@SessionScope
public class CreationFertigkeitChangeEventListener {

	private final EvetProzessorErgebnisSender sender;
	private final FertigkeitenService fertigkeitenService;
	
	public CreationFertigkeitChangeEventListener(
			EvetProzessorErgebnisSender sender, FertigkeitenService fertigkeitenService) {
		super();
		this.sender = sender;
		this.fertigkeitenService = fertigkeitenService;
	}
	
	@EventListener
	public void prozessiere(CreationFertigkeitChangeEvent event) {
		Fertigkeit fert = getFromCharakter(event.getCharacter(), event.getFertId());
		fert.setWert(event.getNewValue());
		Set<ValidationError> allErrors = CreationProzessorUtil.validierePrioritaeten(event.getCharacter());
		fertigkeitenService.save(fert);
		sender.sende(GenerischesProzessErgebnis.builder()
				.id(fert.getId().toString() + "-display-aug")
				.value(fert.getWert().toString())
				.build());
		sender.sende(GenerischesProzessErgebnis.builder()
				.id(CreationIds.FERTIGKEITEN_PRIO_DISPLAY)
				.value(CreationProzessorUtil.getDisplay(PrioTyp.FERTIGKEITEN, event.getCharacter()))
				.errors(allErrors)
				.build());
	}
	
	private Fertigkeit getFromCharakter(CharakterErschaffung character, Long id) {
		Optional<Fertigkeit> optional = character.getFertigkeiten().stream().filter(fert -> fert.getId().equals(id)).findFirst();
		if(optional.isEmpty()) {
			Fertigkeit fert = fertigkeitenService.findById(id);
			character.getFertigkeiten().add(fert);
			return fert;
		} else {
			return optional.get();
		}
	}
}
