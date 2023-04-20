package de.rpg.erschaffung;
import java.math.BigDecimal;

public class FertigkeitenErschaffungsRegeln {
	private static final int WISSENSFERTIGKEITS_PUNKTE_MULTIPLIKATOR = 5;
	private static final BigDecimal SPRACHFERTIGKEITS_PUNKTE_MULTIPLIKATOR = new BigDecimal("1.5");

//	public static Integer getWissensfertigkeitsPunkte(Character character) {
//		int intelligenz = character.getAttributes().getOrDefault(AttributTyp.INT, 0);
//		return intelligenz * WISSENSFERTIGKEITS_PUNKTE_MULTIPLIKATOR;
//	}
//	
//	public static Integer getSprachfertigkeitsPunkte(Character character) {
//		int intelligenz = character.getAttributes().getOrDefault(AttributTyp.INT, 0);
//		BigDecimal punkte = SPRACHFERTIGKEITS_PUNKTE_MULTIPLIKATOR
//				.multiply(new BigDecimal(intelligenz))
//				.round(new MathContext(0, RoundingMode.CEILING));
//		return punkte.intValue();
//	}
}
