package de.rpg.business;

public interface ProzessErgebnis {

	<T extends ProzessErgebnisVisitor> T accept(T visitor);
}
