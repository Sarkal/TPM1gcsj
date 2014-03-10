package grapher.ui;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.floor;
import static java.lang.Math.log10;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import grapher.fc.Function;
import grapher.fc.FunctionFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;


public class Grapher extends JPanel{
	static final int MARGIN = 40;
	static final int STEP = 15;

	static final BasicStroke dash = new BasicStroke(1, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND,
			1.f,
			new float[] { 4.f, 4.f },
			0.f);
	static final BasicStroke bold = new BasicStroke(2);
	static final BasicStroke normal = new BasicStroke();

	protected int W = 400;
	protected int H = 300;

	protected double xmin, xmax;
	protected double ymin, ymax;

	protected Vector<Function> functions;

	private DefaultListModel<String> list;
	private FunctionTable jTableFunc;

	public Grapher(DefaultListModel<String> list, FunctionTable jTableFunc) {
		xmin = -PI/2.; xmax = 3*PI/2;
		ymin = -1.5;   ymax = 1.5;

		functions = new Vector<Function>();
		MouseAllListener listener = new MouseAllListener(this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.addMouseWheelListener(listener);

		this.list = list;
		this.jTableFunc = jTableFunc;
	}

	public void add(String expression) {
		add(FunctionFactory.createFunction(expression));
		repaint();
	}

	public void add(Function function) {
		functions.add(function);
		list.addElement(function.toString());
		repaint();
	}

	public void del(String expression) {
		if (expression != null) {
			for(Function f:functions) {
				if (f.toString().equals(expression)) {
					functions.remove(f);
					list.removeElement(expression);
					break;
				}
			}
			repaint();
		}
	}

	public void del(Function function) {
		functions.remove(function);
		list.removeElement(function.toString());
		repaint();
	}

	public Dimension getPreferredSize() { return new Dimension(W, H); }

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		W = getWidth();
		H = getHeight();

		Graphics2D g2 = (Graphics2D)g;

		// background
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, W, H);

		g2.setColor(Color.BLACK);

		// box
		g2.translate(MARGIN, MARGIN);
		W -= 2*MARGIN;
		H -= 2*MARGIN;
		if(W < 0 || H < 0) { 
			return; 
		}

		g2.drawRect(0, 0, W, H);

		g2.drawString("x", W, H+10);
		g2.drawString("y", -10, 0);


		// plot
		g2.clipRect(0, 0, W, H);
		g2.translate(-MARGIN, -MARGIN);

		// x values
		final int N = W/STEP + 1;
		final double dx = dx(STEP);
		double xs[] = new double[N];
		int    Xs[] = new int[N];
		for(int i = 0; i < N; i++) {
			double x = xmin + i*dx;
			xs[i] = x;
			Xs[i] = X(x);
		}

		for(Function f : functions) {
			if (this.isFuncInList(f)) {
				// y values
				int Ys[] = new int[N];
				for(int i = 0; i < N; i++) {
					Ys[i] = Y(f.y(xs[i]));
				}
				int rowSelected = jTableFunc.getSelectedRow();
				int colSelected = jTableFunc.getSelectedColumn();

				// Si la fonction a afficher correspond a la fonction selectionnee, on la dessine en gras
				if (0 <= rowSelected && rowSelected < jTableFunc.getRowCount() && 
						0 <= colSelected && colSelected < jTableFunc.getColumnCount() &&
						f.toString().equals((String)jTableFunc.getValueAt(rowSelected, 0))) {
					g2.setStroke(bold);
				}

				TableModel model = jTableFunc.getModel(); 
				int i = 0;
				// On recupere la couleur actuelle de la fonction a afficher
				while (i < jTableFunc.getRowCount() && !f.toString().equals((String)jTableFunc.getValueAt(i, 0)))
					i++;

				if (i >= jTableFunc.getRowCount())
					g2.setColor(Color.BLACK);
				else {
					g2.setColor((Color)jTableFunc.getModel().getValueAt(i, 1));
				}

				g2.drawPolyline(Xs, Ys, N);
				g2.setStroke(normal);
				g2.setColor(Color.BLACK);
			}
		}

		g2.setClip(null);

		// axes
		drawXTick(g2, 0);
		drawYTick(g2, 0);

		double xstep = unit((xmax-xmin)/10);
		double ystep = unit((ymax-ymin)/10);

		g2.setStroke(dash);
		for(double x = xstep; x < xmax; x += xstep)  { drawXTick(g2, x); }
		for(double x = -xstep; x > xmin; x -= xstep) { drawXTick(g2, x); }
		for(double y = ystep; y < ymax; y += ystep)  { drawYTick(g2, y); }
		for(double y = -ystep; y > ymin; y -= ystep) { drawYTick(g2, y); }
	}

	private boolean isFuncInList(Function f) {
		return list.contains(f.toString());
	}

	protected double dx(int dX) { return  (double)((xmax-xmin)*dX/W); }
	protected double dy(int dY) { return -(double)((ymax-ymin)*dY/H); }

	protected double x(int X) { return xmin+dx(X-MARGIN); }
	protected double y(int Y) { return ymin+dy((Y-MARGIN)-H); }

	protected int X(double x) { 
		int Xs = (int)round((x-xmin)/(xmax-xmin)*W);
		return Xs + MARGIN; 
	}
	protected int Y(double y) { 
		int Ys = (int)round((y-ymin)/(ymax-ymin)*H);
		return (H - Ys) + MARGIN;
	}

	protected void drawXTick(Graphics2D g2, double x) {
		if(x > xmin && x < xmax) {
			final int X0 = X(x);
			g2.drawLine(X0, MARGIN, X0, H+MARGIN);
			g2.drawString((new Double(x)).toString(), X0, H+MARGIN+15);
		}
	}

	protected void drawYTick(Graphics2D g2, double y) {
		if(y > ymin && y < ymax) {
			final int Y0 = Y(y);
			g2.drawLine(0+MARGIN, Y0, W+MARGIN, Y0);
			g2.drawString((new Double(y)).toString(), 5, Y0);
		}
	}

	protected static double unit(double w) {
		double scale = pow(10, floor(log10(w)));
		w /= scale;
		if(w < 2)      { w = 2; } 
		else if(w < 5) { w = 5; }
		else           { w = 10; }
		return w * scale;
	}


	protected void translate(int dX, int dY) {
		double dx = dx(dX);
		double dy = dy(dY);
		xmin -= dx; xmax -= dx;
		ymin -= dy; ymax -= dy;
		repaint();	
	}

	protected void zoom(Point center, int dz) {
		double x = x(center.x);
		double y = y(center.y);
		double ds = exp(dz*.01);
		xmin = x + (xmin-x)/ds; xmax = x + (xmax-x)/ds;
		ymin = y + (ymin-y)/ds; ymax = y + (ymax-y)/ds;
		repaint();	
	}

	protected void zoom(Point p0, Point p1) {
		double x0 = x(p0.x);
		double y0 = y(p0.y);
		double x1 = x(p1.x);
		double y1 = y(p1.y);
		xmin = min(x0, x1); xmax = max(x0, x1);
		ymin = min(y0, y1); ymax = max(y0, y1);
		repaint();	
	}

	public Vector<Function> getFunctions() {
		return functions;
	}
}
