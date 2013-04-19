package TrackController;

import TrackModel.Block;
import TrackModel.Line;
import TrainModel.TrainModel;
/*************************************
 * For block.type
 * 0 regular
 * 1 switch
 * 2 crossing
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackControllerModule {
	
	private TrackControllerPanel currentPanel;
	private ArrayList<ArrayList<TrackController>> trackControllerList;
	private ArrayList<String> lineNames;
	private ArrayList<Line> lines;
	private ArrayList<Block> myBlocks;
	private ArrayList<Integer> switchList;
	private Map<Integer, TrainModel> trainList;
	private ArrayList<Integer> crossingList;
	private boolean hasTrack = false;
	/**
	 * Create the frame.
	 */
	public TrackControllerModule() 
	{
		myBlocks = new ArrayList<Block>();
		switchList = new ArrayList<Integer>();
		crossingList = new ArrayList<Integer>();
		trackControllerList = new ArrayList<ArrayList<TrackController>>();

		lineNames = new ArrayList<String>();
		lineNames.add("Lines");
		currentPanel = new TrackControllerPanel(this);
		lines = new ArrayList<Line>();
	}
	/************************************************************************************************
	 CALL THIS TO WAKE ME UP************************************************************************/
	public void getTrack(Line track){
		myBlocks = track.getBlocks();
		trackControllerList.add(TCListMaker.makeTCList(track));
		/*for(int i = 0; i < this.trackControllerList.size(); i++) {
			System.out.print("TC # " + i + ":" + this.trackControllerList.get(i).getNumBlocks() + ":[");
			for(Block b : this.trackControllerList.get(i).blocksControlled) {
				System.out.print(b.getBlockNumber() + ", ");
			}
			System.out.println("]");
		}*/
		lineNames.add(track.getName());
		lines.add(track);
		hasTrack = true;
		currentPanel.displayChange();
		PLC.setup(this);
		runPLC();

	}

	public TrackControllerPanel getPanel() {
		return this.currentPanel;
	}
	
	public void receiveTick() {
		if(hasTrack) {
			runPLC();
		}
	}
	
	public void receiveTrains(Map<Integer, TrainModel> newTrainList){
		trainList = newTrainList;
		currentPanel.displayChange();
	}
	
	/**************************************************************************************
	 * CALL ME ONCE PER TICK**************************************************************/
	public void runPLC(){
		TrackController.runPLC(trackControllerList, lines);
		/*for(int i = 0; i < trackControllerList.size(); i++) {
			for(int j = 0; j < trackControllerList.get(i).size(); j++) {
				for(int k = 0; k < trackControllerList.get(i).get(j).brokenRails.size(); k++) {
					System.out.println(i+":"+j+":"+trackControllerList.get(i).get(j).brokenRails.get(k));
				}
			}
		}*/
		if(currentPanel.getCL() >= 0 && trackControllerList.get(currentPanel.getCL()).get(currentPanel.getCC()).isChanged) {
			currentPanel.displayChange();
		}
	}

	protected ArrayList<Block> getBlockList(String line) {
		for(Line l : lines) {
			if(l.getName().equals(line))
				return l.getBlocks();
		}
		return null;
	}
	
	protected Map getTrainList() {
		return trainList;
	}
	
	protected ArrayList<Integer> getSwitchList() {
		return switchList;
	}
	
	protected ArrayList<TrackController> getTrCList(int tcIndex) {
		return trackControllerList.get(tcIndex);
	}
	protected ArrayList<Integer> getCrossingList() {
		return crossingList;
	}
	
	protected ArrayList<String> getLineNames() {
		return this.lineNames;
	}
}
