package city.makai.wtcd.generator.components;

import java.awt.Component;

import javax.swing.JButton;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ButtonWithFather extends JButton {
	@Getter
	@Setter
	public Component father;

	public ButtonWithFather(String string) {
		super(string);
	}

	private static final long serialVersionUID = 6405311965336762149L;

}
