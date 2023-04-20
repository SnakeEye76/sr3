package de.rpg.character.ausruestung.spezifikation;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.Getter;

@Entity
@DiscriminatorValue("FEUER_WAFFE")
@Getter
public class FeuerWaffenSpezifikation extends BaseWaffenSpezifikation {
	@Enumerated(EnumType.STRING)
	private FeuerwaffenTyp feuerwaffenTyp;
	private String muni;
	private String modi;
	private String rueckstossKomp;
	@ElementCollection(targetClass = WaffenModifikation.class)
	@JoinTable(name = "waffenMods", joinColumns = @JoinColumn(name = "waffenSpekId"))
	@Column(name = "mod", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<WaffenModifikation> mods;
}
