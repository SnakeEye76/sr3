package de.rpg.character;

import de.rpg.erschaffung.RassenModifikatoren;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Durchschnittswerte
 */
@RequiredArgsConstructor
@Getter
public enum RassenCharakteristik {
	ZWERG(120, 54, "Rosa-Weiß bis Schwarz", 150, "Zwerg", RassenModifikatoren.ZWERG),
	ELF(190, 72, "Rosa-Weiß bis Schwarz", 250, "Elf", RassenModifikatoren.ELF),
	MENSCH(170, 70, "Rosa-Weiß bis Schwarz", 55, "Mensch", RassenModifikatoren.MENSCH),
	ORK(190, 95, "Blaßrosa bis Schwarz", 40, "Ork", RassenModifikatoren.ORK),
	TROLL(280, 225, "Rosa-Weiß bis Braun", 50, "Troll", RassenModifikatoren.TROLL);
	
	
	private final int groesse;
	private final int gewicht;
	private final String Haupfarbe;
	private final int lebenserwartung;
	private final String name;
	private final RassenModifikatoren mod;
	
}
