package city.makai.wtcd.generator.definitions;

import java.util.ArrayList;
import java.util.List;

public class WTCDDocument implements WTCDRenderer {
	public static int selectionExtraSpace = 0;

	String wtcdVersion = "1.3";
	List<WTCDRenderer> entity = new ArrayList<>();

	public WTCDDocument add(WTCDRenderer e) {
		entity.add(e);
		return this;
	}

	@Override
	public String render() {
		StringBuilder builder = new StringBuilder();
		entity.forEach(e -> {
			String entity = e.render();
			if (entity != null && !entity.isEmpty())
				builder.append(entity).append(System.lineSeparator());
		});
		return builder.toString();
	}

	@Override
	public String code() {
		StringBuilder builder = new StringBuilder();
		builder.append("WTCD " + wtcdVersion).append(System.lineSeparator());
		entity.forEach(e -> {
			builder.append(e.code()).append(System.lineSeparator());
		});

		return builder.toString();
	}

	public String renderDocument() {
		StringBuilder builder = new StringBuilder();

		builder.append(code()).append(System.lineSeparator());
		builder.append(render());

		return builder.toString();
	}
}
