package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CyberwareTyp {
	ALPHA("Alpha", 2, BigDecimal.valueOf(0.80));
	
	private final String label;
	private final int preisMultiplikator;
	private final BigDecimal essenzMultiplikator; 
}
