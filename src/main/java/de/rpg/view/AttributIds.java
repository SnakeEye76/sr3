package de.rpg.view;

import org.apache.commons.lang3.StringUtils;

public class AttributIds {

	public static final String KON_INPUT = "kon-attr-input";
	public static final String SCH_INPUT = "sch-attr-input";
	public static final String ST_INPUT = "st-attr-input";
	public static final String CHA_INPUT = "cha-attr-input";
	public static final String INT_INPUT = "int-attr-input";
	public static final String WIL_INPUT = "wil-attr-input";
	
	public static final String KON_DISPLAY = "kon-attr-display";
	public static final String SCH_DISPLAY = "sch-attr-display";
	public static final String ST_DISPLAY = "st-attr-display";
	public static final String CHA_DISPLAY = "cha-attr-display";
	public static final String INT_DISPLAY = "int-attr-display";
	public static final String WIL_DISPLAY = "wil-attr-display";
	
	public static String getDisplayFor(String inputId) {
		switch(inputId) {
			case KON_INPUT:
				return KON_DISPLAY;
			case SCH_INPUT:
				return SCH_DISPLAY;
			case ST_INPUT:
				return ST_DISPLAY;
			case CHA_INPUT:
				return CHA_DISPLAY;
			case INT_INPUT:
				return INT_DISPLAY;
			case WIL_INPUT:
				return WIL_DISPLAY;
			default: 
				return StringUtils.EMPTY;
		}
	}
}
