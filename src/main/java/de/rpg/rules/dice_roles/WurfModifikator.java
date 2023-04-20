package de.rpg.rules.dice_roles;

import de.rpg.rules.Modifikator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WurfModifikator implements Modifikator{

	private final Integer wert;
	private final String grund;
}
