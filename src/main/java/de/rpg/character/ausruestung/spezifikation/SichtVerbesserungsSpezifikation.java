package de.rpg.character.ausruestung.spezifikation;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("SICHT_VERBESSERUNG")
@Getter
public class SichtVerbesserungsSpezifikation extends BaseAusruestungSpezifikation{

	private int vergroesserung;
	@ElementCollection
	private List<AusruestungsMod> mods;
}
