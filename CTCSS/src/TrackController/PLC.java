/*
 * PLC
 * PLC checks the status of the blocks associated with the given track controller and acts accordingly
 * Author: Zachary Shelhamer
 * Date Created: 4/7/2013
 * Date Last Updated: 4/24/2013
 */

package TrackController;

import Simulator.Simulator;
import TrackModel.Block;
import TrainModel.TrainModel;
import Log.Log;
import java.util.ArrayList;
import java.util.Map;

/**
 * Checks the status of the blocks associated with the given Track Controller
 * and acts accordingly.
 * 
 * @author Zachary Shelhamer
 * 
 */
public class PLC
{

	private static ArrayList<Block> myBlocks = new ArrayList<Block>();
	private static ArrayList<Integer> crossingList = null;
	private static ArrayList<Integer> brokenRailList;
	private static TrackController _tc;
	private static Map<Integer, TrainModel> trainList;
	private static TrackControllerModule _tcm;
	private static Simulator _sim;
	private static TrackControllerPanel _tcp;

	/**
	 * Runs the PLC program for the given Track Controller
	 * 
	 * @param trackController
	 *            Current Track Controller
	 * @param bL
	 *            List of blocks
	 */
	public static void runPLC(TrackController trackController,
			ArrayList<Block> bL)
	{
		myBlocks = bL;
		trainList = _tcm.getTrainList();
		detectTrains(trackController);
		crossings(trackController);
		detectBrokenRail(trackController);
		switches(trackController);
		handleAuthority(trackController);
		preventAccidents(trackController);
	}

	/**
	 * Sets up the Instances for the PLC class
	 * 
	 * @param tcm
	 *            Instance of Track Controller Module
	 * @param sim
	 *            Instance of Simulator
	 * @param currentPanel
	 *            Instance of Track Controller Panel
	 */
	public static void setup(TrackControllerModule tcm, Simulator sim,
			TrackControllerPanel currentPanel)
	{
		_tcm = tcm;
		_sim = sim;
		_tcp = currentPanel;
	}

	/**
	 * Detects the presence of Trains for the current Track Controller
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void detectTrains(TrackController trackController)
	{
		int temp = 0;
		boolean same = true;
		ArrayList<Block> tempTrain = new ArrayList<Block>();
		for (int index = 0; index < myBlocks.size(); index++)
		{
			if (trackController.blocksControlled.contains(myBlocks.get(index))
					&& myBlocks.get(index).getOccupied())
			{
				temp++;
				tempTrain.add(myBlocks.get(index));
			}
		}
		trackController.setNumTrains(temp);

		for (int i = 0; i < tempTrain.size(); i++)
		{
			if (tempTrain.size() == trackController.getTrainsControlled()
					.size())
			{
				if (tempTrain.get(i).getBlockNumber() != trackController
						.getTrainsControlled().get(i).getBlockNumber())
				{
					same = false;
					break;
				}
			}
		}

		if (!same)
		{
			_tcp.displayChange();
		}
		trackController.setTrainsControlled(tempTrain);
	}

	/**
	 * Handles the crossings on the track
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void crossings(TrackController trackController)
	{
		if (trainList != null)
		{
			for (int i = 0; i < trainList.size(); i++)
			{
				for (int j = 0; j < trackController.crossingsControlled.size(); j++)
				{
					if (trainList.get(i + 1).getLine()
							.equals(trackController.getLine())
							&& trainList.get(i + 1).getRouteInfo() != null)
					{
						if (trainList.get(i + 1).getRouteInfo().size() > trainList
								.get(i + 1).getBlockIndex() + 1)
						{
							if (trackController.crossingsControlled
									.contains(trainList.get(i + 1).getBlock())
									|| trackController.crossingsControlled
											.contains(trainList
													.get(i + 1)
													.getRouteInfo()
													.get(trainList.get(i + 1)
															.getBlockIndex() + 1)))
							{
								if (trackController.crossingsControlled.get(j)
										.getCrossing() == true)
								{
									trackController.isChanged = true;
								}
								trackController.crossingsControlled.get(j)
										.setCrossing(false);
							}
							else
							{
								if (trackController.crossingsControlled.get(j)
										.getCrossing() == false)
								{
									trackController.isChanged = true;
								}
								trackController.crossingsControlled.get(j)
										.setCrossing(true);
							}
						}
						else
						{
							if (trackController.crossingsControlled.get(j)
									.getCrossing() == false)
							{
								trackController.isChanged = true;
							}
							trackController.crossingsControlled.get(j)
									.setCrossing(true);
						}
					}
				}
			}
			if (trackController.isChanged)
			{
				_tcp.displayChange();
			}
			trackController.isChanged = false;
		}
	}

	/**
	 * Handles the switches on the track
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void switches(TrackController trackController)
	{
		if (trainList != null)
		{
			// for each train
			for (int i = 0; i < trainList.size(); i++)
			{
				// for each switch controlled by this track controller
				for (int j = 0; j < trackController.switchesControlled.size(); j++)
				{
					// if this train is on the same line as the track controller
					if (trainList.get(i + 1).getLine()
							.equals(trackController.line))
					{
						if (trainList.get(i + 1).getRouteInfo().size() > trainList
								.get(i + 1).getBlockIndex() + 2)
						{
							// if the train has a route
							if (trainList.get(i + 1).getRouteInfo() != null)
							{
								// if this train's next two blocks are not null
								if (trainList
										.get(i + 1)
										.getRouteInfo()
										.get(trainList.get(i + 1)
												.getBlockIndex() + 2) != null)
								{
									// if next block on route is the current
									// switch on the list of switches
									if (trainList
											.get(i + 1)
											.getRouteInfo()
											.get(trainList.get(i + 1)
													.getBlockIndex() + 1) == trackController.switchesControlled
											.get(j))
									{
										// cycle through the adjacency list of
										// the block that contains the switch
										for (int k = 0; k < _sim
												.getLine(
														trainList.get(i + 1)
																.getLine())
												.adj(trackController.switchesControlled
														.get(j)
														.getBlockNumber())
												.size(); k++)
										{
											// if the current block on the
											// adjacency list is equal to the
											// current block, we use next next
											if (_sim.getLine(
													trainList.get(i + 1)
															.getLine())
													.adj(trackController.switchesControlled
															.get(j)
															.getBlockNumber())
													.get(k) == trainList
													.get(i + 1)
													.getRouteInfo()
													.get(trainList.get(i + 1)
															.getBlockIndex())
													.getBlockNumber())
											{
												// System.out.println("The current block is on the adjacency list");
												trackController.switchesControlled
														.get(j)
														.setSwitchedTo(
																trainList
																		.get(i + 1)
																		.getRouteInfo()
																		.get(trainList
																				.get(i + 1)
																				.getBlockIndex() + 2)
																		.getBlockNumber());
											}
											// if the current block on the
											// adjacency list is NOT equal to
											// the current block, we use current
											// block
											else
											{
												// System.out.println("The current block is not on the adjacency list");
												trackController.switchesControlled
														.get(j)
														.setSwitchedTo(
																trainList
																		.get(i + 1)
																		.getRouteInfo()
																		.get(trainList
																				.get(i + 1)
																				.getBlockIndex())
																		.getBlockNumber());
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
	}

	/**
	 * Detects any broken rails on the track
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void detectBrokenRail(TrackController trackController)
	{
		for (int index = 0; index < trackController.blocksControlled.size(); index++)
		{
			if (myBlocks.get(
					trackController.blocksControlled.get(index)
							.getBlockNumber()).getFailure())
			{
				if (!trackController.brokenRails
						.contains(trackController.blocksControlled.get(index)
								.getBlockNumber()))
				{
					trackController.brokenRails
							.add(trackController.blocksControlled.get(index)
									.getBlockNumber());
					trackController.isChanged = true;
					reportBrokenRail(trackController.blocksControlled
							.get(index).getBlockNumber());
				}
			}
			else
			{
				if (trackController.brokenRails
						.contains(trackController.blocksControlled.get(index)
								.getBlockNumber()))
				{
					trackController.brokenRails
							.remove(trackController.brokenRails
									.indexOf(trackController.blocksControlled
											.get(index).getBlockNumber()));
					trackController.isChanged = true;
					handleFixedRail(trackController);
				}

			}

		}

		handleBrokenRail(trackController);
	}

	/**
	 * Handles when a rail is fixed
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	private static void handleFixedRail(TrackController trackController)
	{
		if (trainList != null)
		{
			for (int i = 0; i < trainList.size(); i++)
			{
				_sim.routeTrain(
						trainList.get(i + 1),
						trainList.get(i + 1).getRouteInfo()
								.get(trainList.get(i + 1).getRouteLength() - 1)
								.getBlockNumber(),
						_sim.getLine(trainList.get(i + 1).getLine()));

			}
		}
	}

	/**
	 * Handles the broken rails on the track
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void handleBrokenRail(TrackController trackController)
	{
		for (int index = 0; index < trackController.brokenRails.size(); index++)
		{
			if (trainList != null)
			{
				for (int i = 0; i < trainList.size(); i++)
				{
					if (trainList.get(i + 1).getRouteInfo() != null)
					{
						for (int j = 0; j < trainList.get(i + 1).getRouteInfo()
								.size(); j++)
						{
							if (trackController.brokenRails.contains(trainList
									.get(i + 1).getRouteInfo().get(j)
									.getBlockNumber()))
							{
								if (trainList.get(i + 1).getBlockIndex() + 1 < trainList
										.get(i + 1).getRouteInfo().size())
								{
									// if(trainList.get(i +
									// 1).getRouteInfo().get(trainList.get(i +
									// 1).getBlockIndex() + 1).getBlockNumber()
									// ==
									// trackController.brokenRails.get(index))
									// && !trainList.get(i +
									// 1).getEmergencyBrake())
									if (trainList
											.get(i + 1)
											.getRouteInfo()
											.get(trainList.get(i + 1)
													.getBlockIndex() + 1)
											.getBlockNumber() == trackController.brokenRails
											.get(index))
									{
										System.out
												.println("here in broken rail");
										// trainList.get(i +
										// 1).toggleEmergencyBrake();
										if (trainList.get(i + 1).getAuthority() != 0)
										{
											trainList.get(i + 1)
													.setAuthority(0);
										}

									}
									else
									{
										// System.out.println("here we are in handle broken rail");
										_sim.routeTrain(
												trainList.get(i + 1),
												trainList
														.get(i + 1)
														.getRouteInfo()
														.get(trainList
																.get(i + 1)
																.getRouteInfo()
																.size() - 1)
														.getBlockNumber(),
												_sim.getLine(trainList.get(
														i + 1).getLine()));
										// handleAuthority(trackController);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Reports broken rails to the log
	 * 
	 * @param blockNumber
	 *            Number of broken block
	 */
	public static void reportBrokenRail(int blockNumber)
	{
		Log.Instance().append(3, "Broken Rail At Block " + blockNumber + "\n");
	}

	/**
	 * Prevents train collisions on the track
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void preventAccidents(TrackController trackController)
	{
		// if train list is not null
		if (trainList != null)
		{
			// for each train
			for (int i = 0; i < trainList.size(); i++)
			{
				// if the train has route info
				if (trainList.get(i + 1).getRouteInfo() != null)
				{
					// through each block
					// for(int j = trainList.get(i + 1).getCurrentBlock() + 1; j
					// < trainList.get(i + 1).getRouteInfo().size() -
					// trainList.get(i + 1).getCurrentBlock() - 1; j++)
					// for(int j = trainList.get(i +
					// 1).getRouteInfo().get(trainList.get( i +
					// 1).getBlockIndex() + 1); j < trainList.get(i +
					// 1).getRouteLength() - trainList.get(i +
					// 1).getRouteInfo().get(trainList.get( i +
					// 1).getBlockIndex() + 1) - 1; j++)
					for (int j = trainList.get(i + 1).getBlockIndex() + 1; j < trainList
							.get(i + 1).getRouteLength()
							- trainList.get(i + 1).getBlockIndex() - 1; j++)
					{
						// if a block is occupied

						if (trainList.get(i + 1).getRouteInfo().get(j)
								.getOccupied()
								&& trainList.get(i + 1).getRouteInfo().get(j)
										.getDirection() == 1
								&& trainList
										.get(i + 1)
										.getRouteInfo()
										.get(trainList.get(i + 1)
												.getBlockIndex())
										.getDirection() == 1)

							if (trainList.get(i + 1).getRouteInfo().get(j)
									.getOccupied()
									&& trainList.get(i + 1).getTrainID() != trainList
											.get(i + 1).getRouteInfo().get(j)
											.blockTrainID())
							{
								// if we are not at the last block in the
								// train's route
								if ((j + 1) < trainList.get(i + 1)
										.getRouteInfo().size())
								{
									for (int k = 0; k < trainList.get(i + 1)
											.getRouteInfo().size(); k++)
									{
										if (trainList.get(i + 1).getRouteInfo()
												.get(k).getType() == 1)
										{
											trainList.get(i + 1).getRouteInfo()
													.get(k + 1)
													.setFailure(true);
											Block temp = trainList.get(i + 1)
													.getRouteInfo().get(k + 1);
											_sim.routeTrain(
													trainList.get(i + 1),
													trainList
															.get(i + 1)
															.getRouteInfo()
															.get(trainList
																	.get(i + 1)
																	.getRouteInfo()
																	.size() - 1)
															.getBlockNumber(),
													_sim.getLine(trainList.get(
															i + 1).getLine()));
											temp.setFailure(false);
											// trainList.get(i +
											// 1).getRouteInfo().get(k +
											// 1).setFailure(false);
											break;
										}
									}

								}
							}
						// If two trains are about to collide, we need to hit
						// the E-Brake
						if (trainList.get(i + 1).getBlock() != trainList
								.get(i + 1).getRouteInfo()
								.get(trainList.get(i + 1).getRouteLength() - 1))
						{
							if ((trainList
									.get(i + 1)
									.getRouteInfo()
									.get(trainList.get(i + 1).getBlockIndex() + 1)
									.getOccupied())
									&& trainList.get(i + 1).getTrainID() != trainList
											.get(i + 1)
											.getRouteInfo()
											.get(trainList.get(i + 1)
													.getBlockIndex() + 1)
											.blockTrainID())
							{
								System.out
										.println("here we are trying to prevent a hit");
								// trainList.get(i + 1).toggleEmergencyBrake();
								if (trainList.get(i + 1).getAuthority() != 0)
								{
									trainList.get(i + 1).setAuthority(0);
									// trainList.get(i + 1).setBrake(true);
									// trainList.get(i +
									// 1).toggleEmergencyBrake();
								}
							}
						}
						else
							handleAuthority(trackController);
					}
				}
			}
		}
	}

	/**
	 * Handles the authority for the trains
	 * 
	 * @param trackController
	 *            Current Track Controller
	 */
	public static void handleAuthority(TrackController trackController)
	{
		if (trainList != null)
		{
			for (int i = 0; i < trainList.size(); i++)
			{
				if (trainList.get(i + 1).getAuthority() > (trainList.get(i + 1)
						.getRouteLength()
						- trainList.get(i + 1).getBlockIndex() - 1))
				{
					if (trainList.get(i + 1).getAuthority() != trainList.get(
							i + 1).getRouteLength()
							- trainList.get(i + 1).getBlockIndex() - 1)
					{
						// System.out.println("here in the top part of handle authority");
						trainList.get(i + 1).setAuthority(
								trainList.get(i + 1).getRouteLength()
										- trainList.get(i + 1).getBlockIndex()
										- 1);
						break;
					}
				}
				if (trainList.get(i + 1).getRouteLength() > 1
						&& trainList.get(i + 1).getBlockIndex() < trainList
								.get(i + 1).getRouteLength() - 1)
				{
					if ((trainList.get(i + 1).getRouteInfo()
							.get(trainList.get(i + 1).getBlockIndex() + 1)
							.getOccupied() && trainList.get(i + 1).getTrainID() != (trainList
							.get(i + 1).getRouteInfo().get(trainList.get(i + 1)
							.getBlockIndex() + 1)).blockTrainID())
							|| trainList
									.get(i + 1)
									.getRouteInfo()
									.get(trainList.get(i + 1).getBlockIndex() + 1)
									.getFailure())
					{
						// System.out.println("here in middle part of handle authority");
						if (trainList.get(i + 1).getAuthority() != 0)
						{
							trainList.get(i + 1).setAuthority(0);
						}
					}
					// Next block is OK
					else
					{
						for (int j = 0; j < trainList.get(i + 1)
								.getRouteLength()
								- trainList.get(i + 1).getBlockIndex() - 1; j++)
						{
							if (trainList
									.get(i + 1)
									.getRouteInfo()
									.get(trainList.get(i + 1).getBlockIndex()
											+ j).getOccupied()
									&& trainList.get(i + 1).getTrainID() != trainList
											.get(i + 1)
											.getRouteInfo()
											.get(trainList.get(i + 1)
													.getBlockIndex() + j)
											.blockTrainID() && j > 0)
							{
								// System.out.println("here in the last part of handle authority");
								if (trainList.get(i + 1).getAuthority() != (j - 1))
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
