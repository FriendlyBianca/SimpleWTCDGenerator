package city.makai.wtcd.generator.components;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class SelectionDefinitionFrame extends InternalFrame {

	private static final long serialVersionUID = 6632987648338832501L;
	city.makai.wtcd.generator.definitions.Selection selection = new city.makai.wtcd.generator.definitions.Selection();
	List<SingleChoice> choices = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectionDefinitionFrame frame = new SelectionDefinitionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	int i = 0;

	public city.makai.wtcd.generator.definitions.Selection getSelection() {
		selection.clear();
		choices.forEach(e -> {
			if (e.choice.getText() != null && !e.choice.getText().isEmpty() && e.action != null) {
				selection.addAction(e.choice.getText(), e.action);
			}
		});
		return selection;
	}

	/**
	 * Create the frame.
	 */
	public SelectionDefinitionFrame() {
		setBounds(100, 100, 278, 346);

		JButton enter = new JButton("\u8FDB\u5165\u9009\u62E9\u652F");
		enter.setBounds(28, 5, 107, 27);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GeneratorFrame.clicked != null) {
					Component clicked = GeneratorFrame.clicked;
					if (clicked instanceof Actionable) {
						((Actionable) clicked).setAction(selection);
					}
					GeneratorFrame.addLine(SelectionDefinitionFrame.this);
				}
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(enter);

		JButton add = new JButton("\u6DFB\u52A0\u9009\u9879");
		add.setBounds(140, 5, 93, 27);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SingleChoice c;
				SelectionDefinitionFrame.this.getContentPane().add(c = new SingleChoice());
				i++;
				c.setLocation(0, (i + 1) * 30);
				c.father = SelectionDefinitionFrame.this;
				choices.add(c);
			}
		});
		getContentPane().add(add);

	}
}
