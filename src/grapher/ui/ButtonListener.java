package grapher.ui;

import grapher.fc.Function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
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
			JOptionPane box = new JOptionPane();
			String inputValue = JOptionPane.showInputDialog("Nouvelle expression :");
			if (inputValue != null)
				g.add(inputValue);
			break;
		case "-":
		case "Remove":
			System.out.println((String)l.getSelectedValue());
			g.del((String)l.getSelectedValue());
			break;
		case "Color...":
			System.out.println("Color...");
			break;
		case "Modify...":
			System.out.println("Modify...");
			break;
		}
		
	}
	
}
