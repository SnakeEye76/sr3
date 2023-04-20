package de.rpg.view.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import de.rpg.api.RequestToEvent;
import de.rpg.business.CharacterService;
import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.HauptEventProzessor;
import de.rpg.business.ProzessErgebnis;
import de.rpg.business.ProzessErgebnisVisitor;
import de.rpg.business.ViewProzessErgebnis;
import de.rpg.character.Character;
import de.rpg.view.ChangeRequest;

@Controller
@SessionAttributes(CharacterErstellungsRestController.CHARACTER_ATR_NAME)
public class CharacterErstellungsRestController {
	private static final String CHARACTER_ATR_NAME = "character";
	private static final Logger LOG = LoggerFactory.getLogger(CharacterErstellungsRestController.class);
	
	private final HauptEventProzessor eventProzessor;
	private final CharacterService characterServcie;
	private final RequestToEvent requestToEvent;
	
	public CharacterErstellungsRestController(HauptEventProzessor eventProzessor, CharacterService characterServcie,
			RequestToEvent requestToEvent) {
		super();
		this.eventProzessor = eventProzessor;
		this.characterServcie = characterServcie;
		this.requestToEvent = requestToEvent;
	}
	
	@PostMapping(path="/change", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<GenerischesProzessErgebnis> change(@ RequestBody ChangeRequest request, @SessionAttribute()Character character) {
		List<ProzessErgebnis> ergebnisse = eventProzessor.prozessEvent(requestToEvent.fromRequest(request), character);
		List<GenerischesProzessErgebnis> jsonErgebnis = new ArrayList<>();
		for(ProzessErgebnis ergebnis : ergebnisse) {
			ergebnis.accept(new ProzessErgebnisVisitor() {
				@Override
				public void visit(GenerischesProzessErgebnis generischesProzessErgebnis) {
					jsonErgebnis.add(GenerischesProzessErgebnis.builder()
						.id(generischesProzessErgebnis.getId())
						.value(generischesProzessErgebnis.getValue())
						.type("singleText").build());					
				}
				
				@Override
				public void visit(ViewProzessErgebnis viewProzessErgebnis) {
					LOG.error("Falsches Prozessergebnis");
				}
			});
		}
		return jsonErgebnis;
	}
	
	@PostMapping(path="/changedhtml", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView changeDhtml(@ RequestBody ChangeRequest request, @SessionAttribute()Character character) {
		List<ProzessErgebnis> ergebnisse = eventProzessor.prozessEvent(requestToEvent.fromRequest(request), character);
		ModelAndView mav = new ModelAndView();
		for(ProzessErgebnis ergebnis : ergebnisse) {
			ergebnis.accept(new ProzessErgebnisVisitor() {
				@Override
				public void visit(GenerischesProzessErgebnis generischesProzessErgebnis) {
					LOG.error("Falsches Prozessergebnis");
				}
				
				@Override
				public void visit(ViewProzessErgebnis viewProzessErgebnis) {
					if(StringUtils.isBlank(mav.getViewName()) || viewProzessErgebnis.getModelAndView().getViewName().equals(mav.getViewName())) {
						mav.addAllObjects(viewProzessErgebnis.getModelAndView().getModel());
					}
					mav.setViewName(viewProzessErgebnis.getModelAndView().getViewName());
				}
			});
		}
		return mav;
	}
}
