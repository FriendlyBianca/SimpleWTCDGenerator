package city.makai.wtcd.generator.components;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextField;

import city.makai.wtcd.generator.definitions.Action;
import city.makai.wtcd.generator.definitions.Goto;
import city.makai.wtcd.generator.definitions.SectionContent;
import lombok.Getter;
import lombok.Setter;

public class SectionDefinitionFrame extends InternalFrame implements Actionable {
	private static final long serialVersionUID = 6648808502352843959L;
	public JTextField name;
	public JEditorPane content;
	@Getter
	@Setter
	Action action;
	SectionContent c = new SectionContent("", "");
	private JButton exectue;

	public SectionContent getSectionContent() {
		c.setContent(content.getText());
		c.setName(name.getText());
		return c;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SectionDefinitionFrame frame = new SectionDefinitionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SectionDefinitionFrame() {
		setBounds(100, 100, 233, 320);
		getContentPane().setLayout(null);

		name = new JTextField();
		name.setBounds(0, 0, 217, 33);
		getContentPane().add(name);
		name.setColumns(10);

		content = new JEditorPane();
		content.setBounds(0, 46, 217, 194);
		getContentPane().add(content);

		JButton connect = new JButton("\u8FDE\u7EBF");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GeneratorFrame.clicked != null) {
					Component clicked = GeneratorFrame.clicked;
					if (clicked instanceof Actionable) {
						((Actionable) clicked).setAction(Goto.section(c));
					}
					GeneratorFrame.addLine(SectionDefinitionFrame.this);
				} else {
					GeneratorFrame.clicked = SectionDefinitionFrame.this;
				}
			}
		});
		connect.setBounds(113, 253, 90, 27);
		getContentPane().add(connect);

		exectue = new JButton("\u6267\u884C");
		exectue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneratorFrame.clicked = SectionDefinitionFrame.this;
			}
		});
		exectue.setBounds(10, 253, 90, 27);
		getContentPane().add(exectue);

	}

}
