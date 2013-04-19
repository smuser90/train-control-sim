package TrackController;

import TrackModel.Block;
import TrainModel.TrainModel;

import java.util.ArrayList;
import java.util.Map;

public class PLC {

	private static ArrayList<Block> myBlocks = new ArrayList<Block>();//TrackControllerModule.myBlocks;
	private static ArrayList<Integer> crossingList = null;
	private static ArrayList<Integer> brokenRailList;
	private static TrackController _tc;
	//private static Map trainList;
	private static Map<Integer, TrainModel> trainList;
	private static TrackControllerModule _tcm;
	
	static int index = 0;
	static int index_2 = 0;
	
	public static void runPLC(TrackController trackController, ArrayList<Block> bL) {
		myBlocks = bL;
		//if(trackController.crossingsControlled == null)
			
		trainList =_tcm.getTrainList();
		//System.out.println("here in PLC, the trainList size is " + trainList.size());
		detectTrains(trackController);
		crossings(trackController);
		detectBrokenRail(trackController);
		switches(trackController);
		//System.out.println("Here in runPLC in the PLC class with trackcontroller " + trackController.getID());
	}
	
	public static void setup(TrackControllerModule tcm) {
		_tcm = tcm;
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
		//for(index = 0; index < trackController.crossingsControlled.size(); index++){
			//CYCLE THROUGH TRAIN LIST AND IF ONE OF THE TRAINS NEXT BLOCK IS THIS CROSSING THEN PUT THE CROSSING DOWN
		//System.out.println("here before train list is null");
		if(trainList != null)
		{
			//System.out.println("here we are in crossings if");
			for(int i = 0; i < trainList.size(); i++){
				//System.out.println("here we are in crossings for");
				if(trainList.get(i + 1).getLine().equals(trackController.getLine()))//on the same line
				{
					//System.out.println("here we are in crossings, if to check line");
					if(trackController.crossingsControlled.contains(trainList.get(i + 1).getBlock()))//trainList.get(i).getBlockIndex()))
					{
						System.out.println("here we are in crossings and the block is contained");
						trackController.crossingsControlled.get(i).setCrossing(false);//trainList.get(i).getBlock().getBlockNumber()).setCrossing(false);
					}
					else
					{
						System.out.println("here we are in crossings and the block is not contained");
						trackController.crossingsControlled.get(i).setCrossing(true);//trainList.get(i).getBlock().getBlockNumber()).setCrossing(true);
					}
				}
			}
		}
	}
//			  	if(trainList.get(i).m_routeInfo.contains(trackController.crossingsControlled.get(index).getBlockNumber() && trainList.get(i).m_line.equals(trackController.getLine()){
//			  		if((trainList.get(i).m_routeInfo.get(trainList.get(i).m_blockIndex() + 1) == trackController.crossingsControlled.get(index).getBlockNumber() || trainList.get(i).m_routeInfo.get(trainList.get(i).m_blockIndex()) == trackController.crossingsControlled.get(index).getBlockNumber()){
//			  			//THIS MEANS THAT THE TRAIN HAS THIS CROSSING ON ITS ROUTE INFO AND THAT ITS INDEX + 1 IS THE CROSSING
//			  			//OR THAT THE TRAIN IS CURRENTLY ON THE BLOCK WITH THE CROSSING...ARMS NEED TO GO DOWN ASAP!
//			  			trackController.crossingsControlled.get(index).setCrossing(false);
//			  		}
//			  	}
//			  	else
//			  		trackController.crossingsControlled.get(index).setCrossing(true);
//			  }
		//}
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
	//}
	
	
	
	public static void switches(TrackController trackController){
	/*	for(int i = 0; i < trainList.size(); i++)
		{
			//trains are on this list
			if(trainList.get(i).getLine().equals(trackController.getLine()))
			{
				//train's has at least two more blocks in route
				if(trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex() + 1) != null && trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex() + 2) != null
				{
					if(trackController.switchesControlled.contains((trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex() + 1))))
					{
						//for each adjacent , check if it is contained. if so, set switch to trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex() + 2)
						//else, switch to trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex())
					}
				}
			}
		}
		
		*/
	
	
		//use adjacentcy list 
		//roll through all trains for this track controller
		//if train one block away from switch, we can do this
		//if current block is on adjacency list of switch number, use next next
		//if current block is not on adjacency list of switch number, we use cur
		//EXAMPLE
		/*
		 * 0: 1
		 * 1: 0 2
		 * 8: 1
		 * 
		 * 0--------->1----------->2
		 * cur		  next         nextnext
		 * 
		 * since 2 is adjacent to 1, switch to 2, block.setSwitched(2);
		 * 
		 * 
		 * 8--------->1----------->0
		 * cur		next		  nextnext
		 * 
		 * since 8 is not in the adjacency list of 1, switch to 8
		 * 
		 * use Line.adj(switch) to get all of the adjacent blocks to this switch
		 * 
		 * 
		 */
		//if not, switch to current
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
					trackController.brokenRails.remove(trackController.brokenRails.indexOf(trackController.blocksControlled.get(index).getBlockNumber()));
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
