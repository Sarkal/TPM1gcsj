package grapher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class ButtonListener implements ActionListener {
	Grapher g;
	JList l;
	
	public ButtonListener(Grapher g, JList l) {
		this.g = g;
		this.l = l;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "+":
		case "Add":
			String inputValue = JOptionPane.showInputDialog("Nouvelle expression :");
			if (inputValue != null)
				g.add(inputValue);
			break;
		case "-":
		case "Remove":
			System.out.println((String)l.getSelectedValue());
			g.del((String)l.getSelectedValue());
			break;
		}
		
	}
	
}
