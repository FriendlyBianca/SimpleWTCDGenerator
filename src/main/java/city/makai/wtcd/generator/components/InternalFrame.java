package city.makai.wtcd.generator.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JInternalFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InternalFrame extends JInternalFrame implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = -16100258219074176L;

	public InternalFrame() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				GeneratorFrame.frame.repaint();
			}
		});
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GeneratorFrame.frame.repaint();
	}
}
