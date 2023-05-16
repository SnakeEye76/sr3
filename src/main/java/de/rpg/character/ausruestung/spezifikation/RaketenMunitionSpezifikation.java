package de.rpg.character.ausruestung.spezifikation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("RAKETE")
@Getter
public class RaketenMunitionSpezifikation extends BaseMunitionSpezifikation{

	private Integer intelligenz;
	private String sprengwirkung;
}
