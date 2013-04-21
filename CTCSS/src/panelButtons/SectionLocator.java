package panelButtons;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SectionLocator {
	private ArrayList<Point2D.Double> points;
	private int numSections;
	private int xSize;
	private int ySize;
	private int maxRows = 4;
	//private int maxCols = 9;
	private int numRows;
	private int numCols;
	
	public SectionLocator(int ns, int x, int y) {
		numSections = ns;
		xSize = x;
		ySize = y;
		points = new ArrayList<Point2D.Double>();
		if(ns <= maxRows) {
			numRows = ns;
		} else {
			numRows = maxRows;
		}
		numCols = ns/numRows + 1;
	}
	
	public ArrayList<Point2D.Double> getPoints() {
		Point2D.Double point = new Point2D.Double();
		point.x = 10;
		point.y = 75;
		points.add(point);
		int yOffset = 10;
		
		int xStart = 100;
		int xOffset = (xSize - xStart)/numCols;
		for(int i = 0; i < numRows; i++) {
			xStart = 100;
			for(int j = 0; j < numCols; j++) {
				point = new Point2D.Double();
				if(i%2 == 0) {
					point.x = xStart + j*xOffset;
				} else {
					point.x = xSize - xOffset/2 - j*xOffset;
				}
				point.y = yOffset;
				points.add(point);
			}
			yOffset += ySize / numRows;
		}
		
		return points;
	}
}
