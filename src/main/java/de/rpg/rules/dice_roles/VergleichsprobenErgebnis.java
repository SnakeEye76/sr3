package de.rpg.rules.dice_roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VergleichsprobenErgebnis {
	public enum Status {
		ANGREIFER_PATZER, VERTEIDIGER_PATZER, BEIDE_PATZER, ANGREIFER_ERFOLG, VERTEIDIGER_ERFOLG, GLEICHSTAND;
	}
	
	private final Integer nettoErfolge;
	private final Status status;
	
	
	public static VergleichsprobenErgebnis angreiferPatzer() {
		return new VergleichsprobenErgebnis(0, Status.ANGREIFER_PATZER);
	}
	
	public static VergleichsprobenErgebnis verteidigerPatzer() {
		return new VergleichsprobenErgebnis(0, Status.VERTEIDIGER_PATZER);
	}
	
	public static VergleichsprobenErgebnis beidePatzer() {
		return new VergleichsprobenErgebnis(0, Status.BEIDE_PATZER);
	}
	
	public static VergleichsprobenErgebnis angreiferErfolg(Integer anzahlErfolge) {
		return new VergleichsprobenErgebnis(anzahlErfolge, Status.ANGREIFER_ERFOLG);
	}
	
	public static VergleichsprobenErgebnis verteidigerErfolg(Integer anzahlErfolge) {
		return new VergleichsprobenErgebnis(anzahlErfolge, Status.VERTEIDIGER_ERFOLG);
	}
	
	public static VergleichsprobenErgebnis gleichstand() {
		return new VergleichsprobenErgebnis(0, Status.GLEICHSTAND);
	}
}
