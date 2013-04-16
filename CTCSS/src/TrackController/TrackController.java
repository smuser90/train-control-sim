package TrackController;

import java.util.ArrayList;

import TrackModel.Block;

public class TrackController {
	private int blocks;
	private int number;
	private int track;
	private int trains;
	protected ArrayList<Block> blocksControlled = new ArrayList<Block>();

	static int index;
	public TrackController(int tcIndex, ArrayList<Block> controlled){
		this.blocksControlled = controlled;
		this.number = tcIndex;
	}

	public static void reportBrokenRail(int blockNumber) {
		//reportBrokenRailToCTC(blockNumber);
	}
	
	public static void runPLC(ArrayList<TrackController> trackControllerList){
		for(index = 0; index < trackControllerList.size(); index++)
		{
			PLC.runPLC(trackControllerList.get(index));
		}
	}
	
	public void setNumTrains(int numTrains) {
		trains = numTrains;
	}
	
	public int getNumTrains() {
		return trains;
	}
	
	public void setNumBlocks(int numBlocks) {
		blocks = numBlocks;
	}
	
	public int getNumBlocks() {
		return this.blocksControlled.size();
	}
	
	public int getTrackNum() {
		return 0;
	}
	
	public int getID() {
		return number;
	}
}