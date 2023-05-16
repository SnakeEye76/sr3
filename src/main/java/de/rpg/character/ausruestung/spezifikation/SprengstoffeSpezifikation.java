package de.rpg.character.ausruestung.spezifikation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("SPRENGSTOFF")
@Getter
public class SprengstoffeSpezifikation extends BaseAusruestungSpezifikation{

	private int stufe;
}
