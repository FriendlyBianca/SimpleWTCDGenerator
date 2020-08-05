package city.makai.wtcd.generator.definitions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Goto extends Action {
	@Getter
	SectionContent next;

	@Override
	public String renderAction() {
		return "goto " + getNext().getName();
	}

	public static Goto section(SectionContent section) {
		return new Goto(section);
	}
}
