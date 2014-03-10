package grapher.ui;

import grapher.fc.FunctionFactory;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class FunctionTableModel extends AbstractTableModel {
	private String[] columnNames = {"Function",	"Color"};
	private Object[][] data;
	private Grapher g;
	private DefaultListModel<String> list;
	private Main main;
	public FunctionTableModel(Main main, DefaultListModel<String> list, String[] expressions) {
		int i = 0;
		this.list = list;
		this.main = main;
		data = new Object[expressions.length][2];
		for(String s:expressions) {
			data[i][0] = s;
			data[i][1] = Color.black;
			i++;
		}
		
	}

	public String getFunctionNameAt(int index) {
		return (String) data[index][0];
	}

	public Color getFunctionColorAt(int index) {
		return (Color) data[index][1];
	}

	public void addRow(String name, Color color) {
		Object[][] temp = data;
		data = new Object[data.length+1][2];
		int i = 0;
		for (i = 0; i < temp.length; i++)
		{
			data[i] = temp[i];
		}
		data[i][0] = name;
		data[i][1] = color;
		fireTableDataChanged();
	}
	
	public void delRow(int num) {
		int j = 0;
		
		Object[][] temp = data;
		data = new Object[data.length-1][2];
		for (int i = 0; i < temp.length; i++)
		{
			if (num != i) {
				data[j] = temp[i];
				j++;
			}
		}
		fireTableDataChanged();
	}
	
	public void addRow(String name) {
		this.addRow(name, Color.BLACK);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	/*
	 * JTable uses this method to determine the default renderer/
	 * editor for each cell.  If we didn't implement this method,
	 * then the last column would contain text ("true"/"false"),
	 * rather than a check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		return true;
	}

	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + this.getFunctionNameAt(i) + this.getFunctionColorAt(j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return this.getFunctionNameAt(rowIndex);
		else
			return this.getFunctionColorAt(rowIndex);
	}

	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			main.grapher.functions.add(FunctionFactory.createFunction((String) value));
			list.removeElement((String)data[row][col]);
			list.addElement((String) value);
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
		main.grapher.repaint();
	}
	
	
	public void changeColorAt(int index, Color c) {
		data[index][1] = c;
	}

	public void changeNameAt(int index, String s) {
		data[index][0] = s;
	}

}
