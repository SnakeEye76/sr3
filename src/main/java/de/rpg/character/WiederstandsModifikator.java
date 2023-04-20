package de.rpg.character;

import de.rpg.erschaffung.RassenModifikator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WiederstandsModifikator implements RassenModifikator{
	public enum Typ {
		KRANKHEITEN, DERMALPANZERUNG;
	}
	
	private final Typ typ;
	private final Integer wert;
}
