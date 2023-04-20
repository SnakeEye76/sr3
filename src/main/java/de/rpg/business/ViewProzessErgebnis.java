package de.rpg.business;

import org.springframework.web.servlet.ModelAndView;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class ViewProzessErgebnis implements ProzessErgebnis{
	
	private final String id;
	private final ModelAndView modelAndView;
	
	@Override
	public <T extends ProzessErgebnisVisitor> T accept(T visitor) {
		visitor.visit(this);
		return visitor;
	}
}
