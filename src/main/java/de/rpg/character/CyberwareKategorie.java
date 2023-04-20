package de.rpg.character;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CyberwareKategorie {
	HEADWARE("Headware");
	private final String label;
}
