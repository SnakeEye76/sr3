package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("WAFFE")
@Getter
public class BaseWaffenSpezifikation extends BaseAusruestungSpezifikation {
	private int tarnstufe;
	private String schaden;
	private BigDecimal gewicht;
	
}
