package de.rpg.api;
import java.util.Locale;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import de.rpg.business.GenerischesProzessErgebnis;
import de.rpg.business.ViewProzessErgebnis;

@Service
public class EvetProzessorErgebnisSender {
	
	private final TemplateEngine engine;
	
	public EvetProzessorErgebnisSender(TemplateEngine engine) {
		super();
		this.engine = engine;
	}

	@SendTo("/topic/ergebnis")
	public GenerischesProzessErgebnis sende(GenerischesProzessErgebnis ergebnis) {
		return ergebnis;
	}
	
	@SendTo("/topic/ergebnis")
	public String sendeView(ViewProzessErgebnis ergebnis) {
		final Context ctx = new Context(Locale.GERMAN);
		ctx.setVariables(ergebnis.getModelAndView().getModel());
		return engine.process(ergebnis.getModelAndView().getViewName(), ctx);
	}
}
