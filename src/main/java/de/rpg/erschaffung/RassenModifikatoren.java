package de.rpg.erschaffung;
import static de.rpg.character.ReichweitenModifikator.Typ.BEWAFFNETER_NAHKAMPF;
import static de.rpg.character.ReichweitenModifikator.Typ.WAFFENLOSER_KAMPF;
import static de.rpg.character.SichtModifikator.IR;
import static de.rpg.character.SichtModifikator.LV;
import static de.rpg.character.WiederstandsModifikator.Typ.DERMALPANZERUNG;
import static de.rpg.character.WiederstandsModifikator.Typ.KRANKHEITEN;
import static de.rpg.erschaffung.AttributTyp.CHA;
import static de.rpg.erschaffung.AttributTyp.INT;
import static de.rpg.erschaffung.AttributTyp.KON;
import static de.rpg.erschaffung.AttributTyp.SCH;
import static de.rpg.erschaffung.AttributTyp.ST;
import static de.rpg.erschaffung.AttributTyp.WILL;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.rpg.character.ReichweitenModifikator;
import de.rpg.character.SichtModifikator;
import de.rpg.character.WiederstandsModifikator;
import de.rpg.rules.Modifikator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RassenModifikatoren {

	MENSCH(Collections.emptyMap(), null, null, Collections.emptyList()),
	ZWERG(Map.of(KON, 1, ST, 2, WILL, 1), IR, new WiederstandsModifikator(KRANKHEITEN, 2), Collections.emptyList()),
	ELF(Map.of(SCH, 1, CHA, 1), LV, null, Collections.emptyList()),
	ORK(Map.of(KON, 3, ST, 2, CHA, -1, INT, -1), LV, null, Collections.emptyList()),
	TROLL(Map.of(KON, 5, SCH, -1, ST, 4, INT, -2, CHA, -2), IR, new WiederstandsModifikator(DERMALPANZERUNG, 1), List.of(new ReichweitenModifikator(WAFFENLOSER_KAMPF, 1), new ReichweitenModifikator(BEWAFFNETER_NAHKAMPF, 1)));
	
	private final Map<AttributTyp, Integer> attributModifikationen;
	private final SichtModifikator sichtModifikator;
	private final WiederstandsModifikator wiederstandModifikator;
	private final List<ReichweitenModifikator> reichweitenModifikatoren;
	
	public Modifikator getModifikator(AttributTyp attribut) {
		return () -> attributModifikationen.getOrDefault(attribut, 0);
	}
}
