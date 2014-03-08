package grapher.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;


public class Main extends JFrame {
	@SuppressWarnings("unchecked")
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		JSplitPane jSP;
		JPanel jP = new JPanel();
		JPanel jPB = new JPanel();
		JPanel jPBW = new JPanel();
		JButton bPlus = new JButton("+");
		JButton bMinus = new JButton("-");
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		JList lFunc = new JList<String>(list);
		
		

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
		
		jSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jP, grapher) ;
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
