package de.rpg.rules.dice_roles;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VergleichendeProbe {

	private final WuerfelAnzahl angreiferWuerfel;
	private final WuerfelAnzahl verteidigerWuerfel;
	private final List<WurfModifikator> angreiferModifikatoren;
	private final List<WurfModifikator> verteidigerModifikatoren;
	
	public VergleichsprobenErgebnis getErgebnis(List<Integer> angreiferWuerfelWurf, List<Integer> verteidigerWuerfelWurf) {
		Wurfergebnis angreiferWurf = Wurfergebnis.erzeuge(angreiferWuerfelWurf, new Mindestwurf(verteidigerWuerfel.getWuerfel()), angreiferModifikatoren);
		Wurfergebnis verteidigerWurf = Wurfergebnis.erzeuge(verteidigerWuerfelWurf, new Mindestwurf(angreiferWuerfel.getWuerfel()), verteidigerModifikatoren);
		if(angreiferWurf.getAnzahlEinsen() == angreiferWuerfelWurf.size()
				&& verteidigerWurf.getAnzahlEinsen() == verteidigerWuerfelWurf.size()) {
			return VergleichsprobenErgebnis.beidePatzer();
		} else if(angreiferWurf.getAnzahlEinsen() == angreiferWuerfelWurf.size()) {
			return VergleichsprobenErgebnis.angreiferPatzer();
		} else if(verteidigerWurf.getAnzahlEinsen() == verteidigerWuerfelWurf.size()) {
			return VergleichsprobenErgebnis.verteidigerPatzer();
		} else if(verteidigerWurf.getAnzahlErfolge() == angreiferWurf.getAnzahlErfolge()) {
			return VergleichsprobenErgebnis.gleichstand();
		} else if(verteidigerWurf.getAnzahlErfolge() > angreiferWurf.getAnzahlErfolge()) {
			return VergleichsprobenErgebnis.verteidigerErfolg(verteidigerWurf.getAnzahlErfolge() - angreiferWurf.getAnzahlErfolge());
		} else {
			return VergleichsprobenErgebnis.verteidigerErfolg(angreiferWurf.getAnzahlErfolge() - verteidigerWurf.getAnzahlErfolge());
		}
	}
}
