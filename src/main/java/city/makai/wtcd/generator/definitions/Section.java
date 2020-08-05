package city.makai.wtcd.generator.definitions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Section implements WTCDRenderer {
	SectionContent section;
	Action then;

	@Override
	public String render() {
		return "---<<< " + getSection().getName() + " >>>---" + System.lineSeparator() + getSection().getContent()
				+ System.lineSeparator();
	}

	@Override
	public String code() {
		if (then == null) {
			then = new Exit();
		}
		StringBuilder execution = new StringBuilder();
		if (section.codeToExecute.size() > 0) {
			execution.append(" {").append(System.lineSeparator());
			section.codeToExecute.forEach(e -> {
				execution.append("  ").append(e.code()).append(System.lineSeparator());
			});
			execution.append("}");
		}
		return "section " + getSection().getName() + execution.toString() + " then " + getThen().renderAction();
	}
}
