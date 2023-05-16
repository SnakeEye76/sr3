package de.rpg.character.ausruestung.spezifikation;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@DiscriminatorValue("GRANATE")
@Getter
public class GranatenSpezifikation extends BaseWaffenSpezifikation {
	
	private String sprengwirkung;
}
