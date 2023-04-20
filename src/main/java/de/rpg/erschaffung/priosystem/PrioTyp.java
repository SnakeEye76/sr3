package de.rpg.erschaffung.priosystem;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.rpg.character.RassenCharakteristik;
import de.rpg.erschaffung.Magie;
import de.rpg.view.Formatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PrioTyp {
	RASSE(null, Prioritaet::getRassen, prio-> Collections.emptySet(), prio -> PrioTyp.rasseToLabel(prio)), 
	MAGIE(null, prio-> Collections.emptySet(), Prioritaet::getMagie, prio -> PrioTyp.magieToLabel(prio)), 
	ATTRIBUTE(Prioritaet::getAttributPunkte, prio-> Collections.emptySet(), prio-> Collections.emptySet(), prio -> PrioTyp.attriToLabel(prio)), 
	FERTIGKEITEN(Prioritaet::getFertigkeitPunkte, prio-> Collections.emptySet(), prio-> Collections.emptySet(), prio -> PrioTyp.fertToLabel(prio)), 
	RESSOURCEN(Prioritaet::getRessourcen, prio-> Collections.emptySet(), prio-> Collections.emptySet(), prio -> PrioTyp.ressToLabel(prio));
	
	private final Function<Prioritaet, Integer> getZahlenwert;
	private final Function<Prioritaet, Set<RassenCharakteristik>> getRassenCharakteristik;
	private final Function<Prioritaet, Set<Magie>> getMagie;
	private final Function<Prioritaet, String> label;
	
	private static String rasseToLabel(Prioritaet prio) {
		return new StringBuilder(prio.name())
				.append(" ")
				.append(prio.getRassen().stream().map(RassenCharakteristik::getName).collect(Collectors.joining(", ")))
				.toString();
	}
	
	private static String magieToLabel(Prioritaet prio) {
		return new StringBuilder(prio.name())
				.append(" ")
				.append(prio.getMagie().stream().map(Magie::getName).collect(Collectors.joining(", ")))
				.toString();
	}
	
	private static String zahlToLabel(Prioritaet prio, String suffix, Function<Prioritaet, Integer> provider) {
		return new StringBuilder(prio.name())
				.append(" ")
				.append(provider.apply(prio))
				.append(suffix)
				.toString();
	}
	
	private static String attriToLabel(Prioritaet prio) {
		return zahlToLabel(prio, " Attributspunkte", Prioritaet::getAttributPunkte);
	}
	
	private static String fertToLabel(Prioritaet prio) {
		return zahlToLabel(prio, " Fertigkeitenpunkte", Prioritaet::getFertigkeitPunkte);
	}
	
	private static String ressToLabel(Prioritaet prio) {
		return new StringBuilder(prio.name())
				.append(" ")
				.append(Formatter.NUYEN.format(prio.getRessourcen()))
				.toString();
	}
}
