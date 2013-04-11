package CTC;

import java.util.ArrayList;

import Log.Log;
import Simulator.Simulator;
import TrackModel.Block;

public class CTCModule {
	// Fields
	private CTCPanel gui;
	public Simulator sim;
	private ArrayList<Block> greenLine = null;
	private ArrayList<Integer> gTrainIDs = null;
	private ArrayList<String> lines = null;
	private Log log = Log.Instance();
	
	public CTCModule(Simulator s) {
		gui = new CTCPanel(this);
		sim = s;
		lines = new ArrayList<String>();
		lines.add("Lines");
		gTrainIDs = new ArrayList<Integer>();
	}
		
	public CTCPanel getPanel() {
		return this.gui;
	}
	
	protected void scheduleTrain(int line) {
		if(sim != null) {
			if(line == 0) {
				sim.scheduleTrain(line);
				log.append(0, "Train added to Green Line\n");
			} else {
				sim.scheduleTrain(line);
				log.append(0, "Train added to Red Line\n");
			}
		}
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
	
	public void setGLTrains(ArrayList<Integer> tids) {
		gTrainIDs = tids;
		gui.update();
	}
	
	public ArrayList<Integer> getGLTrains() {
		return gTrainIDs;
	}
}
