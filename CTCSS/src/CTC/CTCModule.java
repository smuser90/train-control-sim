package CTC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Log.Log;
import Simulator.Simulator;
import TrackModel.Block;
import TrackModel.Line;
import TrainModel.TrainModel;

public class CTCModule {
	//// Fields
	private CTCPanel gui;
	private Simulator sim = null;
	private ArrayList<String> lineNames = null;
	private ArrayList<Line> lines = null;
	private Map<Integer, TrainModel> trains = null;
	private Log log = Log.Instance();
	
	public CTCModule() {
		gui = new CTCPanel(this);
		lineNames = new ArrayList<String>();
		lineNames.add("Lines");
		lines = new ArrayList<Line>();
		trains = new HashMap<Integer, TrainModel>();
	}
		
	public CTCPanel getPanel() {
		return this.gui;
	}
	
	public void setSimulator(Simulator s) {
		sim = s;
	}
	
	protected ArrayList<String> getLines() {
		return lineNames;
	}
	
	public void addLine(Line line) {
		lineNames.add(line.getName());
		lines.add(line);
		gui.update();
	}
	
	protected ArrayList<Block> getLine(String lName) {
		for(Line l : lines) {
			if(l.getName().equals(lName))
				return l.getBlocks();
		}
		return null;
	}
	
	/* Block Methods **************************************************************/
	
	protected void closeBLock(String lName, int bNum) {
		sim.closeBLock(bNum, lName);
		log.append(2, "CLosing BLock: " + bNum + " on Line: " + lName + "\n");
	}
	
	protected void openBLock(String lName, int bNum) {
		sim.openBLock(bNum, lName);
		log.append(2, "Opening BLock: " + bNum + " on Line: " + lName + "\n");
	}
	
	protected void setSpeedLimit(String lName, int bNum, int lim) {
		sim.setSpeedLimit(bNum, lName, lim);
		log.append(2, "Setting Speed Limit to " + lim + "KPH at BLock: " + bNum + " on Line: " + lName + "\n");
	}
	
	public void addBLock(String lName, int prevBlock) {
		sim.addBlock(lName, prevBlock);
		
	}
	
	public void removeBLock(String lName, int blockNum) {
		sim.removeBlock(lName, blockNum);
		
	}
	
	/* Train Methods **************************************************************/
	
	protected void scheduleTrain(String line) {
		if(sim != null) {
			sim.scheduleTrain(line);
			log.append(2, "Train added to " + line + "\n");
		}
	}
	
	protected void routeTrain(int trainID, String station, String lName) {
		for(Line l : lines) {
			if(l.getName().equals(lName)) {
				for(Block b : l.getBlocks()) {
					if(b.getStationName().equals(station)) {
						sim.routTrain(trains.get(trainID), b.getBlockNumber(), l);
						log.append(2, "Routeing Train:" + trainID + " to Station:" + station +"\n");
						break;
					}
				}
				break;
			}
		}
		
	}
	
	protected void setAuthority(int trainID, int a) {
		sim.setAuthority(trainID, a);
		log.append(2, "Authority for Train: " + trainID + " set to " + a + " blocks\n");
	}
	
	public ArrayList<Integer> getLineTrains(String line) {
		ArrayList<Integer> tids = new ArrayList<Integer>();
		for(int i = 0; i < trains.size(); i++) {
			if(trains.get(i+1).getLine().equals(line)) {
				tids.add(i+1);
			}
		}
		return tids;
	}
	
	public void setTrains(Map<Integer, TrainModel> t) {
		trains = t;
		gui.update();
	}

	

	
}
