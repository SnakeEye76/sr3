package de.rpg.character.ausruestung.spezifikation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Halterung {
	LAUF("Lauf"), UNTERLAUF("Unterlauf"), AUFMONTIERT("Aufmontiert");
	
	private final String label;
}
