package de.rpg.character.ausruestung.spezifikation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CyberwareKategorie {
	HEADWARE("Headware"),
	BODYWARE("Bodyware");
	private final String label;
}
