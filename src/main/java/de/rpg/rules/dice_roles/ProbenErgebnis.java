package de.rpg.rules.dice_roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProbenErgebnis { 
	public enum Status {
		ERFOLG, FEHLSCHLAG, PATZER;
	}
	
	private final Status status;
	private final Integer anzahlErfolge;
	
	public static ProbenErgebnis patzer() {
		return new ProbenErgebnis(Status.PATZER, 0);
	}
	
	public static ProbenErgebnis fehlschlag() {
		return new ProbenErgebnis(Status.FEHLSCHLAG, 0);
	}
	
	public static ProbenErgebnis erfolg(Integer anzahlErfolge) {
		return new ProbenErgebnis(Status.ERFOLG, anzahlErfolge);
	}
	
	public boolean istGleichstand() {
		return Status.ERFOLG == status 
				&& anzahlErfolge == 0;
	}
}
