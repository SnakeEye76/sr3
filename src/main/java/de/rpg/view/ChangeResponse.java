package de.rpg.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeResponse {

	private String id;
	private String value;
}
