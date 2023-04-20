package de.rpg.character.ausruestung.spezifikation;

import de.rpg.character.PreisMultiplikator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@DiscriminatorValue("PROJEKTIL_WAFFE")
@Getter
public class ProjektilWaffenSpezifikation extends BaseWaffenSpezifikation {
	private int minStaerke;
	@Enumerated(EnumType.STRING)
	private PreisMultiplikator preisMultiplikator;
}
