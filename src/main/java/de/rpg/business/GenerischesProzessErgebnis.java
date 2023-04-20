package de.rpg.business;

import java.util.Set;

import de.rpg.character.ValidationError;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class GenerischesProzessErgebnis implements ProzessErgebnis{
	
	private final String id;
	private final String value;
	private final String type;
	private final Set<ValidationError> errors;
	
	@Override
	public <T extends ProzessErgebnisVisitor> T accept(T visitor) {
		visitor.visit(this);
		return visitor;
	}
}
