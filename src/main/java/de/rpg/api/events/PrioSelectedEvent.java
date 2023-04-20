package de.rpg.api.events;

import de.rpg.business.EventProzessor;
import de.rpg.character.Character;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.view.ChangeRequest;
import de.rpg.view.CreationIds;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class PrioSelectedEvent implements CreationEvent{

	private final PrioTyp typ;
	private final Prioritaet prioritaet;
	private final String id;
	
	public PrioSelectedEvent(ChangeRequest request) {
		switch(request.getId()) {
			case CreationIds.RASSE_PRIO_SELECT:
				typ = PrioTyp.RASSE;
				break;
			case CreationIds.MAGIE_PRIO_SELECT:
				typ = PrioTyp.MAGIE;
				break;
			case CreationIds.ATTRIBUTS_PRIO_SELECT:
				typ = PrioTyp.ATTRIBUTE;
				break;
			case CreationIds.FERTIGKEITEN_PRIO_SELECT:
				typ = PrioTyp.FERTIGKEITEN;
				break;
			case CreationIds.RESSOURCEN_PRIO_SELECT:
				typ = PrioTyp.RESSOURCEN;
				break;
			default:
				throw new IllegalArgumentException("id " + request.getId() + " ist keine Prio-Selektion");
		}
		id = request.getId();
		prioritaet = Prioritaet.valueOf(request.getValue().substring(0, 1));
	}

	@Override
	public <T extends EventProzessor> T accept(T prozessor, Character character) {
		prozessor.prozessiere(this, character);
		return prozessor;
	}

}
