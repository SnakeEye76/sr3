package de.rpg.character;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.rpg.character.ausruestung.Ausruestung;
import de.rpg.character.ausruestung.spezifikation.CyberwareSpezifikation;
import de.rpg.erschaffung.AttributTyp;
import de.rpg.erschaffung.Magie;
import de.rpg.erschaffung.RassenModifikatoren;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import de.rpg.rules.Modifikator;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyClass;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String strassenName;
	private LocalDate geburtstag;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PRIORITAET", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "prio_typ", length = 40, nullable = false)
	@MapKeyClass(PrioTyp.class)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<PrioTyp, Prioritaet> prioritaeten = new EnumMap<>(PrioTyp.class);
	@Enumerated(EnumType.STRING)
	private RassenModifikatoren rasse;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ATTRIBUTES", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "attribut_typ", length = 40, nullable = false)
	@MapKeyClass(AttributTyp.class)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<AttributTyp, Integer> attributes = new EnumMap<>(AttributTyp.class);
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "INITIATIVEN", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "ini_typ", length = 40, nullable = false)
	@MapKeyClass(IniTyp.class)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<IniTyp, Initiative> initiativen;
	@ElementCollection
	private List<Fertigkeit> fertigkeiten = new ArrayList<>();
	private transient Kampfpool kampfpool;
	private transient Astralkampfpool astralkampfpool;
	private transient Steuerpool steuerpool;
	private transient Hackingpool hackingpool;
	private transient Zauberpool zauberpool;
	@ElementCollection
	private List<Ausruestung> ausruestung;
	@ElementCollection
	private List<CyberwareSpezifikation> cyberware;
	@ElementCollection
	private List<Connection> connections;
	@ElementCollection
	private List<Lebensstil> lebensstile;
	@OneToOne
	private Zustandsmonitor koerperlicherZustand;
	@OneToOne
	private Zustandsmonitor geistigerZustand;
	@Enumerated(EnumType.STRING)
	private MagieTyp magieTyp;
	@Enumerated(EnumType.STRING)
	private Magie magie;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "VALIDATIONERRORS", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "error_typ", length = 40, nullable = false)
	@MapKeyClass(ErrorTyp.class)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<ErrorTyp, Errors> validationErrors;
	
	public Integer getModified(AttributTyp attribut) {
		Integer wert = attributes.get(attribut);
		for(Modifikator modi : getAttributModifikatoren(attribut)) {
			wert += modi.getWert();
		}
		return wert;
	}
	
	public Integer getModifiedFertigkeit(Long fertId) {
		Fertigkeit fertigkeit = getFertigkeiten().stream().filter(fert -> fert.getId().equals(id)).findFirst().orElseThrow(()-> new EntityNotFoundException());
		Integer wert = fertigkeit.getWert();
		for(Modifikator modi : getFertigkeitsModifikatoren(fertigkeit)) {
			wert += modi.getWert();
		}
		return wert;
	}
	
	private List<? extends Modifikator> getFertigkeitsModifikatoren(Fertigkeit fertigkeit) {
		return fertigkeit.getModifikatoren();
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
