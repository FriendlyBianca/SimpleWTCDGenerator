package city.makai.wtcd.generator.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import city.makai.wtcd.generator.definitions.Action;
import city.makai.wtcd.generator.definitions.BooleanVariable;
import city.makai.wtcd.generator.definitions.GotoWithBoolean;
import city.makai.wtcd.generator.definitions.LineOfCode;
import lombok.Setter;

public class SetFlag extends InternalFrame {
	private static final long serialVersionUID = 1481615263568976309L;
	private JTextField name;
	public BooleanVariable value = new BooleanVariable("");
	public GotoWithBoolean action = new GotoWithBoolean(value, null, null);

	/**
	 * Create the frame.
	 */
	public SetFlag() {
		GeneratorFrame.variables.add(value);

		setBounds(100, 100, 153, 319);
		getContentPane().setLayout(null);

		JButton markFalse = new JButton("\u79FB\u9664\u6807\u8BB0");
		markFalse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component c = GeneratorFrame.clicked;
				if (!(c instanceof SectionDefinitionFrame)) {
					GeneratorFrame.clicked = null;
					return;
				}
				SectionDefinitionFrame s = (SectionDefinitionFrame) c;
				s.c.getCodeToExecute().add(new LineOfCode() {

					@Override
					public String code() {
						return name.getText() + " = false";
					}
				});
				GeneratorFrame.addLogic(markFalse);
			}
		});
		markFalse.setBounds(14, 90, 113, 27);
		getContentPane().add(markFalse);

		name = new JTextField();
		name.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				value.setName(name.getText());
			}
		});
		name.setBounds(14, 13, 109, 24);
		getContentPane().add(name);
		name.setColumns(10);

		JButton markTrue = new JButton("\u8BBE\u7F6E\u6807\u8BB0");
		markTrue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component c = GeneratorFrame.clicked;
				if (!(c instanceof SectionDefinitionFrame)) {
					GeneratorFrame.clicked = null;
					return;
				}
				SectionDefinitionFrame s = (SectionDefinitionFrame) c;
				s.c.getCodeToExecute().add(new LineOfCode() {

					@Override
					public String code() {
						return name.getText() + " = true";
					}
				});
				GeneratorFrame.addLogic(markTrue);
			}
		});
		markTrue.setBounds(14, 50, 113, 27);
		getContentPane().add(markTrue);

		JButton entry = new JButton("\u8FDB\u5165\u5224\u5B9A");
		entry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GeneratorFrame.clicked != null) {
					Component clicked = GeneratorFrame.clicked;
					if (clicked instanceof Actionable) {
						((Actionable) clicked).setAction(action);
					}
					GeneratorFrame.addLine(SetFlag.this);
				}
			}
		});
		entry.setBounds(14, 166, 113, 27);
		getContentPane().add(entry);

		withTrue = new TrueButton("\u6709\u6807\u8BB0\u65F6");
		withTrue.setFather(this);
		withTrue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneratorFrame.clicked = withTrue;
			}
		});
		withTrue.setBounds(14, 206, 113, 27);
		getContentPane().add(withTrue);

		withFalse = new FalseButton("\u672A\u6807\u8BB0\u65F6");
		withFalse.setFather(this);
		withFalse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneratorFrame.clicked = withFalse;
			}
		});
		withFalse.setBounds(14, 246, 113, 27);
		getContentPane().add(withFalse);

	}

	ButtonWithAction withTrue;
	ButtonWithAction withFalse;

	public Action getFinalAction() {
		value.setName(name.getText());
		action.whenFalse = withFalse.action;
		action.whenTrue = withTrue.action;
		return action;
	}

	public static class ButtonWithAction extends ButtonWithFather implements Actionable {
		private static final long serialVersionUID = 7117774680902934986L;
		@Setter
		public Action action;

		public ButtonWithAction(String string) {
			super(string);
		}

	}

	public static class TrueButton extends ButtonWithAction {

		public TrueButton(String string) {
			super(string);
		}

		private static final long serialVersionUID = 1458025875737967478L;
	}

	public static class FalseButton extends ButtonWithAction {

		public FalseButton(String string) {
			super(string);
		}

		private static final long serialVersionUID = 1458025875737967478L;
	}

}
