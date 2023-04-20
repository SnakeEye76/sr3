package de.rpg.character;

import java.util.List;

import de.rpg.erschaffung.AttributTyp;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import de.rpg.rules.ModifikatorUtils;
import de.rpg.rules.dice_roles.WuerfelAnzahl;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Fertigkeit implements WuerfelAnzahl{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer wert;
	private transient List<StufenModifikator> modifikatoren;
	@ManyToOne
	private FertigkeitSpezifikation spezifikation;
	
	@Override
	public Integer getWuerfel() {
		return ModifikatorUtils.modifizierterWert(wert, modifikatoren);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FertigkeitSpezifikation) {
			if(spezifikation == null) {
				return false;
			} else {
				return spezifikation.getId().equals(((FertigkeitSpezifikation) obj).getId());
			}
		} else if(obj instanceof Fertigkeit) {
			return id.equals(((FertigkeitSpezifikation) obj).getId());
		}
		return super.equals(obj);
	}
}
