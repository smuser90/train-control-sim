package TrainModel;

import TrainController.TrainControllerModule;
import Simulator.Simulator;
import Log.Log;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;
import java.util.Set;

public class TrainModelModule 
{
	private boolean locked = true;
	private Simulator m_sim = null;
	private static TMPanel m_gui = null;
	private Map<Integer, TrainModel> m_trainList;
	private TrainControllerModule m_tcModule = null;
	private Log	log = null;
	private int m_trainID;
	
	
	public TrainModelModule()
	{
		m_gui = new TMPanel(this);
		log = Log.Instance();
		m_trainID = 1;
		m_trainList = new HashMap<Integer, TrainModel>();
	}
	
	public void linkSimulator(Simulator sim)
	{
		m_sim = sim;
	}
	public void receiveController(TrainControllerModule tc)
	{
		m_tcModule = tc;
	}
	
	public Map<Integer, TrainModel> getTrainList()
	{
		return m_trainList;
	}
	
	public TMPanel getPanel()
	{
		return m_gui;
	}
	
	public Set<Integer> getTrainIDS()
	{
		return m_trainList.keySet();
	}
	
	public void addTrain(int line)
	{
		System.out.println("addTrain called");
		TrainModel train = new TrainModel(m_trainID, line, m_tcModule.getTrainController(), this);
		train.setTrainController();
		m_trainList.put( new Integer(m_trainID), train);
		m_trainID++;
		if(line == 0)
			log.append(0, "Train Added on Green Line\n");
		else
			log.append(0,  "Train Added on Red Line\n");
		m_gui.populateBox();
	}
	
	public void toggleLock()
	{
		locked = !locked;
	}
	
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
	
	public void removeTrain(int ID)
	{
		m_trainList.remove(new Integer(ID));
		m_gui.populateBox();
	}
	
	public long getSimTime()
	{
		return m_sim.getSimTime();
	}
	
	public void tick(double timeLapse)
	{
		for(int i = 1; i < m_trainID; i++)
		{
			TrainModel tm = m_trainList.get( new Integer(i));
			if(tm != null)
				tm.tick(timeLapse);
		}
		m_gui.update();
	}
}
