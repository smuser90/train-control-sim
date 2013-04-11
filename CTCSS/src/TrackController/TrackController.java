package TrackController;

import java.util.ArrayList;

public class TrackController {
	private int blocks;
	private int number;
	private int track;
	private int trains;
	protected ArrayList<Integer> blocksControlled = new ArrayList<Integer>();

	static int index;
	public TrackController(int num, int block, int trk, int train){
		this.blocks = block;
		this.number = num;
		this.track = trk;		
		this.trains = train;
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
		return blocks;
	}
	
	public int getTrackNum() {
		return track;
	}
	
	public int getID() {
		return number;
	}
}