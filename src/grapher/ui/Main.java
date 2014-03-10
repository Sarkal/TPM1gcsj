package grapher.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class Main extends JFrame {
	Grapher grapher;
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu expr = new JMenu("Expression");
		JMenuItem add = new JMenuItem("Add");
		JMenuItem remove = new JMenuItem("Remove");
		
		JSplitPane jSP;
		JPanel jP = new JPanel();
		JPanel jPB = new JPanel();
		JPanel jPBW = new JPanel();
		JButton bPlus = new JButton("+");
		JButton bMinus = new JButton("-");
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		FunctionTable jTableFunc = new FunctionTable(this, new FunctionTableModel(this, list, expressions));
		jTableFunc.setDefaultRenderer(Color.class, new ColorRenderer(true));
		jTableFunc.setDefaultEditor(Color.class, new ColorEditor());
		
		grapher = new Grapher(list, jTableFunc);		
		for(String expression : expressions) {
			grapher.add(expression);
		}
		
		add.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		remove.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_DELETE, 0));
		expr.add(add);
		expr.add(remove);
		menuBar.add(expr);
		setJMenuBar(menuBar);

		jP.setLayout(new BorderLayout());
		jP.add(jTableFunc, BorderLayout.CENTER);
		jP.add(jPBW, BorderLayout.SOUTH);

		jPBW.setLayout(new BorderLayout());
		jPB.setLayout(new GridLayout(1, 2));

		jPB.add(bPlus);
		jPB.add(bMinus);
		jPBW.add(jPB, BorderLayout.WEST);
		jPBW.add(new JPanel(), BorderLayout.CENTER);

		ButtonListener bl = new ButtonListener(grapher, jTableFunc);
		bPlus.addActionListener(bl);
		bMinus.addActionListener(bl);
		add.addActionListener(bl);
		remove.addActionListener(bl);

		jSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jP, grapher);
		this.add(jSP);

		pack();
	}

	public static void main(String[] argv) {
		final String[] expressions = argv;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				new Main("grapher", expressions).setVisible(true); 
			}
		});
	}
}
