package de.rpg.erschaffung;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AttributTyp {
	KON("Konstitution"), SCH("Schnelligkeit"), ST("Stärke"), CHA("Charisma"), INT("Intelligenz"), WILL("Willenskraft"), ESSENZ("Essenz"), MAGIE("Magie"), REA("Reaktion");
	
	private final String label;
	
	public static AttributTyp byId(String id) throws IllegalArgumentException{
		switch(id) {
		case "kon-attr-input":
			return KON;
		case "sch-attr-input":
			return SCH;
		case "st-attr-input":
			return ST;
		case "cha-attr-input":
			return CHA;
		case "int-attr-input":
			return INT;
		case "wil-attr-input":
			return WILL;
		default:
			throw new IllegalArgumentException("Die Uebergeben Id ist keine AttributsId <" + id + ">");
		}
	}
}
