/*
 * Simulator.java
 * Simulates time and all the systems talking to each other
 * Author: Nikolas Parshook
 * Date Created: 04/09/2013
 * Date Last Updated: 04/21/2013
 */

package Simulator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import CTC.CTCModule;
import Log.Log;
import System.System_GUI;
import TrackController.TrackControllerModule;
import TrackModel.Block;
import TrackModel.Line;
import TrackModel.Track;
import TrackModel.TrackModelModule;
import TrainModel.TrainModel;
import TrainModel.TrainModelModule;

/**
 * Simulates time and all the systems talking to each other
 * @author Nikolas Parshook
 *
 */
public class Simulator implements Runnable
{
	
	// Fields
	//private static final int trainsMax = 1;
	//private static int trains = 0;
	private Log log = null;
	private boolean paused = false;
	private int realTime = 250;
	private int timeStep = 1;
	private long sysTimeNum;
	private Date sysTime;
	private DateFormat df;
	private CTCModule ctc;
	private TrackControllerModule tcm;
	private TrainModelModule tm;
	private TrackModelModule trm;
	private System_GUI sys;
	private SpeedDialog sd;
	private Line newLine;
	
	/* Simulator Ops **********************************************************************/
	
	/**
	 * Run the simulator
	 */
	public void run() 
	{
		try 
		{
			if(!paused) 
			{
				// Put simulator to sleep for current time step
				Thread.sleep(realTime/timeStep);
				
				// Calculate and load new system time into log
				sysTimeNum += realTime;
				sysTime.setTime(sysTimeNum);
				loadLogTime();
				
				// For the current tick tell the Trains and Track Controllers to update
				tm.tick(realTime/1000.0);
				tcm.receiveTick();
				
				// Check for new tracks and load them
				if(trm.hasTrack()) 
				{
					newLine = trm.gotTrack();
					this.loadGTrack();
				}
				
				/*if(trains < trainsMax)
				{
					tm.addTrain("Dicks");
					trains++;
				}*/
				
			} 
			else 
			{
				Thread.sleep(1000);
			}
				
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		this.run();
	}
	
	/**
	 * Create the simulator
	 * @param c CTCModule for the system
	 * @param TcM TrackControllerModule for the system
	 * @param TM TrainModelModule for the system
	 * @param Tmm TrackModelModule for the system
	 */
	public Simulator(CTCModule c, TrackControllerModule TcM, TrainModelModule TM, TrackModelModule Tmm) 
	{
		ctc = c;
		tcm = TcM;
		tm = TM;
		tm.linkSimulator(this);
		trm = Tmm;
		sysTimeNum = System.currentTimeMillis();
		sysTime = new Date(sysTimeNum);
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
		log = Log.Instance();
		loadLogTime();
	}
	
	/**
	 * Set the system for the simulator
	 * @param s the system
	 */
	public void setSys(System_GUI s) 
	{
		sys = s;
	}
	
	/* Sets the time in the log */
	private void loadLogTime() 
	{
		log.setSysTime(df.format(sysTime));
	}
	
	/**
	 * Adjust the speed of the simulation time
	 * @param speed speed of the simulation
	 */
	public void setSimSpeed(int speed) 
	{
		if(speed == -1) 
		{
			log.append(3, "Speed must be a number 'x' s.t. 1 <= x <= 10\n");
		} 
		else 
		{
			timeStep = speed;
			log.append(1, "Speed set to " + Integer.toString(timeStep) + "*real time\n" );
		}
	}
	
	/**
	 * Pause and unpause the simulator
	 */
	public void togglePause() 
	{
		paused = !paused;
		if(paused) 
		{
			tm.toggleLock();
			log.append(1, "Simulation Paused\n");
		} 
		else 
		{
			tm.toggleLock();
			log.append(1, "Simulation Unpaused\n");
		}
	}
	
	/**
	 * Get the dialog for setting the speed
	 * @return SpeedDialog
	 */
	public SpeedDialog getSpeedDialog() 
	{
		return new SpeedDialog(this, timeStep);
	}
	
	/**
	 * Get the dialog for the metrics
	 * @return
	 */
	public Metrics getMetrics() 
	{
		double temp = 0.0;
		for(int i = 0; i < tm.getTrainList().size(); i++) 
		{
			temp += tm.getTrainList().get(i+1).getPassengersPerHour();
		}
		return new Metrics(temp);
	}
	
	/**
	 * Return the value of the current system time
	 * @return long
	 */
	public long getSimTime()
	{
		return sysTimeNum;
	}
	
	/**
	 * Load a track into the simulator
	 */
	public void loadGTrack() 
	{
		tcm.getTrack(newLine, this);
		ctc.addLine(newLine);
		log.append(1, "Track Loaded\n");
	}
	
	/* TrackActions ***********************************************************/
	
	/**
	 * Returns the line associated with the line name lName
	 * @param lName name of the line
	 * @return Linie
	 */
	public Line getLine(String lName) 
	{
		return trm.getTrack().getLine(lName);
	}
	
	/**
	 * Get all of the lines currently in the simulator
	 * @return ArrayList<Line>
	 */
	public ArrayList<Line> getLines() 
	{
		return trm.getTrack().getLines();
	}
	
	/* Train Actions ***********************************************************/
	
	/**
	 * Add a new train to the Line line
	 * @param line line to add
	 */
	public void scheduleTrain(String line) 
	{
		TrainModel train = tm.addTrain(line);
		Track tr = trm.getTrack();
		Line ln = tr.getLine(line);
		routeTrain(train, 0, ln);
		ctc.setTrains(tm.getTrainList());
		tcm.receiveTrains(tm.getTrainList());
	}
	
	/**
	 * Route a train to the block StationID
	 * @param train the train to route
	 * @param StationID id of the block to route to
	 * @param l Line name that the station is on
	 */
	public void routeTrain(TrainModel train, int StationID, Line l) 
	{
		trm.route(train, train.getCurrentBlock(), StationID, l);
	}
	
	/**
	 * Set the authority of the train trainID
	 * @param trainID id of train to set authority of 
	 * @param a authority to set to
	 */
	public void setAuthority(int trainID, int a) 
	{
		tm.getTrainList().get(trainID).setAuthority(a);
	}
	
	/* Block Actions ***********************************************************/
	
	/**
	 * Set the speed limit of the block bNum to lim on Line lName
	 * @param bNum The block number of the block to change the speed limit
	 * @param lName name of the line the block is on
	 * @param lim speed limit to set to 
	 */
	public void setSpeedLimit(int bNum, String lName, int lim) 
	{
		trm.setSpeedLimit(bNum, lName, lim);
		trm.printOpen();
	}
	
	/**
	 * Open up the block bnum on lname
	 * @param bNum Block to open
	 * @param lName Line that the block is on
	 */
	public void openBLock(int bNum, String lName) 
	{
		trm.openBlock(bNum, lName);
	}
	
	/**
	 * Close the block bNum on line lName
	 * @param bNum Number of the block to close
	 * @param lName Name of the line the block is on
	 */
	public void closeBLock(int bNum, String lName) 
	{
		trm.closeBlock(bNum, lName);
	}
	
	/* Get the system the simulator is running on */
	protected System_GUI getSys() 
	{
		return sys;
	}
	
	/**
	 * Add a block to the track
	 * @param lName Name of the line to add a block to
	 * @param prevBlock Number of the block to add a bock after
	 */
	public void addBlock(String lName, int prevBlock) 
	{
		trm.addBlock(lName, prevBlock);
	}
	
	/**
	 * Remove a block from the track
	 * @param lName Name of the line to remove a block from
	 * @param blockNum Number of the block to remove
	 */
	public void removeBlock(String lName, int blockNum) 
	{
		trm.removeBlock(lName, blockNum);
	}
	
}
