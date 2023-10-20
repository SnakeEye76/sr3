package de.rpg.business.creation;

import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.business.attribute.AttributProzessor;
import de.rpg.character.ValidationError;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.view.CreationIds;

@Component
@SessionScope
public class PrioSelectedEventListener {
	
	private final EvetProzessorErgebnisSender sender;
	
	public PrioSelectedEventListener(
			EvetProzessorErgebnisSender sender) {
		super();
		this.sender = sender;
	}

	@EventListener
	public void prozessiere(PrioSelectedEvent event) {
		event.getCharacter().getPrioritaeten().put(event.getTyp(), event.getPrioritaet());
		Set<ValidationError> attributeErrors = AttributProzessor.validiereAttribute(event.getCharacter());
		Set<ValidationError> allErrors = CreationProzessorUtil.validierePrioritaeten(event.getCharacter());
		allErrors.addAll(attributeErrors);
		if(PrioTyp.RASSE == event.getTyp() || PrioTyp.MAGIE == event.getTyp()) {
			sender.sendeView(ViewProzessErgebnis.builder()
					.id(event.getId())
					.modelAndView(null)
					.build());
		}
		
		sender.sende(GenerischesProzessErgebnis.builder()
			.id(CreationIds.getDisplayFor(event.getId()))
			.value(CreationProzessorUtil.getDisplay(event, event.getCharacter()))
			.errors(allErrors)
			.build());
	}
}
