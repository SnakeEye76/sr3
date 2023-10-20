package de.rpg.api.events;

import de.rpg.erschaffung.CharakterErschaffung;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreationEvent implements Event{
	private final CharakterErschaffung character;
}
