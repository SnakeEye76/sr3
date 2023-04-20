package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance
@DiscriminatorColumn(name="TYPE")
@Table(name="Ausruestung_Spezifikation")
@Getter
@Setter
public class BaseAusruestungSpezifikation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int preis;
	private String name;
	private String verfuegbarkeit;
	private BigDecimal strassenindex;
	private String legalitaet;
	private int jahr;
	private String beschreibung;
}
