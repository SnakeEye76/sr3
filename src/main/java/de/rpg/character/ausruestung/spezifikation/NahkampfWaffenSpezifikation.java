package de.rpg.character.ausruestung.spezifikation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("NAHKAMPF_WAFFE")
@Getter
public class NahkampfWaffenSpezifikation extends BaseWaffenSpezifikation {
	private int reichweite;
}
