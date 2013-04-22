/*
 * GraphPanel.java
 * Custom JPanel for displaying a graph of PanelButtons
 * Author: Nikolas Parshook
 * Date Created: 04/19/2013
 * Date Last Updated: 04/21/2013
 */

package TrackDisplay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Custom JPanel for displaying a graph of PanelButtons
 * 
 * @author Nikolas Parshook
 * 
 */
public class GraphPanel extends JPanel 
{
	// Fields
	ArrayList<PanelButton> pbs;
	ArrayList<ArrayList<Integer>> connections;
	Graphics2D g2;
	Color gColor = Color.BLACK;

	/**
	 * Create the panel.
	 */
	public GraphPanel() 
	{
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		setBounds(0, 0, 600, 190);
		pbs = new ArrayList<PanelButton>();
		connections = new ArrayList<ArrayList<Integer>>();

	}
	
	/**
	 * Add a panelButton to this panel
	 * @param pb the PanelButton to add
	 */
	public void addButton(PanelButton pb) 
	{
		pbs.add(pb);
		connections.add(new ArrayList<Integer>());
		pb.setParent(this);
		add(pb);
	}

	/**
	 * Creates a connection between button and bConnected
	 * @param button 
	 * @param bConnected a panelButton that button goes to
	 */
	public void addConnection(int button, int bConnected) 
	{
		pbs.get(button).addGoingTo(bConnected);
		pbs.get(bConnected).addCameFrom(button);
		connections.get(button).add(bConnected);
	}

	/* Custom painting of the GraphPanel */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		for (int j = 0; j < pbs.size(); j++) 
		{
			Component c1 = pbs.get(j);
			for (int k = 0; k < connections.get(j).size(); k++) 
			{
				drawL(g2, c1, pbs.get(connections.get(j).get(k)));
			}
		}

		for (int j = 0; j < pbs.size(); j++) 
		{
			if (pbs.get(j).isHighLighted()) 
			{
				PanelButton c1 = pbs.get(j);
				g2.setColor(Color.ORANGE);
				for (int k = 0; k < c1.goingTo().size(); k++) 
				{
					if (c1.cameFrom().contains(c1.goingTo().get(k))) 
					{
						g2.setColor(Color.GREEN);
					}
					drawL(g2, c1, pbs.get(c1.goingTo().get(k)));
					g2.setColor(Color.ORANGE);
				}
				g2.setColor(Color.BLUE);
				for (int k = 0; k < c1.cameFrom().size(); k++) 
				{
					if (c1.goingTo().contains(c1.cameFrom().get(k))) {
						g2.setColor(Color.GREEN);
					}
					drawL(g2, pbs.get(c1.cameFrom().get(k)), c1);
					g2.setColor(Color.BLUE);
				}
				break;
			}

		}
		g2.setStroke(new BasicStroke(1));
	}

	/* Draws an arrow from c1 to c2 */
	private void drawL(Graphics2D g2, Component c1, Component c2) 
	{
		Point2D.Double p1 = getCenterEdge(c1, c2);// getCenter(c1); //
		Point2D.Double p2 = getCenterEdge(c2, c1);// getCenter(c2); //
		g2.draw(new Line2D.Double(p1, p2));
		drawAH(g2, p1, p2);

	}

	/* Draws the Arrow Head for the arrow from p1 to p2 */
	private void drawAH(Graphics2D g2, Point2D p1, Point2D p2) 
	{
		int r = 4; // thickness of vertex
		int s = 6; // thickness of line
		double ax = p1.getX() + 0.5;
		double ay = p1.getY() + 0.5;
		double length = Math.sqrt((p2.getX() - p1.getX())
				* (p2.getX() - p1.getX()) + (p2.getY() - p1.getY())
				* (p2.getY() - p1.getY()));
		if (length == 0)
			return;
		double sin = (p2.getY() - p1.getY()) / length;
		double cos = (p2.getX() - p1.getX()) / length;
		double px = length - (r + 1);
		double py = 0;
		double ly = s;
		double lx = length - (r + .75 * s);
		double rx = lx;
		double ry = -ly;
		double mx = length;// -(r+s+s/2);
		double my = 0;
		double tx;
		double ty;
		tx = px * cos - py * sin;
		ty = px * sin + py * cos;
		px = tx;
		py = ty;
		tx = lx * cos - ly * sin;
		ty = lx * sin + ly * cos;
		lx = tx;
		ly = ty;
		tx = rx * cos - ry * sin;
		ty = rx * sin + ry * cos;
		rx = tx;
		ry = ty;
		tx = mx * cos - my * sin;
		ty = mx * sin + my * cos;
		mx = tx;
		my = ty;
		px += ax;
		py += ay;
		mx += ax;
		my += ay;
		lx += ax;
		ly += ay;
		rx += ax;
		ry += ay;
		int[] xvals = new int[4];
		int[] yvals = new int[4];
		xvals[0] = (int) px;
		xvals[1] = (int) lx;
		xvals[2] = (int) mx;
		xvals[3] = (int) rx;
		yvals[0] = (int) py;
		yvals[1] = (int) ly;
		yvals[2] = (int) my;
		yvals[3] = (int) ry;
		g2.fillPolygon(xvals, yvals, 4);
	}

	/* Returns the point that will the arrow from c1 to c2 will point to */
	private Point2D.Double getCenterEdge(Component c1, Component c2) 
	{
		Point2D.Double p = new Point2D.Double();
		Rectangle r = c1.getBounds();
		Rectangle r2 = c2.getBounds();
		double x1 = r.getX();
		double x2 = x1 + r.getWidth();
		double cx1 = r.getCenterX();
		double cy1 = r.getCenterY();
		double y1 = r.getY();
		double y2 = y1 + r.getHeight();
		double x3 = r2.getX();
		double x4 = x3 + r2.getWidth();
		double y3 = r2.getY();
		double y4 = y3 + r2.getHeight();
		double cx2 = r.getCenterX();
		double cy2 = r.getCenterY();
		if (y2 < y3) 
		{
			if (x3 < x2 && x4 > x1) 
			{
				p.x = cx1;
				p.y = y2;
			} 
			else if (x3 < x1) 
			{
				p.x = x1;
				p.y = y2;
			}
			else 
			{
				p.x = x2;
				p.y = y2;
			}
		} 
		else if (y1 > y4) 
		{
			if (x3 < x2 && x4 > x1) 
			{
				p.x = cx1;
				p.y = y1;
			} 
			else if (x4 < x1) 
			{
				p.x = x1;
				p.y = y1;
			} 
			else 
			{
				p.x = x2;
				p.y = y1;
			}
		} 
		else if (y3 < y2 && y4 > y1) 
		{
			if (x3 > x2) 
			{
				p.x = x2;
				p.y = cy1;
			} 
			else if (x4 < x1)
			{
				p.x = x1;
				p.y = cy1;
			}
		}
		return p;
	}

	/**
	 * toggle all of the buttons on the panel to off
	 */
	public void turnOffAll() {
		for (int i = 0; i < pbs.size(); i++) {
			pbs.get(i).turnOff();
		}
	}
	
	public void setButtonPos(int bNum, int x, int y, int width, int height) 
	{
		pbs.get(bNum).setBounds(x, y, width, height);
	}
}
