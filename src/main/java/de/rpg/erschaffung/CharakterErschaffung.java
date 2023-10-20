package de.rpg.erschaffung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.rpg.api.events.CreationEvent;
import de.rpg.api.events.Event;
import de.rpg.character.Connection;
import de.rpg.character.Fertigkeit;
import de.rpg.character.Lebensstil;
import de.rpg.character.MagieTyp;
import de.rpg.character.ausruestung.Ausruestung;
import de.rpg.character.ausruestung.spezifikation.CyberwareSpezifikation;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.rules.Modifikator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharakterErschaffung {

	private String name;
	private String strassenName;
	private LocalDate geburtstag;
	private Map<PrioTyp, Prioritaet> prioritaeten = new EnumMap<>(PrioTyp.class);
	private RassenModifikatoren rasse;
	private Map<AttributTyp, Integer> attributes = new EnumMap<>(AttributTyp.class);
//	private Map<IniTyp, Initiative> initiativen;
	private List<Fertigkeit> fertigkeiten = new ArrayList<>();
//	private transient Kampfpool kampfpool;
//	private transient Astralkampfpool astralkampfpool;
//	private transient Steuerpool steuerpool;
//	private transient Hackingpool hackingpool;
//	private transient Zauberpool zauberpool;
	private List<Ausruestung> ausruestung;
	private List<CyberwareSpezifikation> cyberware;
	private List<Connection> connections;
	private List<Lebensstil> lebensstile;
	private MagieTyp magieTyp;
	private Magie magie;
	private List<CreationEvent> events = new ArrayList<>();
	
	public void addEvent(Event event) {
		if(event instanceof CreationEvent)
		this.events.add((CreationEvent) event);
	}
	
	public Integer getModified(AttributTyp attribut) {
		Integer wert = attributes.get(attribut);
		for(Modifikator modi : getAttributModifikatoren(attribut)) {
			wert += modi.getWert();
		}
		return wert;
	}
	
	private List<Modifikator> getAttributModifikatoren(AttributTyp attribut) {
		if(rasse == null) {
			return Collections.emptyList();
		}
		return List.of(rasse.getModifikator(attribut));
	}
	
	private String getPrio(PrioTyp typ) {
		Prioritaet prioritaet = prioritaeten.get(typ);
		if(prioritaet == null) {
			return null;
		} else {
			return prioritaet.name();
		}
	}
	
	public String getRassPrio() {
		return getPrio(PrioTyp.RASSE);
	}
	
	public String getMagPrio() {
		return getPrio(PrioTyp.MAGIE);
	}
	
	public String getAttrPrio() {
		return getPrio(PrioTyp.ATTRIBUTE);
	}
	
	public String getFertPrio() {
		return getPrio(PrioTyp.FERTIGKEITEN);
	}
	
	public String getResPrio() {
		return getPrio(PrioTyp.ATTRIBUTE);
	}
}
