package de.rpg.view;

import org.apache.commons.lang3.StringUtils;

public class CreationIds {

	public static final String RASSE_PRIO_SELECT = "rass-prio-input-dhtml";
	public static final String MAGIE_PRIO_SELECT = "mag-prio-input-dhtml";
	public static final String ATTRIBUTS_PRIO_SELECT = "attr-prio-input";
	public static final String FERTIGKEITEN_PRIO_SELECT = "fert-prio-input";
	public static final String RESSOURCEN_PRIO_SELECT = "res-prio-input";
	
	public static final String RASSE_PRIO_DISPLAY = "rass-prio-display";
	public static final String MAGIE_PRIO_DISPLAY = "mag-prio-display";
	public static final String ATTRIBUTS_PRIO_DISPLAY = "attr-prio-display";
	public static final String FERTIGKEITEN_PRIO_DISPLAY = "fert-prio-display";
	public static final String RESSOURCEN_PRIO_DISPLAY = "res-prio-display";
	
	public static final String RASSE_AUSWAHL = "rasseAuswahl";
	public static final String MAGIE_AUSWAHL = "magieAuswahl";
	
	public static final String FERTIGKEITEN_AUSWAHL = "fertigkeitenSelect";
	
	public static String getDisplayFor(String inputId) {
		switch(inputId) {
			case RASSE_PRIO_SELECT:
				return RASSE_PRIO_DISPLAY;
			case MAGIE_PRIO_SELECT:
				return MAGIE_PRIO_DISPLAY;
			case ATTRIBUTS_PRIO_SELECT:
				return ATTRIBUTS_PRIO_DISPLAY;
			case FERTIGKEITEN_PRIO_SELECT:
				return FERTIGKEITEN_PRIO_DISPLAY;
			case RESSOURCEN_PRIO_SELECT:
				return RESSOURCEN_PRIO_DISPLAY;
			default: 
				return StringUtils.EMPTY;
		}
	}
}
