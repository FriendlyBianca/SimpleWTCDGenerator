package city.makai.wtcd.generator.definitions;

import java.util.ArrayList;
import java.util.List;

public class Selection extends Action {
	List<Action> actions = new ArrayList<>();
	List<String> choices = new ArrayList<>();

	public Selection addAction(String choice, Action next) {
		actions.add(next);
		choices.add(choice);
		return this;
	}

	public Selection clear() {
		actions.clear();
		choices.clear();
		return this;
	}

	@Override
	public String renderAction() {
		StringBuilder builder = new StringBuilder();
		builder.append("selection [").append(System.lineSeparator());
		for (int j = 0; j < actions.size(); j++) {
			for (int i = 0; i < WTCDDocument.selectionExtraSpace; i++) {
				builder.append(" ");
			}
			builder.append("  choice \"" + choices.get(j) + "\" ");
			WTCDDocument.selectionExtraSpace += 2;
			builder.append(actions.get(j).renderAction()).append(System.lineSeparator());
			WTCDDocument.selectionExtraSpace -= 2;
		}
		for (int i = 0; i < WTCDDocument.selectionExtraSpace; i++) {
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}
}
