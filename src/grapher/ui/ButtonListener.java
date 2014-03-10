package grapher.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ButtonListener implements ActionListener {
	Grapher g;
	JTable t;
	
	public ButtonListener(Grapher g, JTable t) {
		this.g = g;
		this.t = t;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		FunctionTable ft = (FunctionTable)t.getModel();
		switch (arg0.getActionCommand()) {
		case "+":
		case "Add":
			System.out.println("add");
			String inputValue = JOptionPane.showInputDialog("Nouvelle expression :");
			if (inputValue != null) {
				if (g.add(inputValue) != -1)
					ft.addRow(inputValue, Color.black);
			}
			g.repaint();
			break;
		case "-":
		case "Remove":
			int numRow = t.getSelectedRow();
			String name;
			
			if (numRow >= 0) {
				name = (String)t.getValueAt(numRow, 0);

				System.out.println(name);
				ft.delRow(t.getSelectedRow());
				g.del(name);
				g.repaint();
			}
			break;
		}
		
	}
	
}
