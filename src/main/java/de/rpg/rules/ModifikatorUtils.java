package de.rpg.rules;

import java.util.List;

public class ModifikatorUtils {

	public static Integer modifizierterWert(Integer wert, List<? extends Modifikator> modifikatoren) {
		Integer ergebnis = wert;
		for(Modifikator mod : modifikatoren) {
			ergebnis += mod.getWert();
		}
		return ergebnis;
	}
}
