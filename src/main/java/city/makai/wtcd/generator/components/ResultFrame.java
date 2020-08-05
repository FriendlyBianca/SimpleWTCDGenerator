package city.makai.wtcd.generator.components;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import java.awt.BorderLayout;

public class ResultFrame extends JFrame {
	JEditorPane result;

	public ResultFrame() {
		result = new JEditorPane();
		this.setSize(525, 825);
		getContentPane().add(result, BorderLayout.CENTER);
	}

	private static final long serialVersionUID = -4336757813797388544L;

}
