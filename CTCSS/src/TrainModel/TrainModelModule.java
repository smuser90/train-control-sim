/*
 * 	TrainModelModule.java
 * 	Train Model Module Class - Top of the TMM Hierarchy. Adds and Removes train models, keeps track of them. 
 * 	Author: Charles Musso
 * 	Date Created: 04/07/13
 * 	Date Last Updated: 4/25/13
 * 
 */

package TrainModel;

import TrainController.TrainControllerModule;
import Simulator.Simulator;
import Log.Log;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;
import java.util.Set;

/**
 * TrainModelModule: Top of the TMM Hierarchy. Adds and Removes train models, keeps track of them. 
 * @author Charles Musso
 *
 */
public class TrainModelModule 
{
	private boolean locked = true;
	private Simulator m_sim = null;
	private static TMPanel m_gui = null;
	private Map<Integer, TrainModel> m_trainList;
	private TrainControllerModule m_tcModule = null;
	private int m_trainID;
	private Log log;
	private int m_tickCount;
	
	
	/**
	 * Create the module
	 */
	public TrainModelModule()
	{
		m_gui = new TMPanel(this);
		log = Log.Instance();
		m_trainID = 1;
		m_trainList = new HashMap<Integer, TrainModel>();
		m_tickCount = 0;
	}
	
	/**
	 * link the simulator reference
	 * @param sim the Simulator
	 */
	public void linkSimulator(Simulator sim)
	{
		m_sim = sim;
	}
	
	/**
	 * link the train controller reference
	 * @param tc the TrainControllerModule
	 */
	public void receiveController(TrainControllerModule tc)
	{
		m_tcModule = tc;
	}
	
	/**
	 * get the train list
	 * @return hashMap of TrainIDS and TrainModels
	 */
	public Map<Integer, TrainModel> getTrainList()
	{
		return m_trainList;
	}
	
	/**
	 * get the JPanel associated with this class
	 * @return JPanel that displays the module
	 */
	public TMPanel getPanel()
	{
		return m_gui;
	}
	
	/**
	 * get the trainID Set
	 * @return Set of TrainIDS
	 */
	public Set<Integer> getTrainIDS()
	{
		return m_trainList.keySet();
	}
	
	/**
	 * add a train 
	 * @param line line to add train to 
	 */
	public TrainModel addTrain(String line)
	{
		TrainModel train = new TrainModel(m_trainID, line, m_tcModule.getTrainController(), this);
		train.setTrainController();
		m_trainList.put( new Integer(m_trainID), train);
		m_trainID++;
		m_gui.populateBox();
		return train;
	}
	
	/**
	 * add a train 
	 * @param line line to add train to 
	 */
	public void modifyTrain(int ID, int field, int value)
	{
		TrainModel train = m_trainList.get(new Integer(ID));
		switch(field)
		{
		case 0: //Speed Limit
			train.setSpeedLimit(value);
			break;
		case 1: //Authority
			train.setAuthority(value);
			break;
		}
		m_gui.update();
	}
	
	/**
	 * remove a train
	 * @param ID the ID associated with the train to be removed
	 */
	public void removeTrain(int ID)
	{
		m_trainList.remove(new Integer(ID));
		m_gui.populateBox();
		writeLog(2, "Train:"+ID+" retired to the yard\n");
	}
	
	/**
	 * send tick update to all trains
	 * @param timeLapse the amount of simulation time that has passed
	 */
	public void tick(double timeLapse)
	{
		for(int i = 1; i < m_trainID; i++)
		{
			TrainModel tm = m_trainList.get( new Integer(i));
			if(tm != null)
				tm.tick(timeLapse);
		}
		
		m_tickCount++;
		m_tickCount = m_tickCount % 2;
		if(m_tickCount == 0)
			m_gui.update();
	}
	
	/**
	 * toggles lock to keep trains from updating when simulation paused
	 */
	public void toggleLock()
	{
		locked = !locked;
	}
	
	/* Returns current Simulation Time */
	protected long getSimTime()
	{
		return m_sim.getSimTime();
	}
	
	/* Write out to the global log */ 
	protected void writeLog(int severity, String str)
	{
		log.append(severity, str);
	}
	
	/* Pauses simulation: Allows no updates when true */

}
