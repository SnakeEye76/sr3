package de.rpg.erschaffung;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Magie {
	VOLLZAUBERER(25, "Vollmzauberer"), ADEPT(0, "Adept"), ASPEKTZAUBERER(35, "Aspektzauberer");
	
	private final int spruchpunkte;
	private final String name;
}
