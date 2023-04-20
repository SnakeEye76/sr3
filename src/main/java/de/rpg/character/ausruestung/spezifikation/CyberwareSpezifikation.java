package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import de.rpg.character.PreisMultiplikator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CyberwareSpezifikation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private CyberwareKategorie kategorie;
	private String name;
	private BigDecimal essenz;
	private int preis;
	@Enumerated(EnumType.STRING)
	private PreisMultiplikator preisMultiplikator;
	private int maximalStufe;
	private String verfuegbarkeit;
	private BigDecimal starssenIndex;
	private String legalitaet;
}
