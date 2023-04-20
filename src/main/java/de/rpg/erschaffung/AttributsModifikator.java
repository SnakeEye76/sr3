package de.rpg.erschaffung;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class AttributsModifikator implements RassenModifikator{

	private final AttributTyp typ;
	private final Integer wert;
}
