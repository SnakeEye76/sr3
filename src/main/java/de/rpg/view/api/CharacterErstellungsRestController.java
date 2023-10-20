package de.rpg.view.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.rpg.api.RequestToEvent;
import de.rpg.api.events.CreationEvent;
import de.rpg.api.events.Event;
import de.rpg.business.CharacterService;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.view.ChangeRequest;

@Controller
@SessionAttributes(CharacterErstellungsRestController.CHARACTER_ATR_NAME)
public class CharacterErstellungsRestController {
	protected static final String CHARACTER_ATR_NAME = "character";
	private static final Logger LOG = LoggerFactory.getLogger(CharacterErstellungsRestController.class);
	
	private final CharacterService characterServcie;
	private final RequestToEvent requestToEvent;
	private final ApplicationEventPublisher eventPublisher;
	
	public CharacterErstellungsRestController(CharacterService characterServcie,
			RequestToEvent requestToEvent, ApplicationEventPublisher eventPublisher) {
		super();
		this.characterServcie = characterServcie;
		this.requestToEvent = requestToEvent;
		this.eventPublisher = eventPublisher;
	}
	
	@PostMapping(path="/change", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void change(@ RequestBody ChangeRequest request, @SessionAttribute()CharakterErschaffung character) {
		Event event = requestToEvent.fromRequest(request, character);
		character.addEvent(event);
		eventPublisher.publishEvent(event);
	}
	
	@PostMapping(path="/changedhtml", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void changeDhtml(@ RequestBody ChangeRequest request, @SessionAttribute()CharakterErschaffung character) {
		CreationEvent event = requestToEvent.fromRequest(request, character);
		character.addEvent(event);
		eventPublisher.publishEvent(event);
	}
}
