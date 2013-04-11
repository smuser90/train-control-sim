package TrainModel;

import TrackModel.Block;
import TrainController.TrainControllerModule;

import java.util.Map;
import java.lang.Integer;

public class TrainModelModule 
{
	private static TMPanel m_gui = null;
	private static Map<Integer, TrainModel> m_trainList = null;
	private TrainControllerModule m_tcModule = null;
	private int m_trainID;
	
	
	public TrainModelModule()
	{
		m_gui = new TMPanel();
		m_trainID = 1;
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
	
	public void addTrain()
	{
		TrainModel train = new TrainModel(m_trainID, m_tcModule.getTrainController());
		train.setTrainController(train);
		m_trainList.put( new Integer(m_trainID), train);
		m_trainID++;
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
	}
	
	public void removeTrain(int ID)
	{
		m_trainList.remove(new Integer(ID));
		
	}
	
	public void tick(double timeLapse)
	{
		for(int i = 1; i < m_trainID; i++)
		{
			TrainModel tm = m_trainList.get( new Integer(i));
			if(tm != null)
				tm.tick(timeLapse);
		}
	}
}
