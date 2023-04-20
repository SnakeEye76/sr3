package de.rpg.rules.dice_roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WuerfelModifikator {

	private final Integer wert;
	private final String grund;
}
