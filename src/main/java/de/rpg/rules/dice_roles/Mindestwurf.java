package de.rpg.rules.dice_roles;

import java.util.List;

import de.rpg.rules.ModifikatorUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Mindestwurf {
	private static final Integer MINIMALER_MINDESTWURF = 2;

	private final Integer wert;
	
	public Integer getWert(List<WurfModifikator> modifikatoren) {
		Integer ergebnis = ModifikatorUtils.modifizierterWert(wert, modifikatoren);
		if(ergebnis < MINIMALER_MINDESTWURF) {
			return MINIMALER_MINDESTWURF;
		} else {
			return ergebnis;
		}
	}
}
