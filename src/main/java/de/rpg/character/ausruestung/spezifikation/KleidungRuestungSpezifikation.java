package de.rpg.character.ausruestung.spezifikation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("KLEIDUNG")
@Getter
public class KleidungRuestungSpezifikation extends BaseAusruestungSpezifikation{

	private int ballistisch;
	private int stoss;
}
