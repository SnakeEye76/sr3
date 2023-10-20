package de.rpg.api;

import org.springframework.stereotype.Service;

import de.rpg.api.events.CreationAttributeChangeEvent;
import de.rpg.api.events.CreationEvent;
import de.rpg.api.events.CreationFertigkeitChangeEvent;
import de.rpg.api.events.FertigkeitHinzugefuegt;
import de.rpg.api.events.MagieSelectedEvent;
import de.rpg.api.events.PrioSelectedEvent;
import de.rpg.api.events.RasseSelectedEvent;
import de.rpg.business.FertigkeitenService;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import de.rpg.view.AttributIds;
import de.rpg.view.ChangeRequest;
import de.rpg.view.CreationIds;

@Service
public class RequestToEvent {
	
	private final FertigkeitenService fertigkeitenService;
	
	public RequestToEvent(FertigkeitenService fertigkeitenService) {
		super();
		this.fertigkeitenService = fertigkeitenService;
	}

	public CreationEvent fromRequest(ChangeRequest request, CharakterErschaffung character){
		
		switch(request.getId()) {
			case AttributIds.KON_INPUT:
			case AttributIds.SCH_INPUT:
			case AttributIds.ST_INPUT:
			case AttributIds.CHA_INPUT:
			case AttributIds.INT_INPUT:
			case AttributIds.WIL_INPUT:
				return new CreationAttributeChangeEvent(request, character);
			case CreationIds.RASSE_PRIO_SELECT:
			case CreationIds.MAGIE_PRIO_SELECT:
			case CreationIds.ATTRIBUTS_PRIO_SELECT:
			case CreationIds.FERTIGKEITEN_PRIO_SELECT:
			case CreationIds.RESSOURCEN_PRIO_SELECT:
				return new PrioSelectedEvent(request, character);
			case CreationIds.RASSE_AUSWAHL:
				return new RasseSelectedEvent(request, character);
			case CreationIds.MAGIE_AUSWAHL:
				return new MagieSelectedEvent(request, character);
			case CreationIds.FERTIGKEITEN_AUSWAHL:
				FertigkeitSpezifikation fertigkeit = fertigkeitenService.findSpecById(request.getValue());
				return new FertigkeitHinzugefuegt(fertigkeit, character);
			default:
				if(request.getId().endsWith("-fert-input")) {
					return new CreationFertigkeitChangeEvent(request, character);
				}
				throw new IllegalArgumentException("ChangeRequest <" + request + "> nicht konvertierbar.");
		}
	}
}
