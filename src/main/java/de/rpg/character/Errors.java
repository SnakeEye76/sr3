package de.rpg.character;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class Errors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long errId;
	@ElementCollection
	private Set<ValidationError> errors;
}
