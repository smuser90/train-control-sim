package TrackController;

import Simulator.Simulator;
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
	private static Simulator _sim;
	private static TrackControllerPanel _tcp;
	
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
	
	public static void setup(TrackControllerModule tcm, Simulator sim, TrackControllerPanel currentPanel) {
		_tcm = tcm;
		_sim = sim;
		_tcp = currentPanel;
	}
	
	//Method cycles through all the track controllers and sets the number of trains on each block to zero
	//It then cycles through all the blocks and checks if they are owned by that controller and are occupied
	//If so, the number of trains is increased for that controller
	public static void detectTrains(TrackController trackController){
		int temp = 0;
		boolean same = true;
		ArrayList<Block> tempTrain = new ArrayList<Block>();
		for(index = 0; index < myBlocks.size(); index++)
		{
			if(trackController.blocksControlled.contains(myBlocks.get(index)) && myBlocks.get(index).getOccupied())
			{
				temp++;
				tempTrain.add(myBlocks.get(index));
			}
		}
		trackController.setNumTrains(temp);
		
		
		for(int i = 0; i < tempTrain.size(); i++)
		{
			if(tempTrain.size() == trackController.getTrainsControlled().size())
			{
				if(tempTrain.get(i).getBlockNumber() != trackController.getTrainsControlled().get(i).getBlockNumber())
				{
					same = false;
				}
			}
		}
		
		if(!same)
		{
			_tcp.displayChange();
		}
		trackController.setTrainsControlled(tempTrain);
	}
	
	public static void crossings(TrackController trackController){
		if(trainList != null)
		{
			for(int i = 0; i < trainList.size(); i++)
			{
				for(int j = 0; j < trackController.crossingsControlled.size(); j++)
				{
					if(trainList.get(i + 1).getLine().equals(trackController.getLine()) && trainList.get(i + 1).getRouteInfo() != null)
					{
						if(trainList.get(i + 1).getRouteInfo().size() > trainList.get(i + 1).getBlockIndex() + 1)
						{
							if(trackController.crossingsControlled.contains(trainList.get(i + 1).getBlock()) || trackController.crossingsControlled.contains(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1)))//trainList.get(i).getBlockIndex()))
							{
								if(trackController.crossingsControlled.get(j).getCrossing() == true)
								{
									trackController.isChanged = true;
								}
								trackController.crossingsControlled.get(j).setCrossing(false);
							}
							else
							{
								if(trackController.crossingsControlled.get(j).getCrossing() == false)
								{
									trackController.isChanged = true;
								}
								trackController.crossingsControlled.get(j).setCrossing(true);
							}
						}
						else
						{
							if(trackController.crossingsControlled.get(j).getCrossing() == false)
							{
								trackController.isChanged = true;
							}
							trackController.crossingsControlled.get(j).setCrossing(true);
						}
					}
				}
			}
			if(trackController.isChanged)
			{
				_tcp.displayChange();
			}
			trackController.isChanged = false;
		}
	}
	
	public static void switches(TrackController trackController){
		//System.out.println("here in switches");
		/*if(trainList != null)
		{
			//System.out.println("train list isn't null!!!");
			for(int i = 0; i < trainList.size(); i++)
			{
				for(int x = 0; x < trackController.switchesControlled.size(); x++)
				{
					//System.out.println("Were in the for loop to go through the trains");
					if(trainList.get(i + 1).getLine().equals(trackController.getLine()) && trainList.get(i + 1).getRouteInfo() != null)
					{
						//trains are on this list
						//System.out.println("route info isn't null!");
						if(trainList.get(i + 1).getLine().equals(trackController.getLine()))
						{
							//System.out.println("were on the same line as a track controller");
							//train's has at least two more blocks in route
							//System.out.println("route info size " + trainList.get(i + 1).getRouteInfo().size());
							if(trainList.get(i + 1).getRouteInfo().size() > trainList.get(i + 1).getBlockIndex() + 2)
							{
								//if next two blocks are not null
								if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1) != null && trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2) != null)
								{
									System.out.println("The next two blocks in the route info aren't null!");
									//next block is a switch
									System.out.println("switch controlled is " + trackController.switchesControlled.get(0).getBlockNumber());
									System.out.println("train next block is " + trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber());
									
									//if next block in route info is a switch
									if(trackController.switchesControlled.contains((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1))))
									{
										System.out.println("We have made it into the switch depth where we start to check adjacency list");
										System.out.println(_sim.getLine(trainList.get(i + 1).getLine()));
										//for(int j = 0; j < sim.getLine(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).size(); j++)
										if(_sim.getLine(trainList.get(i + 1).getLine()).adj(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1 ).getBlockNumber()) != null)
										{
											for(int j = 0; j < _sim.getLine(trainList.get(i + 1).getLine()).adj(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).size(); j++)
											{
												//if(sim.getLine(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).get(j) == trainList.get(i + 1).getBlock().getBlockNumber())
		
												//if(_sim.getLine(trainList.get(i + 1).getLine()).adj((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()) == trainList.get(i + 1).getBlock().getBlockNumber()))
												if(_sim.getLine(trainList.get(i + 1).getLine()).adj(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber()).get(j) == trainList.get(i + 1).getBlock().getBlockNumber())
												{
													//switch to next next
													//trackController.switchesControlled.get((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber())).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2).getBlockNumber());
													trackController.switchesControlled.get(x).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2).getBlockNumber());
												}
												else
												{
													//switch to current
	
													//trackController.switchesControlled.get((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber())).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex()).getBlockNumber());
													trackController.switchesControlled.get(x).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex()).getBlockNumber());
												}
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
			}
		}*/
		
		
	
	///////////////////////////////////////////////////////////////////////////
	if(trainList != null)
	{
		//for each train
		for(int i = 0; i < trainList.size(); i++)
		{
			//for each switch controlled by this track controller
			for(int j = 0; j < trackController.switchesControlled.size(); j++)
			{
				//if this train is on the same line as the track controller
				if(trainList.get(i + 1).getLine().equals(trackController.line))
				{
					if(trainList.get(i + 1).getRouteInfo().size() > trainList.get(i + 1).getBlockIndex() + 2)
					{
						//if the train has a route
						if(trainList.get(i + 1).getRouteInfo() != null)
						{
							//if this train's next two blocks are not null
							if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2) != null)
							{
								//if next block on route is the current switch on the list of switches
								if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1) == trackController.switchesControlled.get(j))
								{
									//cycle through the adjacency list of the block that contains the switch
									for(int k = 0; k < _sim.getLine(trainList.get(i + 1).getLine()).adj(trackController.switchesControlled.get(j).getBlockNumber()).size(); k++)
									{
										//if the current block on the adjacency list is equal to the current block, we use next next
										if(_sim.getLine(trainList.get(i + 1).getLine()).adj(trackController.switchesControlled.get(j).getBlockNumber()).get(k) == trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex()).getBlockNumber())
										{
											//System.out.println("The current block is on the adjacency list");
											trackController.switchesControlled.get(j).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 2).getBlockNumber());
										}
										//if the current block on the adjacency list is NOT equal to the current block, we use current block
										else
										{
											//System.out.println("The current block is not on the adjacency list");
											trackController.switchesControlled.get(j).setSwitchedTo(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex()).getBlockNumber());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
		
		
		
		
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
				//System.out.println("train list is not null");
				for(int i = 0; i < trainList.size(); i++)
				{
					if(trainList.get(i + 1).getRouteInfo() != null)
					{
						//System.out.println("route info is not null");
						for(int j = 0; j < trainList.get(i + 1).getRouteInfo().size(); j++)
						{
							if(trackController.brokenRails.contains(trainList.get(i + 1).getRouteInfo().get(j).getBlockNumber()))
							{
								//System.out.println("track controller broken rails contains this block number");
								if(trainList.get(i + 1).getBlockIndex() + 1 < trainList.get(i + 1).getRouteInfo().size())
								{
									//System.out.println("next index is less than train list size");
									if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getBlockNumber() == trackController.brokenRails.get(index) && !trainList.get(i + 1).getEmergencyBrake())
									{
										//System.out.println("we found out that next is a broken rail");
										trainList.get(i + 1).toggleEmergencyBrake();
										//System.out.println("The next rail is broken");
									}
									else
									{
										//System.out.println("we found out that broken rail is coming up but is not next");
										_sim.routeTrain(trainList.get(i + 1), trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getRouteInfo().size() - 1).getBlockNumber(), _sim.getLine(trainList.get(i + 1).getLine()));
									}
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
	
	public static void preventAccidents(TrackController trackController)
	{
		//if train list is not null
		if(trainList != null)
		{
			//for each train
			for(int i = 0; i < trainList.size(); i++)
			{
				//if the train has route info
				if(trainList.get(i + 1).getRouteInfo() != null)
				{
					//through each block
					for(int j = 0; j < trainList.get(i + 1).getRouteInfo().size(); j++)
					{
						//if a block is occupied
						if(trainList.get(i + 1).getRouteInfo().get(j).getOccupied())
						{
							//if we are not at the last block in the train's route
							if((j + 1) < trainList.get(i + 1).getRouteInfo().size())
							{
								for(int k = 0; k < trainList.get(i + 1).getRouteInfo().size(); k++)
								{
									if(trainList.get(i + 1).getRouteInfo().get(k).getType() == 1)
									{
										trainList.get(i + 1).getRouteInfo().get(k + 1).setFailure(true);
										_sim.routeTrain(trainList.get(i + 1), trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getRouteInfo().size() - 1).getBlockNumber(), _sim.getLine(trainList.get(i + 1).getLine()));
										trainList.get(i + 1).getRouteInfo().get(k + 1).setFailure(false);
										break;
									}
								}
								
							}
						}
						//If two trains are about to collide, we need to hit the E-Brake
						if((trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getOccupied()) && trainList.get(i + 1).getEmergencyBrake() == false)
						{
							trainList.get(i + 1).toggleEmergencyBrake();
						}
					}
				}
			}
		}
	}
	
	public static void handleAuthority(TrackController trackController)
	{
		if(trainList != null)
		{
			for(int i = 0; i < trainList.size(); i++)
			{
				if(trainList.get(i + 1).getAuthority() > (trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1)) 
				{
					if(trainList.get(i + 1).getAuthority() != trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1)
					{
						trainList.get(i + 1).setAuthority(trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1);
						break;
					}
				}
				if(trainList.get(i + 1).getRouteLength() > 1 && trainList.get(i + 1).getBlockIndex() < trainList.get(i + 1).getRouteLength() - 1)
				{
					if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getOccupied() || trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getFailure())
					{
						if(trainList.get(i + 1).getAuthority() != 0)
						{
							trainList.get(i + 1).setAuthority(0);
						}
					}
					//Next block is OK
					else
					{
						for(int j = 0; j < trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1; j++)
						{
							if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + j).getOccupied() && j > 0)
							{
								if(trainList.get(i + 1).getAuthority() != (j-1))
								{
									trainList.get(i + 1).setAuthority(j - 1);
								}
							}
						}
					}
				}
			}
		}
	}
}


//for all trains
//for(int i = 0; i < trainList.size(); i++)
//{
//	//for each block in the route
//	for(int j = 1; j < trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1; j++)
//	{
//		if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + j).getFailure() || trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + j).getOccupied())
//		{
//			if(trainList.get(i + 1).getAuthority() != (j - 1))
//			{
//				if(trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + j - 1).getType() != 1)
//				{
//					trainList.get(i + 1).setAuthority(j - 1);
//				}
////				else
////				{
////					_sim.routTrain(trainList.get(i + 1), trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getRouteInfo().size() - 1).getBlockNumber(), _sim.getLine(trainList.get(i + 1).getLine()));
////					
////				}
//			}
//		}
//		else
//		{
//			if(trainList.get(i + 1).getAuthority() != trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1)
//			{
//				trainList.get(i + 1).setAuthority(trainList.get(i + 1).getRouteLength() - trainList.get(i + 1).getBlockIndex() - 1);
//			}
//		}
//	}
//	
////	
//}



//if train list is not null
/*	if(trainList != null)
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
					if((i + 1) != trainList.size())
					{
						if(!trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getOccupied() && !trainList.get(i + 1).getRouteInfo().get(trainList.get(i + 1).getBlockIndex() + 1).getFailure())
						{
							trainList.get(i + 1).setAuthority(1);
						}
					}
				}
			}
		}
	}*/
	
	//there is a train list
//	if(trainList != null)
//	{
//		//for all trains
//		for(int i = 0; i < trainList.size(); i++)
//		{
//			//for each block in the route
//			for(int j = trainList.get(i + 1).getBlockIndex(); j < trainList.get(i + 1).getRouteLength() - 1; j++)
//			{
//				if(trainList.get(i + 1).getRouteInfo().get(j + 1).getOccupied() || trainList.get(i + 1).getRouteInfo().get(j + 1).getFailure())
//				{
//					//int z = trainList.get(i + 1).getRouteLength() - (j + 1)
//					if(trainList.get(i + 1).getAuthority() != trainList.get(i + 1).getRouteLength() - (j + 1))
//					{
//						trainList.get(i + 1).setAuthority(trainList.get(i + 1).getRouteLength() - (j + 1));
//					}
//					
//				}
//			}
////			
//		}
//	}

