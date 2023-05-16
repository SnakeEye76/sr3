package de.rpg.character.ausruestung.spezifikation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("FEUERWAFFEN_MUNITION")
@Getter
public class FeuerwaffenMunitionSpezifikation extends BaseMunitionSpezifikation{

	private Integer tarnstufe;
}
