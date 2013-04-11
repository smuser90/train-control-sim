package CTC;

import java.util.ArrayList;

import Simulator.Simulator;
import TrackModel.Block;

public class CTCModule {
	// Fields
	private CTCPanel gui;
	private Simulator sim;
	private ArrayList<Block> greenLine = null;
	private ArrayList<String> lines = null;
	
	public CTCModule(Simulator s) {
		gui = new CTCPanel(this);
		sim = s;
		lines = new ArrayList<String>();
		lines.add("Lines");
	}
		
	public CTCPanel getPanel() {
		return this.gui;
	}
	
	protected void scheduleTrain(int line) {
		sim.scheduleTrain(line);
	}
	
	public void setGLine(ArrayList<Block> gLine) {
		greenLine = gLine;
		if(!lines.contains("Green")) {
			lines.add("Green");
		}
		gui.update();
	}
	
	protected ArrayList<String> getLines() {
		return lines;
	}
	
	protected ArrayList<Block> getGreenLine() {
		return greenLine;
	}
}
