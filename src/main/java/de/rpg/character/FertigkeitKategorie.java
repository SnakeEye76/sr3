package de.rpg.character;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FertigkeitKategorie {
	KAMPF("Kampf"), MAGIE("Magie"), KOERPERLICH("Körperliche"), SOZIAL("Soziale"),  TECHNISCH("Technische"), FAHRZEUG("Fahrzeug"), BAUEN_REPARIEEN("Bauen / Reparieren");
	
	private final String label;
}
