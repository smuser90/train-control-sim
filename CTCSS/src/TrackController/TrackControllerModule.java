/*
 * Track Controller Module
 * Interacts with the Simulator to attain track and on each tick of the clock, sends signal for each Track Controller to run it's PLC program
 * Author: Zachary Shelhamer
 * Date Created: 4/7/2013
 * Date Last Updated: 4/24/2013
 */

package TrackController;

import Simulator.Simulator;
import TrackModel.Block;
import TrackModel.Line;
import TrainModel.TrainModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Interacts with the Simulator to attain track and on each tick of the clock,
 * sends signal for each Track Controller to run it's PLC program
 * 
 * @author Zachary Shelhamer
 * 
 */
public class TrackControllerModule
{
	private TrackControllerPanel currentPanel;
	private ArrayList<ArrayList<TrackController>> trackControllerList;
	private ArrayList<String> lineNames;
	private ArrayList<Line> lines;
	private ArrayList<Block> myBlocks;
	private ArrayList<Integer> switchList;
	private Map<Integer, TrainModel> trainList;
	private ArrayList<Integer> crossingList;
	private Simulator _sim;
	private boolean hasTrack = false;
	private long tickCount = 0;
	/* Initialize the lists and setup the panel */
	public TrackControllerModule()
	{
		myBlocks = new ArrayList<Block>();
		switchList = new ArrayList<Integer>();
		crossingList = new ArrayList<Integer>();
		trackControllerList = new ArrayList<ArrayList<TrackController>>();
		lineNames = new ArrayList<String>();
		lineNames.add("Lines");
		currentPanel = new TrackControllerPanel(this);
		lines = new ArrayList<Line>();
	}

	/**
	 * Track Controller Module receives track and adds a new Track Controller
	 * List
	 * 
	 * @param track
	 *            Line that represents track
	 * @param sim
	 *            Instance of Simulator Class
	 */
	public void getTrack(Line track, Simulator sim)
	{
		_sim = sim;
		myBlocks = track.getBlocks();
		trackControllerList.add(TCListMaker.makeTCList(track));
		lineNames.add(track.getName());
		lines.add(track);
		hasTrack = true;
		currentPanel.displayChange();
		PLC.setup(this, _sim, currentPanel);
		runPLC();

	}

	/**
	 * Returns the Current Panel
	 * 
	 * @return Current Panel
	 */
	public TrackControllerPanel getPanel()
	{
		return this.currentPanel;
	}

	/**
	 * Receives 'tick' from the system
	 */
	public void receiveTick()
	{
		tickCount++;
		if (hasTrack)
		{
			if(tickCount % 4 == 0) {
				runPLC();
			}
		}
	}

	/**
	 * Receives list of all Trains and sets Track Controller Module's Train List
	 * 
	 * @param newTrainList
	 *            List of all Trains on all Tracks
	 */
	public void receiveTrains(Map<Integer, TrainModel> newTrainList)
	{
		trainList = newTrainList;
		currentPanel.displayChange();
	}

	/**
	 * Tells Track Controller class to run the PLC program
	 */
	public void runPLC()
	{
		TrackController.runPLC(trackControllerList, lines);
		if (currentPanel.getCL() >= 0
				&& trackControllerList.get(currentPanel.getCL()).get(
						currentPanel.getCC()).isChanged)
		{
			currentPanel.displayChange();
		}
	}

	protected ArrayList<Block> getBlockList(String line)
	{
		for (Line l : lines)
		{
			if (l.getName().equals(line))
				return l.getBlocks();
		}
		return null;
	}

	protected Map getTrainList()
	{
		return trainList;
	}

	protected ArrayList<Integer> getSwitchList()
	{
		return switchList;
	}

	protected ArrayList<TrackController> getTrCList(int tcIndex)
	{
		return trackControllerList.get(tcIndex);
	}

	protected ArrayList<Integer> getCrossingList()
	{
		return crossingList;
	}

	protected ArrayList<String> getLineNames()
	{
		return this.lineNames;
	}
}
