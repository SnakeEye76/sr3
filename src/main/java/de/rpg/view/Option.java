package de.rpg.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Option {

	private final String text;
	private final String value;
}
