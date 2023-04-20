package de.rpg.rules.dice_roles;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OffeneProbe {

	private WuerfelAnzahl anzahlWuerfel;
	
	public Integer getErgebnis(List<Integer> wuerfelWurf) {
		Optional<Integer> max = wuerfelWurf.stream().max(Comparator.naturalOrder());
		return max.orElse(1);
	}
}
