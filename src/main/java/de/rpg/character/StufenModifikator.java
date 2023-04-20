package de.rpg.character;

import de.rpg.rules.Modifikator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StufenModifikator implements Modifikator{
	private final Integer wert;
	private final String grund;
}
