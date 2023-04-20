package de.rpg.business.attribute;

import org.springframework.stereotype.Service;

import de.rpg.character.Character;
import de.rpg.erschaffung.AttributTyp;
import de.rpg.view.ChangeRequest;

@Service
public class AttributService {

	public Character changeAttribute(ChangeRequest request, Character character) {
		AttributTyp attributTyp = AttributTyp.byId(request.getId());
		Integer value = Integer.parseInt(request.getValue());
		character.getAttributes().put(attributTyp, value);
		return character;
	}
}
