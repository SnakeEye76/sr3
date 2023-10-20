package de.rpg.business.creation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import de.rpg.api.EvetProzessorErgebnisSender;
import de.rpg.api.events.FertigkeitHinzugefuegt;
import de.rpg.business.FertigkeitenService;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.character.Fertigkeit;

@Component
@SessionScope
public class FertigkeitHinzugefuegtEventListener {

	private final EvetProzessorErgebnisSender sender;
	private final FertigkeitenService fertigkeitenService;
	
	public FertigkeitHinzugefuegtEventListener(
			EvetProzessorErgebnisSender sender, FertigkeitenService fertigkeitenService) {
		super();
		this.sender = sender;
		this.fertigkeitenService = fertigkeitenService;
	}
	
	@SuppressWarnings("unlikely-arg-type")//Fertigkeit ist equal zu seiner FertigkeitSpezifikation
	@EventListener
	public void prozessiere(FertigkeitHinzugefuegt event) {
		if(!event.getCharacter().getFertigkeiten().contains(event.getFertigkeit())) {
			Fertigkeit fertigkeit = fertigkeitenService.save(Fertigkeit.builder().spezifikation(event.getFertigkeit()).wert(0).build());
			event.getCharacter().getFertigkeiten().add(fertigkeit);
			ModelAndView mav = new ModelAndView("fragments/fertigkeitEdit :: fertigkeit-editieren");
			mav.addObject("fertigkeit", event.getFertigkeit().getName());
			mav.addObject("fertigkeitenId", fertigkeit.getId());
			sender.sendeView(ViewProzessErgebnis.builder()
					.modelAndView(mav)
					.build());
		}
	}
}
