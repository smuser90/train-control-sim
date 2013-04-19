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
	private static Map<Integer, TrainModel> trainList;
	private static TrackControllerModule _tcm;
	
	static int index = 0;
	static int index_2 = 0;
	
	public static void runPLC(TrackController trackController, ArrayList<Block> bL) {
		myBlocks = bL;
		trainList =_tcm.getTrainList();
		detectTrains(trackController);
		crossings(trackController);
		detectBrokenRail(trackController);
		switches(trackController);
		handleAuthority(trackController);
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
			}
		}
		trackController.setNumTrains(temp);
	}
	
	public static void crossings(TrackController trackController){
		if(trainList != null)
		{
			for(int i = 0; i < trainList.size(); i++)
			{
				if(trainList.get(i + 1).getLine().equals(trackController.getLine()) && trainList.get(i + 1).getRouteInfo() != null)
				{
					if(trainList.get(i + 1).getRouteInfo().size() > trainList.get(i + 1).getBlockIndex() + 1)
					{
						if(trackController.crossingsControlled.contains(trainList.get(i + 1).getBlock()) || trackController.crossingsControlled.contains(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1)))//trainList.get(i).getBlockIndex()))
						{
							trackController.crossingsControlled.get(i).setCrossing(false);
						}
						else
						{
							trackController.crossingsControlled.get(i).setCrossing(true);
						}
					}
					else
					{
						trackController.crossingsControlled.get(i).setCrossing(true);
					}
				}
			}
		}
	}
	
	public static void switches(TrackController trackController){
		/*System.out.println("here in switches");
		if(trainList != null)
		{
			System.out.println("train list isn't null!!!");
			for(int i = 0; i < trainList.size(); i++)
			{
				System.out.println("Were in the for loop to go through the trains");
				if(trainList.get(i + 1).getLine().equals(trackController.getLine()) && trainList.get(i + 1).getRouteInfo() != null)
				{
					//trains are on this list
					System.out.println("route info isn't null!");
					if(trainList.get(i + 1).getLine().equals(trackController.getLine()))
					{
						System.out.println("were on the same line as a track controller");
						//train's has at least two more blocks in route
						if(trainList.get(i + 1).getRouteInfo().size() > trainList.get(i + 1).getBlockIndex() + 2)
						{
							if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1) != null && trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2) != null)
							{
								System.out.println("The next two blocks in the route info aren't null!");
								if(trackController.switchesControlled.contains((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1))))
								{
									System.out.println("We have made it into the switch depth where we start to check adjacency list");
									//for(int j = 0; j < sim.getLine(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).size(); j++)
									for(int j = 0; j < sim.getLine(trainList.get(i + 1).getLine()).adj(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1); j++)
									{
										//if(sim.getLine(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).get(j) == trainList.get(i + 1).getBlock().getBlockNumber())
										if(sim.getLine(trainList.get(i + 1).getLine()).adj(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).get(j) == trainList.get(i + 1).getBlock().getBlockNumber())
										{
											//switch to next next
											trackController.switchesControlled.get((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1))).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2).getBlockNumber());
										}
										else
										{
											//switch to current
											trackController.switchesControlled.get((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1))).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex()).getBlockNumber());
										}
									}
									//sim.getLine will be used to get the current line that the train is on. using this list we just need to check if the cur value is on
									//the adj list for the switch. If so, switch to next next value, if not switch to cur
									
									//for each adjacent , check if it is contained. if so, set switch to trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex() + 2)
									//else, switch to trainList.get(i).getRouteInfo.get(trainList.get(i).getBlockIndex())
								}
							}
						}
					}
				}
			}
		}*/
		
		
	
	
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
	}
	
	public static void handleBrokenRail(TrackController trackController) {
		for(index = 0; index < trackController.brokenRails.size(); index++){
			if(trainList != null)
			{
				for(int i = 0; i < trainList.size(); i++)
				{
					if(trainList.get(i + 1).getRouteInfo() != null)
					{
						for(int j = 0; j < trainList.get(i + 1).getRouteInfo().size(); j++)
						{
							if(trackController.brokenRails.contains(trainList.get(i + 1).getRouteInfo().get(j).getBlockNumber()))
							{
								if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber() == trackController.brokenRails.get(index) && !trainList.get(i + 1).getEmergencyBrake())
								{
									trainList.get(i + 1).toggleEmergencyBrake();
								}
								else
								{
									//Reroute
								}
							}
						}
					}	
				}
			}
		}
	}

	public static void reportBrokenRail(int blockNumber){
		TrackController.reportBrokenRail(blockNumber);
	}
	
	public static void handleAuthority(TrackController trackController){
		//if train list is not null
		if(trainList != null)
		{
			//for all trains
			for(int i = 0; i < trainList.size(); i++)
			{
				//if train is on same line as track controller and route is not null
				if(trainList.get(i + 1).getLine().equals(trackController.getLine()) && trainList.get(i + 1).getRouteInfo() != null)// && trackController.blocksControlled.contains(trainList.get(i + 1).getBlock().getBlockNumber()))
				{
					//if the track controller controls said block
					if(trackController.blocksControlled.contains(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex())))
					{
						trainList.get(i + 1).setAuthority(0);
						if(!trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getOccupied() && !trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getFailure())
						{
							trainList.get(i + 1).setAuthority(1);
						}
					}
				}
			}
		}
	}
	
	
	
}
