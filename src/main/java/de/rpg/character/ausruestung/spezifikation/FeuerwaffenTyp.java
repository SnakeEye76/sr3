package de.rpg.character.ausruestung.spezifikation;
import static org.apache.commons.lang3.Range.between;

import org.apache.commons.lang3.Range;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeuerwaffenTyp {
	HOLDOUT("Hold-out-Pistole", between(0, 5), between(6, 15), between(16, 30), between(31, 50)),
	LP("Leichte Pistole", between(0, 5), between(6, 15), between(16, 30), between(31, 50)),
	SP("Schwere Pistole", between(0, 5), between(6, 20), between(21, 40), between(41, 60)),
	MP("Maschinen Pistole", between(0, 10), between(11, 40), between(41, 80), between(81, 150)),
	TASER("Taser", between(0, 5), between(6, 10), between(11, 12), between(13, 15)),
	SCHROT("Schrotflinte", between(0, 10), between(11, 20), between(21, 50), between(51, 100)),
	SPORT("Sportgewehr", between(0, 100), between(101, 250), between(251, 500), between(501, 750)),
	SCHARF("Scharfschützengewehr", between(0, 150), between(151, 300), between(301, 700), between(701, 1000)),
	STURM("Sturmgewehr", between(0, 50), between(51, 150), between(151, 350), between(351, 550)),
	LMG("Leichtes MG", between(0, 75), between(76, 200), between(201, 400), between(401, 800)),
	MMG("Mittleres MG", between(0, 80), between(81, 250), between(251, 750), between(751, 1200)),
	SMG("Schweres MG", between(0, 80), between(81, 250), between(251, 800), between(801, 1500)),
	STK("Sturmkanone", between(0, 100), between(101, 300), between(301, 900), between(901, 2400)),
	GRW("Granatwerfer", between(5, 50), between(51, 100), between(101, 150), between(151, 300)),
	RAW("Raketenwerfer", between(20, 150), between(151, 450), between(451, 1200), between(1201, 3000));
	
	private final String label;
	private final Range<Integer> kurz;
	private final Range<Integer> mittel;
	private final Range<Integer> weit;
	private final Range<Integer> extremWeit;
}
