package de.rpg.rules.dice_roles;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Wurfergebnis {
	private final int anzahlEinsen;
	private final int anzahlErfolge;
	
	
	public static Wurfergebnis erzeuge(List<Integer> wuerfelWurf, Mindestwurf mindestwurf, List<WurfModifikator> modifikatoren) {
		int anzahlEinsen = 0;
		int anzahlErfolge = 0;
		for(Integer wuerfelErgebnis : wuerfelWurf) {
			if(wuerfelErgebnis == 1) {
				anzahlEinsen++;
				continue;
			}
			if(wuerfelErgebnis >= mindestwurf.getWert(modifikatoren)) {
				anzahlErfolge++;
			}
		}
		return new Wurfergebnis(anzahlEinsen, anzahlErfolge);
	}
}
