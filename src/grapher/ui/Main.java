package grapher.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu expr = new JMenu("Expression");
		JMenuItem add = new JMenuItem("Add");
		JMenuItem remove= new JMenuItem("Remove");
		
		JSplitPane jSP;
		JPanel jP = new JPanel();
		JPanel jPB = new JPanel();
		JPanel jPBW = new JPanel();
		JButton bPlus = new JButton("+");
		JButton bMinus = new JButton("-");
		DefaultListModel<String> list = new DefaultListModel<String>();

		JList lFunc = new JList<String>(list);


		add.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		remove.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_BACK_SPACE, ActionEvent.CTRL_MASK));
		expr.add(add);
		expr.add(remove);
		menuBar.add(expr);
		setJMenuBar(menuBar);

		jP.setLayout(new BorderLayout());
		jP.add(lFunc, BorderLayout.CENTER);
		jP.add(jPBW, BorderLayout.SOUTH);

		jPBW.setLayout(new BorderLayout());
		jPB.setLayout(new GridLayout(1, 2));

		jPB.add(bPlus);
		jPB.add(bMinus);
		jPBW.add(jPB, BorderLayout.WEST);
		jPBW.add(new JPanel(), BorderLayout.CENTER);

		Grapher grapher = new Grapher(list, lFunc);		
		for(String expression : expressions) {
			grapher.add(expression);
		}

		lFunc.addListSelectionListener(new ListListener(grapher));

		ButtonListener bl = new ButtonListener(grapher, lFunc);
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
