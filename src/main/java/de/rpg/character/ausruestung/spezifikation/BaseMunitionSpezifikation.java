package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("MUNITION")
@Getter
public class BaseMunitionSpezifikation extends BaseAusruestungSpezifikation{

	private String schaden;
	private BigDecimal gewicht;
}
