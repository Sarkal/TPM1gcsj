package grapher.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class FunctionTable extends JTable implements MouseListener{
	private Main main;

	public FunctionTable(Main main, FunctionTableModel functionTableModel) {
		// TODO Auto-generated constructor stub
		super(functionTableModel);
		this.addMouseListener(this);
		this.main = main;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

		Point p = arg0.getPoint();

		int row = this.rowAtPoint(p);
		int column = this.convertColumnIndexToModel(this.columnAtPoint(p));
		if (row >= 0 && column >= 0) {
			this.getValueAt(row, 0);
			main.grapher.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
