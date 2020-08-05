package city.makai.wtcd.generator.definitions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class Variable<T> implements WTCDRenderer {
	String type;
	String name;
	T value;

	public String render() {
		return "";
	}

	@Override
	public String code() {
		return new StringBuilder().append("declare [").append(System.lineSeparator())
				.append("  " + type + " " + name + " = " + value.toString()).append(System.lineSeparator()).append("]")
				.toString();
	}
}
