package TrackController;

import java.util.ArrayList;

public class TrackController {
	int blocks;
	int number;
	int track;
	int trains;
	ArrayList<Integer> blocksControlled = new ArrayList<Integer>();

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
}