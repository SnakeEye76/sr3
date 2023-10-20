package de.rpg.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import de.rpg.business.CharacterService;
import de.rpg.business.FertigkeitenService;
import de.rpg.character.Fertigkeit;
import de.rpg.character.FertigkeitKategorie;
import de.rpg.erschaffung.CharakterErschaffung;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import de.rpg.erschaffung.priosystem.PrioTyp;
import de.rpg.erschaffung.priosystem.Prioritaet;
import jakarta.websocket.server.PathParam;

@Controller
@SessionAttributes({CharacterErstellungsController.CHARACTER_ATR_NAME, CharacterErstellungsController.SELECTED_KATEGORIEN_NAME })
public class CharacterErstellungsController {
	protected static final String CHARACTER_ATR_NAME = "character";
	protected static final String SELECTED_KATEGORIEN_NAME = "selectedKategorien";
	private static final String RASS_PRIOS_NAME = "rassPrios";
	private static final String MAG_PRIOS_NAME = "magPrios";
	private static final String ATTR_PRIOS_NAME = "attrPrios";
	private static final String FERT_PRIOS_NAME = "fertPrios";
	private static final String RES_PRIOS_NAME = "resPrios";
	private static final String FERTIGKEITEN_KATEGORIEN = "fertKategorien";
	
	private final CharacterService characterServcie;
	private final FertigkeitenService fertigkeitenServcie;
	
	public CharacterErstellungsController(CharacterService characterServcie,
			FertigkeitenService fertigkeitenServcie) {
		super();
		this.characterServcie = characterServcie;
		this.fertigkeitenServcie= fertigkeitenServcie;
	}
	
	private List<Option> prios(PrioTyp typ) {
		return Stream.of(Prioritaet.values())
				.map(prio -> Option.builder()
						.value(prio.name())
						.text(typ.getLabel().apply(prio))
						.build())
				.toList();
	}
	
	@ModelAttribute(RASS_PRIOS_NAME)
	public List<Option> rassePrios() {
		return prios(PrioTyp.RASSE);
	}
	
	@ModelAttribute(MAG_PRIOS_NAME)
	public List<Option> magiePrios() {
		return prios(PrioTyp.MAGIE);
	}
	
	@ModelAttribute(ATTR_PRIOS_NAME)
	public List<Option> attributPrios() {
		return prios(PrioTyp.ATTRIBUTE);
	}
	
	@ModelAttribute(FERT_PRIOS_NAME)
	public List<Option> fertPrios() {
		return prios(PrioTyp.FERTIGKEITEN);
	}
	
	@ModelAttribute(RES_PRIOS_NAME)
	public List<Option> ressourcenPrios() {
		return prios(PrioTyp.RESSOURCEN);
	}
	
	@ModelAttribute(CHARACTER_ATR_NAME)
	public CharakterErschaffung character(@PathParam("characterId") Long characterId) {
		if(ObjectUtils.isEmpty(characterId)) {
			return new CharakterErschaffung();
		} else {
			return null; //characterServcie.findById(characterId);
		}
	}
	
	@ModelAttribute(SELECTED_KATEGORIEN_NAME)
	public Set<FertigkeitKategorie> selectedKategorien() {
		return new HashSet<>(FertigkeitKategorie.values().length);
	}
	
	@ModelAttribute(FERTIGKEITEN_KATEGORIEN)
	public List<FertigkeitKategorie> fertigkeitenKategorien() {
		return Arrays.asList(FertigkeitKategorie.values());
	}
	
	@GetMapping("/erstellung")
	public String erstellung() {
		return "erstellung";
	}
	
	@PostMapping("/alleFertigkeiten")
	public ModelAndView alleFertigkeiten(@SessionAttribute(CHARACTER_ATR_NAME) CharakterErschaffung character, 
			@SessionAttribute(SELECTED_KATEGORIEN_NAME) Set<FertigkeitKategorie> selectedKategorien,
			@RequestBody(required = false) ChangeRequest request) {
		if(request != null) {
			FertigkeitKategorie kategorie = FertigkeitKategorie.valueOf(request.getId().replace("-Id-check", ""));
			if(Boolean.parseBoolean(request.getValue())) {
				selectedKategorien.add(kategorie);
			} else {
				selectedKategorien.remove(kategorie);
			}
		}
		List<FertigkeitSpezifikation> vergebeneFertigkeiten = character == null ? Collections.emptyList() : character.getFertigkeiten().stream().map(Fertigkeit::getSpezifikation).toList();
		List<Option> options = StreamSupport.stream(fertigkeitenServcie.getAllFertigkeiten().spliterator(), false)
				.filter((fert) -> !vergebeneFertigkeiten.contains(fert))
				.filter((fert) -> selectedKategorien.contains(fert.getKategorie()))
				.map((fert) -> Option.builder().text(fert.getName()).value(String.valueOf(fert.getId())).build())
				.toList();
		ModelAndView mav = new ModelAndView("fragments/dropdown :: dropdown");
		mav.addObject("dropdownId", CreationIds.FERTIGKEITEN_AUSWAHL);
		mav.addObject("dropdownValue", 0);
		mav.addObject("optionNullText", "Fertigkeit auswählen");
		mav.addObject("mitNullOptoin", true);
		mav.addObject("optionen", options);
		return mav;
	}
}

