package city.makai.wtcd.generator.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import city.makai.wtcd.generator.definitions.Exit;
import city.makai.wtcd.generator.definitions.Goto;
import city.makai.wtcd.generator.definitions.Section;
import city.makai.wtcd.generator.definitions.SectionContent;
import city.makai.wtcd.generator.definitions.Variable;
import city.makai.wtcd.generator.definitions.WTCDDocument;

public class GeneratorFrame extends MouseAdapter {

	public static HashMap<Component, Set<Component>> lines = new HashMap<>();
	public static HashMap<Component, Component> logics = new HashMap<>();
	public static List<Variable<?>> variables = new ArrayList<>();
	public static Component clicked = null;
	public static JFrame frame;
	static JButton exit;
	
	Point lastCursorPoint;
	ResultFrame result = new ResultFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GeneratorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GeneratorFrame() {
		initialize();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastCursorPoint = e.getPoint();
		frame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent() == null ? frame.getComponentAt(e.getPoint()) : e.getComponent();
		if (c == null || c == clicked || c instanceof JRootPane || c instanceof JPanel
				|| (c instanceof JFrame && !(c instanceof JInternalFrame))) {
			lines.put(clicked, new HashSet<>());
			if (clicked instanceof Actionable) {
				((Actionable) clicked).setAction(new Exit());
			}
			clicked = null;
			return;
		}

		if (c == exit) {
			if (clicked instanceof Actionable) {
				((Actionable) clicked).setAction(new Exit());
			}
		}

		addLine(c);
	}

	public static void addLine(Component c) {
		Set<Component> l;
		if (!lines.containsKey(clicked)) {
			lines.put(clicked, l = new HashSet<>());
		} else {
			l = lines.get(clicked);
		}
		if (!(clicked instanceof SelectionDefinitionFrame))
			l.clear();
		l.add(c);

		clicked = null;
	}

	public static void addLogic(Component c) {
		logics.put(clicked, c);
		clicked = null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame() {
			@Override
			public void paintComponents(Graphics g) {
				super.paintComponents(g);
			}
		};
		frame.setBounds(100, 100, 800, 653);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);

		JLayeredPane layeredPane = new JLayeredPane() {
			private static final long serialVersionUID = 3765015234769621063L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setStroke(new BasicStroke(2));
				g2d.setColor(new Color(0, 0, 0, 120));
				int strokeRadius = 4;
				if (clicked != null) {
					Component k = clicked;
					int kOffsetX = 0;
					int kOffsetY = 0;
					int eOffsetX = -10;
					int eOffsetY = -40;
					if (k instanceof SectionDefinitionFrame) {
						kOffsetY += 80;
					}
					if (k instanceof SingleChoice) {
						kOffsetX += ((SingleChoice) k).father.getX() + 90;
						kOffsetY += ((SingleChoice) k).father.getY() - 20;
					}
					g.fillRect(kOffsetX + k.getX() + k.getWidth() / 2 - strokeRadius,
							kOffsetY + k.getY() + k.getHeight() / 2 - strokeRadius, strokeRadius * 2, strokeRadius * 2);
					g.drawRect(eOffsetX + lastCursorPoint.x - this.getX() - strokeRadius,
							eOffsetY + lastCursorPoint.y - this.getY() - strokeRadius, strokeRadius * 2,
							strokeRadius * 2);
					g.drawLine(kOffsetX + k.getX() + k.getWidth() / 2, kOffsetY + k.getY() + k.getHeight() / 2,
							eOffsetX + lastCursorPoint.x - this.getX(), eOffsetY + lastCursorPoint.y - this.getY());
				}
				g2d.setColor(new Color(200, 50, 50, 120));
				logics.forEach((k, e) -> {
					int kOffsetX = 0;
					int kOffsetY = 0;
					int eOffsetX = 0;
					int eOffsetY = 0;
					if (k instanceof SectionDefinitionFrame) {
						kOffsetX -= 30;
						kOffsetY += 85;
					}
					Point p = (Point) e.getLocationOnScreen().clone();
					p.translate(-(int) this.getLocationOnScreen().getX(), -(int) this.getLocationOnScreen().getY());

					eOffsetX += p.x + 20;
					eOffsetY += p.y - 50;
					if (((JButton) e).getText().equals("移除标记")) {
						eOffsetY -= 40;
					}

					g.fillRect(kOffsetX + k.getX() + k.getWidth() / 2 - strokeRadius,
							kOffsetY + k.getY() + k.getHeight() / 2 - strokeRadius, strokeRadius * 2, strokeRadius * 2);
					g.drawRect(eOffsetX + e.getX() + e.getWidth() / 2 - strokeRadius,
							eOffsetY + e.getY() + e.getHeight() / 2 - strokeRadius, strokeRadius * 2, strokeRadius * 2);
					g.drawLine(kOffsetX + k.getX() + k.getWidth() / 2, kOffsetY + k.getY() + k.getHeight() / 2,
							eOffsetX + e.getX() + e.getWidth() / 2, eOffsetY + e.getY() + e.getHeight() / 2);
				});
				g2d.setColor(new Color(0, 0, 0, 120));
				lines.forEach((k, v) -> {
					v.forEach(e -> {
						int kOffsetX = 0;
						int kOffsetY = 0;
						int eOffsetX = 0;
						int eOffsetY = 0;
						if (k instanceof SectionDefinitionFrame) {
							kOffsetX += 30;
							kOffsetY += 85;
						}
						if (k instanceof ButtonWithFather) {
							Point p = (Point) k.getLocationOnScreen().clone();
							p.translate(-(int) this.getLocationOnScreen().getX(),
									-(int) this.getLocationOnScreen().getY());
							kOffsetX += p.x + 20;
							kOffsetY += p.y - 205;
							if (((JButton) k).getText().equals("未标记时")) {
								kOffsetY -= 40;
							}
						}
						if (k instanceof SingleChoice) {
							kOffsetX += ((SingleChoice) k).father.getX() + 120;
							kOffsetY += ((SingleChoice) k).father.getY() - 20;
						}
						if (e instanceof SelectionDefinitionFrame) {
							eOffsetY += -200;
						}
						if (e instanceof SectionDefinitionFrame) {
							eOffsetY += -200;
						}
						g.fillRect(kOffsetX + k.getX() + k.getWidth() / 2 - strokeRadius,
								kOffsetY + k.getY() + k.getHeight() / 2 - strokeRadius, strokeRadius * 2,
								strokeRadius * 2);
						g.drawRect(eOffsetX + e.getX() + e.getWidth() / 2 - strokeRadius,
								eOffsetY + e.getY() + e.getHeight() / 2 - strokeRadius, strokeRadius * 2,
								strokeRadius * 2);
						g.drawLine(kOffsetX + k.getX() + k.getWidth() / 2, kOffsetY + k.getY() + k.getHeight() / 2,
								eOffsetX + e.getX() + e.getWidth() / 2, eOffsetY + e.getY() + e.getHeight() / 2);
					});
				});
			}
		};
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				exit.setLocation(frame.getWidth() - 150, frame.getHeight() - 140);
				layeredPane.setSize(frame.getWidth(), frame.getHeight());
			}
		});
		layeredPane.setBounds(0, 50, 782, 556);
		frame.getContentPane().add(layeredPane);

		JButton start = new JButton("\u5F00\u59CB");
		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clicked = start;
			}
		});
		start.setBounds(14, 13, 113, 27);
		layeredPane.add(start);

		exit = new JButton("\u7ED3\u675F");
		exit.addMouseListener(this);
		exit.setBorder(new LineBorder(Color.BLACK));
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.setBackground(Color.LIGHT_GRAY);
		exit.setBounds(655, 516, 113, 27);
		layeredPane.add(exit);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = -3945362413444975966L;
			String[] values = new String[] { "1.3" };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(117, 12, 99, 27);
		frame.getContentPane().add(list);

		JLabel lblNewLabel = new JLabel("WTCD \u7248\u672C\u53F7");
		lblNewLabel.setBounds(14, 13, 125, 18);
		frame.getContentPane().add(lblNewLabel);

		JButton generate = new JButton("\u751F\u6210");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LinkedList<Section> sections = new LinkedList<>();
				logics.forEach((k, v) -> {
					if (k instanceof SetFlag) {
						((SetFlag) k).getFinalAction();
						return;
					}
					if (v instanceof SetFlag) {
						((SetFlag) v).getFinalAction();
						return;
					}
					if (k instanceof SetFlag.ButtonWithAction) {
						((SetFlag) ((SetFlag.ButtonWithAction) k).father).getFinalAction();
						return;
					}
					if (v instanceof SetFlag.ButtonWithAction) {
						((SetFlag) ((SetFlag.ButtonWithAction) v).father).getFinalAction();
						return;
					}
				});
				lines.forEach((k, v) -> {
					if (k == start) {
						Component a = null;

						if (v.size() > 0) {
							a = v.iterator().next();
						}
						if (a instanceof SetFlag) {
							((SetFlag) a).getFinalAction();
							return;
						}
						if (a instanceof SetFlag.ButtonWithAction) {
							((SetFlag) ((SetFlag.ButtonWithAction) a).father).getFinalAction();
							return;
						}
						if (a instanceof SelectionDefinitionFrame) {
							sections.addFirst(new Section(new SectionContent("__internal_start", ""),
									((SelectionDefinitionFrame) a).getSelection()));
						} else if (a instanceof SectionDefinitionFrame) {
							sections.addFirst(new Section(new SectionContent("__internal_start", ""),
									Goto.section(((SectionDefinitionFrame) a).c)));
						} else {
							sections.addFirst(new Section(new SectionContent("__internal_start", ""), new Exit()));
						}
						return;
					}
					if (k == exit) {
						return;
					}
					if (k instanceof SetFlag) {
						((SetFlag) k).getFinalAction();
						return;
					}
					if (k instanceof SetFlag.ButtonWithAction) {
						((SetFlag) ((SetFlag.ButtonWithAction) k).father).getFinalAction();
						return;
					}
					if (k instanceof SelectionDefinitionFrame) {
						((SelectionDefinitionFrame) k).getSelection();
						return;
					}
					if (k instanceof SingleChoice) {
						((SingleChoice) k).father.getSelection();
						return;
					}
					if (k instanceof SectionDefinitionFrame) {
						sections.addLast(new Section(((SectionDefinitionFrame) k).getSectionContent(),
								((SectionDefinitionFrame) k).action));
					}
				});
				WTCDDocument document = new WTCDDocument();
				variables.forEach(e -> {
					document.add(e);
				});
				sections.forEach(e -> {
					document.add(e);
				});
				result.result.setText(document.renderDocument());
				result.setVisible(true);
			}
		});
		generate.setBounds(230, 10, 113, 27);
		frame.getContentPane().add(generate);

		JButton add = new JButton("\u6DFB\u52A0");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SectionDefinitionFrame f;
				frame.getContentPane().add(f = new SectionDefinitionFrame());
				f.setVisible(true);
			}
		});
		add.setBounds(357, 10, 113, 27);
		frame.getContentPane().add(add);

		JButton addSelection = new JButton("\u6DFB\u52A0\u9009\u62E9\u652F");
		addSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectionDefinitionFrame f;
				frame.getContentPane().add(f = new SelectionDefinitionFrame());
				f.setVisible(true);
			}
		});
		addSelection.setBounds(484, 10, 113, 27);
		frame.getContentPane().add(addSelection);

		JButton addVariable = new JButton("\u6DFB\u52A0\u53D8\u91CF");
		addVariable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetFlag f;
				frame.getContentPane().add(f = new SetFlag());
				f.setVisible(true);
			}
		});
		addVariable.setBounds(611, 9, 113, 27);
		frame.getContentPane().add(addVariable);
	}
}
