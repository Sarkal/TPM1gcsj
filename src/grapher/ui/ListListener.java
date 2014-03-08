package grapher.ui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListListener implements ListSelectionListener {
	private Grapher g;

	public ListListener(Grapher g) {
		super();
		this.g = g;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		g.repaint();
	}

}
