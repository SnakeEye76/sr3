package de.rpg.erschaffung.priosystem;

import java.util.Collections;
import java.util.Set;

import de.rpg.character.RassenCharakteristik;
import de.rpg.erschaffung.Magie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prioritaet {
	A(Collections.emptySet(), Set.of(Magie.VOLLZAUBERER), 30, 50, 1000000),
	B(Collections.emptySet(), Set.of(Magie.ADEPT, Magie.ASPEKTZAUBERER), 27, 40, 400000),
	C(Set.of(RassenCharakteristik.TROLL, RassenCharakteristik.ELF), Collections.emptySet(), 24, 34, 90000),
	D(Set.of(RassenCharakteristik.ZWERG, RassenCharakteristik.ORK), Collections.emptySet(), 21, 30, 20000),
	E(Set.of(RassenCharakteristik.MENSCH), Collections.emptySet(), 18, 27, 5000);

	private final Set<RassenCharakteristik> rassen;
	private final Set<Magie> magie;
	private final int attributPunkte;
	private final int fertigkeitPunkte;
	private final int ressourcen;
	
}
