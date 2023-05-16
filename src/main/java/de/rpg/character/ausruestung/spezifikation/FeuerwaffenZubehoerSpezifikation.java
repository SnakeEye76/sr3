package de.rpg.character.ausruestung.spezifikation;

import java.util.List;

import de.rpg.character.PreisMultiplikator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@DiscriminatorValue("FEUER_WAFFE_ZUBEHOER")
@Getter
public class FeuerwaffenZubehoerSpezifikation extends BaseAusruestungSpezifikation{

	private int tarnstufe;
	@Enumerated(EnumType.STRING)
	private Halterung halterung;
	@OneToMany(mappedBy = "ausruestungs_id")
	private List<AusruestungsMod> mods;
	@Enumerated(EnumType.STRING)
	private PreisMultiplikator preisMultiplikator;
}
