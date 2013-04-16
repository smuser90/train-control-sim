package TrackController;

import TrackModel.Block;

import java.util.ArrayList;
import java.util.Map;

public class PLC {

	private static ArrayList<Block> myBlocks = new ArrayList<Block>();//TrackControllerModule.myBlocks;
	private static ArrayList<Integer> crossingList = null;
	private static ArrayList<Integer> brokenRailList;
	private static TrackController _tc;
	private static Map trainList;
	private static TrackControllerModule _tcm;
	
	static int index = 0;
	static int index_2 = 0;
	
	public static void runPLC(TrackController trackController, ArrayList<Block> bL) {
		myBlocks = bL;
		if(trackController.crossingsControlled == null)
			
		//trainList =_tcm.getTrainList();
		//System.out.println("here in PLC, the trainList size is " + trainList.size());
		detectTrains(trackController);
		crossings(trackController);
		detectBrokenRail(trackController);
		//System.out.println("Here in runPLC in the PLC class with trackcontroller " + trackController.getID());
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
			if(trackController.blocksControlled.contains(myBlocks.get(index).getBlockNumber()) && myBlocks.get(index).getOccupied())
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
	
	public static void crossings(TrackController trackController){
		for(index = 0; index < trackController.crossingsControlled.size(); index++){
			//CYCLE THROUGH TRAIN LIST AND IF ONE OF THE TRAINS NEXT BLOCK IS THIS CROSSING THEN PUT THE CROSSING DOWN
			/*for(int i = 0; i < trainList.size(); i++){
			  	if(trainList.get(i).m_routeInfo.contains(trackControlller.crossingsControlled.get(index).getBlockNumber()){
			  		if((trainList.get(i).m_routeInfo.get(trainList.get(i).m_blockIndex() + 1) == trackController.crossingsControlled.get(index).getBlockNumber() || trainList.get(i).m_routeInfo.get(trainList.get(i).m_blockIndex()) == trackController.crossingsControlled.get(index).getBlockNumber()){
			  			//THIS MEANS THAT THE TRAIN HAS THIS CROSSING ON ITS ROUTE INFO AND THAT ITS INDEX + 1 IS THE CROSSING
			  			//OR THAT THE TRAIN IS CURRENTLY ON THE BLOCK WITH THE CROSSING...ARMS NEED TO GO DOWN ASAP!
			  			trackController.crossingsControlled.get(index).setCrossing(false);
			  		}
			  	}
			  }*/
		}
//		for(index = 0; index <crossingList.size(); index++)
//		{
//			if(myBlocks.get(crossingList.get(index) - 1).getOccupied() || myBlocks.get(crossingList.get(index) + 1).getOccupied())
//			{
//				myBlocks.get(crossingList.get(index)).setCrossing(true);
//			}
//			
//			else
//				myBlocks.get(crossingList.get(index)).setCrossing(false);
//		}
	}
	
	public static void switches(){
		
	}
	
	public static void detectBrokenRail(TrackController trackController){
		for(index = 0; index < trackController.blocksControlled.size(); index++)
		{
			if(myBlocks.get(trackController.blocksControlled.get(index).getBlockNumber()).getFailure())
			{
				if(!trackController.brokenRails.contains(trackController.blocksControlled.get(index).getBlockNumber())) {
					trackController.brokenRails.add(trackController.blocksControlled.get(index).getBlockNumber());
					trackController.isChanged = true;
					reportBrokenRail(trackController.blocksControlled.get(index).getBlockNumber());
					//brokenRailList.add(myBlocks.get(trackController.blocksControlled.get(index).getBlockNumber());
					//if not the yard, and train within one block of the broken rail it needs to hit EBrake
					/*if(index > 0)
					{
						//for(int i = 0; i < trainList.size(); i++){
							//if(trainList.get(i).m_routeInfo.contains(trackControlller.blocksControlled.get(index).getBlockNumber()){
								//CALL REROUTE BECAUSE THAT TRAIN PATH CONTAINS THE BROKEN RAIL
								//
						//}
						//}
					}*/
				}
			}
			else{
				if(trackController.brokenRails.contains(trackController.blocksControlled.get(index).getBlockNumber())){
					trackController.brokenRails.remove(trackController.blocksControlled.get(index).getBlockNumber());
					trackController.isChanged = true;
				}
			}
			
		}
		
		handleBrokenRail(trackController);
		//trackController.setBrokenRail(brokenRail);
		//_tc.setBrokenRail();
		
//		for(index = 0; index < myBlocks.size(); index++){
//			if(myBlocks.get(index).failure){
//				reportBrokenRail(index);
//			}
//		}
	}
	
	public static void handleBrokenRail(TrackController trackController) {
		for(index = 0; index < trackController.brokenRails.size(); index++){
			/*for(int i = 0; i < trainList.size(); i++){
				if(trainList.get(i).m_routeInfo.contains(trackController.brokenRails.get(index))){
					if(trainList.get(i).m_routeInfo.get(trainList.get(i).m_blockIndex() + 1) == trackController.brokenRails.get(index) && !trainList.get(i).getEmergencyBrake())
						trainList.get(i).toggleEmergencyBrake();
					else
						REROUTE
				}
			 }*/
		}
	}

	public static void reportBrokenRail(int blockNumber){
		TrackController.reportBrokenRail(blockNumber);
	}
	
	
	
}
