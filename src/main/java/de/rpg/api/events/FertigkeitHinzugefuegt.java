package de.rpg.api.events;

import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import lombok.Getter;

@Getter
public class FertigkeitHinzugefuegt extends CreationEvent {
	
	private final FertigkeitSpezifikation fertigkeit;

	public FertigkeitHinzugefuegt(FertigkeitSpezifikation fertigkeit, CharakterErschaffung character) {
		super(character);
		this.fertigkeit = fertigkeit;
	}
	
	
}
