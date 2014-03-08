package grapher.ui;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseAllListener  implements MouseListener, MouseMotionListener, MouseWheelListener{
	private Grapher g;
	Point p, oP, dP;
	final static int NB = 0, BG = 1, BM = 2, BD = 3, MARGE_CLIC = 10;
	int buttonPressed = NB;
	
	MouseAllListener(Grapher g)
	{
		this.g = g;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int nb;
		Point p;
		
		p = arg0.getPoint();
		
		nb = arg0.getWheelRotation();
		
		if (nb < 0)
			g.zoom(p, 5);
		else
			g.zoom(p, -5);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch (arg0.getButton()) {
		case BG:
			g.zoom(p, 5);
			break;
		case BD:
			g.zoom(p, -5);
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		switch (arg0.getButton()) {
		case BG: // clic gauche
			buttonPressed = BG;
			p = arg0.getPoint();
			oP = arg0.getPoint();
			
			break;
		case BM: // clic milieu
			break;
		case BD: // clic droit
			buttonPressed = BD;
			p = arg0.getPoint();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		switch (arg0.getButton()) {
		case BG: // clic gauche
			g.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			buttonPressed = NB;
			break;
		case BM: // clic milieu
			break;
		case BD: // clic droit
			g.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			dP = arg0.getPoint();
			
			if (Math.abs(dP.x - p.x) > MARGE_CLIC || Math.abs(dP.y - p.y) > MARGE_CLIC)
				g.zoom(p, dP);
			buttonPressed = NB;
			break;
		}
	}

	
	public void mouseDragged(MouseEvent arg0) {
		Graphics2D g2 = (Graphics2D) g.getGraphics();
		Point p1 = new Point();
		int x, y;
		
		switch (buttonPressed) {
		case BG: // clic gauche
			g.setCursor(new Cursor(Cursor.HAND_CURSOR));
			dP = arg0.getPoint();
			g.translate( dP.x - p.x, dP.y - p.y);
			p.x = dP.x;
			p.y = dP.y;
			break;
		case BM: // clic milieu
			break;
		case BD: // clic droit
			g.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			dP = arg0.getPoint();
			g.paintComponent(g2);
			
			if (p.x < dP.x)
				p1.x = p.x;
			else
				p1.x = dP.x;
			
			if (p.y < dP.y)
				p1.y = p.y;
			else
				p1.y = dP.y;
			
			x = Math.abs(dP.x - p.x);
			y = Math.abs(dP.y - p.y);
			
			g2.drawRect(p1.x, p1.y, x, y);
			
			break;
		}
	}

	
	public void mouseMoved(MouseEvent arg0) {
		
	}
}
