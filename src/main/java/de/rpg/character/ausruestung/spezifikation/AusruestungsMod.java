package de.rpg.character.ausruestung.spezifikation;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class AusruestungsMod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private AusruestungsTyp ausruestungsTyp;
	@Enumerated(EnumType.STRING)
	private ModTyp modTyp;
	private BigDecimal wert;
	@Column(name = "ausruestungs_id")
	private Long ausruestungsId;
}
