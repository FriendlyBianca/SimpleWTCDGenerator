package city.makai.wtcd.generator.components;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import city.makai.wtcd.generator.definitions.Action;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

public class SingleChoice extends JPanel implements Actionable {
	private static final long serialVersionUID = -3416221301900013926L;
	public SelectionDefinitionFrame father;
	@Getter
	@Setter
	public Action action;
	public JTextField choice;

	/**
	 * Create the panel.
	 */
	public SingleChoice() {
		setSize(285, 30);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);

		JButton link = new JButton("\u8FDE\u7EBF");
		link.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneratorFrame.clicked = SingleChoice.this;
			}
		});
		link.setBounds(169, 0, 113, 27);
		add(link);

		choice = new JTextField();
		choice.setBounds(0, 1, 165, 24);
		add(choice);
		choice.setColumns(10);

	}
}
