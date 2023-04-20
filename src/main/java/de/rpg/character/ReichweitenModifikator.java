package de.rpg.character;

import de.rpg.erschaffung.RassenModifikator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReichweitenModifikator implements RassenModifikator{
	public enum Typ {
		WAFFENLOSER_KAMPF, BEWAFFNETER_NAHKAMPF;
	}
	
	private final Typ typ;
	private final Integer wert;
}
