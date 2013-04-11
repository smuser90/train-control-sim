package TrackController;

import java.util.ArrayList;

public class PLC {

	private static ArrayList<Block> myBlocks = new ArrayList<Block>();//TrackControllerModule.myBlocks;
	private static ArrayList<Integer> crossingList;
	
	static int index = 0;
	static int index_2 = 0;
	
	public static void runPLC(TrackController trackController) {
		detectTrains(trackController);
		
	}
	
	public static void setup(ArrayList<Block> blocks, ArrayList<Integer> cList) {
		myBlocks = blocks;
		crossingList = cList;
	}
	
	//Method cycles through all the track controllers and sets the number of trains on each block to zero
	//It then cycles through all the blocks and checks if they are owned by that controller and are occupied
	//If so, the number of trains is increased for that controller
	public static void detectTrains(TrackController trackController){
		int temp = 0;
		for(index = 0; index < myBlocks.size(); index++)
		{
			if(trackController.blocksControlled.contains(myBlocks.get(index).blockNumber) && myBlocks.get(index).occupied)
			{
				temp++;
				//TrackControllerPanel.test3();
			}
		}
		trackController.setNumTrains(temp);
		//TrackControllerPanel.updateTrainDisplay(trackController);
//		for(index = 0; index < trackControllerList.size();index++){
//			trackControllerList.get(index).trains = 0;
//			for(index_2 = 0; index_2 < myBlocks.size(); index_2++)
//				if(trackControllerList.get(index).blocksControlled.contains((index_2)) && myBlocks.get(index_2).occupied)
//					trackControllerList.get(index).trains++;
//					//TrackControllerGui.test2();	
//		}
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
	
	public static void detectBrokenRail(TrackController trackController){
		for(index = 0; index < trackController.blocksControlled.size(); index++)
		{
			if(myBlocks.get(trackController.blocksControlled.get(index)).failure)
			{
				//if not the yard, and train within one block of the broken rail it needs to hit EBrake
				if(index > 0)
				{
					
				}
			}
		}
		
//		for(index = 0; index < myBlocks.size(); index++){
//			if(myBlocks.get(index).failure){
//				reportBrokenRail(index);
//			}
//		}
	}
	
	public static void reportBrokenRail(int blockNumber){
		TrackController.reportBrokenRail(blockNumber);
	}
	
}
