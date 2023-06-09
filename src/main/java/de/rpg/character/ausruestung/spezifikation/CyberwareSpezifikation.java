package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;
import java.util.List;

import de.rpg.character.PreisMultiplikator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@DiscriminatorValue("CYBERWARE")
@Getter
public class CyberwareSpezifikation extends BaseAusruestungSpezifikation{

	@Enumerated(EnumType.STRING)
	private CyberwareKategorie kategorie;
	private BigDecimal essenz;
	@Enumerated(EnumType.STRING)
	private PreisMultiplikator preisMultiplikator;
	@ElementCollection
	private List<AusruestungsMod> mods;
}
