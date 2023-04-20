package de.rpg.business;

public interface ProzessErgebnisVisitor {

	void visit(ViewProzessErgebnis viewProzessErgebnis);

	void visit(GenerischesProzessErgebnis generischesProzessErgebnis);

}
