package de.rpg.erschaffung;

import de.rpg.character.FertigkeitenTyp;
import de.rpg.character.FertigkeitKategorie;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FertigkeitSpezifikation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="BAUEN_REPARIEREN_VON_ID")
	private FertigkeitSpezifikation bauenReparierenVon;
	@Enumerated(EnumType.STRING)
	private AttributTyp attribut;
	@Enumerated(EnumType.STRING)
	private FertigkeitenTyp typ;
	@Enumerated(EnumType.STRING)
	private FertigkeitKategorie kategorie;
	private String beschreibung;
	
	public String getName() {
		if(bauenReparierenVon == null) {
			return name;
		} else {
			return new StringBuilder(bauenReparierenVon.getName())
					.append(" (B/R)")
					.toString();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FertigkeitSpezifikation) {
			return id.equals(((FertigkeitSpezifikation) obj).getId());
		}
		return super.equals(obj);
	}
}
