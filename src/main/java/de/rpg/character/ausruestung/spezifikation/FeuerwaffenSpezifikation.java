package de.rpg.character.ausruestung.spezifikation;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@DiscriminatorValue("FEUER_WAFFE")
@Getter
public class FeuerwaffenSpezifikation extends BaseWaffenSpezifikation {
	@Enumerated(EnumType.STRING)
	private FeuerwaffenTyp feuerwaffenTyp;
	private String muni;
	private String modi;
	private String rueckstossKomp;
	@ElementCollection
	private List<AusruestungsMod> mods;
}
