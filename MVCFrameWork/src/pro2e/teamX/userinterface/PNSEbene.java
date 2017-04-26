package pro2e.teamX.userinterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;

import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;

public class PNSEbene extends JPanel {
	private Complex[] rA;
	private double frequency = 0.0;
	private double scale, scaleFactor = 1.0;;

	public PNSEbene() {
		super(null);
		setPreferredSize(new Dimension(300, 325));
	//	setBorder(MyBorderFactory.createMyBorder(" " + getClass().getName() + " "));
		
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					scaleFactor *= 2.0;
				} else {
					scaleFactor /= 2.0;
				}
				if (scaleFactor < 1.0) scaleFactor = 1.0;
				if (scaleFactor > 16.0) scaleFactor = 16.0;
				repaint();
			}
		});


		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int xOffset = getWidth() / 2;
		int yOffset = getHeight() / 2;
		Shape sClip = g.getClipBounds();
		g.setClip(20, 20, getWidth() - 40, getHeight() - 40);
		double minDim = Math.min(getWidth(), getHeight());
		scale = scaleFactor * 0.45 * minDim / 12e3;
		int v = (int) (scale * 12.5e3);

		g.setColor(Color.BLACK);
		fillArrowRight(g, v / 2 + xOffset, yOffset);
		g.drawLine(-v + xOffset, yOffset, v / 2 + xOffset, yOffset);
		fillArrowUp(g, xOffset, -v + yOffset);
		g.drawLine(xOffset, v + yOffset, xOffset, -v + yOffset);

		g.drawRect(getWidth() - 50, 50, 20, 20);
		g.drawString("s", getWidth() - 50 + 8, 50 + 13);

//		if (rA != null) {
//			g.setColor(Color.RED);
//			for (int i = 0; i < rA.length; i++) {
//				int x = (int) (scale * rA[i].re / (2.0 * Math.PI) + xOffset);
//				int y = (int) (scale * rA[i].im / (2.0 * Math.PI) + yOffset);
//				drawCross(g, x, y);
//			}
//		}

		int x = xOffset;
		int y1 = (int) (scale * frequency) + yOffset;
		int y2 = (int) (-scale * frequency) + yOffset;

		g.setColor(Color.red);
		g.fillRect(x - 3, y1 - 3, 6, 6);
		g.fillRect(x - 3, y2 - 3, 6, 6);
		g.setClip(sClip);
	}

	private void fillArrowUp(Graphics g, int x, int y) {
		int[] xp = { x, x + 5, x - 5 };
		int[] yp = { y, y + 15, y + 15 };
		g.fillPolygon(xp, yp, 3);
	}

	private void fillArrowRight(Graphics g, int x, int y) {
		int[] yp = { y, y + 5, y - 5 };
		int[] xp = { x, x - 15, x - 15 };
		g.fillPolygon(xp, yp, 3);
	}

	private void drawCross(Graphics g, int x, int y) {
		((Graphics2D) g).setStroke(new BasicStroke(1.0f));
		g.drawLine(x - 5, y, x + 5, y);
		g.drawLine(x, y + 5, x, y - 5);
		g.drawLine(x - 3, y - 3, x + 3, y + 3);
		g.drawLine(x - 3, y + 3, x + 3, y - 3);
	}

	public void update(Observable obs, Object obj) {
//		Model model = (Model) obs;
//		Filter filter = model.getFilter();
	//	this.rA = filter.rA;
	//	frequency = model.getTestFrequenz();
		repaint();
	}
}
