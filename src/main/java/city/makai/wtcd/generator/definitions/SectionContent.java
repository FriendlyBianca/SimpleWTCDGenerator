package city.makai.wtcd.generator.definitions;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SectionContent {
	@NonNull
	String name;
	@NonNull
	String content;
	List<LineOfCode> codeToExecute = new ArrayList<>();
}
