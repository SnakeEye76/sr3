package de.rpg.rules.dice_roles;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractErfolgsprobe {
	private static final int MINDEST_ANZAHL_ERFOLGE = 1;

	private final Mindestwurf mindestwurf;
	private final WuerfelAnzahl anzahlWuerfel;
	private final List<WurfModifikator> modifikatoren;
	
	protected abstract List<Integer> getWuerfelErgebnis();
	
	public ProbenErgebnis getErgebnis() {
		return getErgebnis(getWuerfelErgebnis());
	}
	
	public ProbenErgebnis getErgebnis(List<Integer> wuerfelWurf) {
		Wurfergebnis wurf = Wurfergebnis.erzeuge(wuerfelWurf, mindestwurf, modifikatoren);
		if(wurf.getAnzahlEinsen() == wuerfelWurf.size()) {
			return ProbenErgebnis.patzer();
		} else if(wurf.getAnzahlErfolge() < getMindestAnzahlErfolge()) {
			return ProbenErgebnis.fehlschlag();
		} else {
			return ProbenErgebnis.erfolg(wurf.getAnzahlErfolge() - getMindestAnzahlErfolge());
		}
	}
	
	protected Integer getMindestAnzahlErfolge() {
		return MINDEST_ANZAHL_ERFOLGE;
	}
	
	public String toString() {
		return new StringBuilder(anzahlWuerfel.getWuerfel())
				.append(" W6 gegen ")
				.append(mindestwurf.getWert(getModifikatoren()))
				.toString();
	}
}
