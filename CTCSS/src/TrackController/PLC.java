package TrackController;

import java.util.ArrayList;

public class PLC {

	static ArrayList<Block> myBlocks = TrackControllerModule.myBlocks;
	static ArrayList<TrackController> trackControllerList = TrackControllerModule.trackControllerList;
	static ArrayList<Integer> crossingList = TrackControllerModule.crossingList;
	
	static int index = 0;
	static int index_2 = 0;
	
	//Method cycles through all the track controllers and sets the number of trains on each block to zero
	//It then cycles through all the blocks and checks if they are owned by that controller and are occupied
	//If so, the number of trains is increased for that controller
	public static void detectTrains(){
		for(index = 0; index < trackControllerList.size();index++){
			trackControllerList.get(index).trains = 0;
			for(index_2 = 0; index_2 < myBlocks.size(); index_2++)
				if(trackControllerList.get(index).blocksControlled.contains((index_2)) && myBlocks.get(index_2).occupied)
					trackControllerList.get(index).trains++;
					//TrackControllerGui.test2();	
		}
	}
	
	//Cycle through each crossing, if there is a train on the block to either side of it, crossing closes
	public static void crossings(){
		for(index = 0; index <crossingList.size(); index++)
		{
			if(myBlocks.get(crossingList.get(index) - 1).occupied || myBlocks.get(crossingList.get(index) + 1).occupied)
			{
				myBlocks.get(crossingList.get(index)).crossingActive = false;
			}
			
			else
				myBlocks.get(crossingList.get(index)).crossingActive = true;
		}
	}
	
	public static void switches(){
		
	}
	
	public static void detectBrokenRail(){
		for(index = 0; index < myBlocks.size(); index++){
			if(myBlocks.get(index).failure){
				reportBrokenRail(index);
			}
		}
	}
	
	public static void reportBrokenRail(int blockNumber){
		TrackController.reportBrokenRail(blockNumber);
	}
}
