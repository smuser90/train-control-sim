/*
 * Track Controller
 * Holds properties for each track controller and runs PLC for the given track controller
 * Author: Zachary Shelhamer
 * Date Created: 4/7/2013
 * Date Last Updated:4/24/2013
 */

package TrackController;

import java.util.ArrayList;

import TrackModel.Block;
import TrackModel.Line;

/**
 * Holds properties for each track controller and runs PLC for the given track
 * controller
 * 
 * @author Zachary Shelhamer
 * 
 */

public class TrackController
{
	private int blocks;
	private int number;
	private int track;
	private int trains;
	protected boolean isChanged = false;
	protected ArrayList<Block> blocksControlled = new ArrayList<Block>();
	protected ArrayList<Integer> brokenRails;
	protected ArrayList<Block> crossingsControlled = new ArrayList<Block>();
	protected ArrayList<Block> switchesControlled = new ArrayList<Block>();
	protected String line;
	protected ArrayList<Block> trainsControlled = new ArrayList<Block>();

	/* Initialize the properties for a given track controller */
	public TrackController(int tcIndex, ArrayList<Block> controlled,
			String lineName)
	{
		this.blocksControlled = controlled;
		this.number = tcIndex;
		brokenRails = new ArrayList<Integer>();
		crossingsControlled = buildCrossingList(controlled);
		switchesControlled = buildSwitchList(controlled);
		line = lineName;
	}

	/**
	 * Generates a list of crossings
	 * 
	 * @param blocks
	 *            List of blocks
	 * @return List of crossings
	 */
	public ArrayList<Block> buildCrossingList(ArrayList<Block> blocks)
	{
		ArrayList<Block> tempCrossingList = new ArrayList<Block>();
		for (int x = 0; x < blocks.size(); x++)
		{
			if (blocks.get(x).getType() == 2)
			{
				tempCrossingList.add(blocks.get(x));
			}
		}
		return tempCrossingList;
	}

	/**
	 * Generates a list of switches
	 * 
	 * @param blocks
	 *            List of blocks
	 * @return List of switches
	 */
	public ArrayList<Block> buildSwitchList(ArrayList<Block> blocks)
	{
		ArrayList<Block> tempSwitchList = new ArrayList<Block>();
		for (int x = 0; x < blocks.size(); x++)
		{
			if (blocks.get(x).getType() == 1)
			{
				tempSwitchList.add(blocks.get(x));
			}
		}
		return tempSwitchList;
	}

	/**
	 * Runs the PLC for each track controller
	 * 
	 * @param trackControllerList
	 *            List of Track Controllers
	 * @param lines
	 *            List of lines
	 */
	public static void runPLC(
			ArrayList<ArrayList<TrackController>> trackControllerList,
			ArrayList<Line> lines)
	{
		for (int index = 0; index < trackControllerList.size(); index++)
		{
			for (int index_2 = 0; index_2 < trackControllerList.get(index)
					.size(); index_2++)
			{
				PLC.runPLC(trackControllerList.get(index).get(index_2), lines
						.get(index).getBlocks());
			}
		}
	}

	/**
	 * Set the number of trains for this track controller
	 * 
	 * @param numTrains
	 *            Number of trains
	 */
	public void setNumTrains(int numTrains)
	{
		trains = numTrains;
	}

	/**
	 * Return the number of trains for this track controller
	 * 
	 * @return Number of trains for this track controller
	 */
	public int getNumTrains()
	{
		return trains;
	}

	/**
	 * Set the number of blocks this track controller controls
	 * 
	 * @param numBlocks
	 */
	public void setNumBlocks(int numBlocks)
	{
		blocks = numBlocks;
	}

	/**
	 * Return the number of blocks this track controller controls
	 * 
	 * @return Number of blocks this track controller controls
	 */
	public int getNumBlocks()
	{
		return this.blocksControlled.size();
	}

	/**
	 * Return track number
	 * 
	 * @return Track Number
	 */
	public int getTrackNum()
	{
		return 0;
	}

	/**
	 * Return Controller ID
	 * 
	 * @return Controller ID
	 */
	public int getID()
	{
		return number;
	}

	/**
	 * Set the broken rail list
	 * 
	 * @param brokenRail
	 *            ArrayList of broken rails
	 */
	public void setBrokenRail(ArrayList<Integer> brokenRail)
	{
		brokenRails = brokenRail;
	}

	/**
	 * Get list of broken rails
	 * 
	 * @return List of broken rails
	 */
	public ArrayList<Integer> getBrokenRail()
	{
		return brokenRails;
	}

	/**
	 * Return the line name
	 * 
	 * @return Line name
	 */
	public String getLine()
	{
		return line;
	}

	/**
	 * Set the trains controlled by this controller
	 * 
	 * @param _trainsControlled
	 *            List of blocks controlled trains are on
	 */
	public void setTrainsControlled(ArrayList<Block> _trainsControlled)
	{
		trainsControlled = _trainsControlled;
	}

	/**
	 * Return the list of trains controlled by this controller
	 * 
	 * @return List of blocks controlled trains are on
	 */
	public ArrayList<Block> getTrainsControlled()
	{
		return trainsControlled;
	}
}