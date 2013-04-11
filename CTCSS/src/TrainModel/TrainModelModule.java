package TrainModel;

import TrackModel.Block;
import java.util.*;

public class TrainModelModule 
{
	private static TMPanel m_gui = null;
	private static Map<Integer, TrainModel> m_trainList = null;
	private int m_trainID;
	
	
	public TrainModelModule()
	{
		m_gui = new TMPanel();
		m_trainID = 1;
	}
	
	public TMPanel getPanel()
	{
		return m_gui;
	}
	
	public boolean addTrain()
	{
		TrainModel train = new TrainModel(m_trainID);
		m_trainID++;
		m_trainList
		return true;
	}
	
	public boolean modifyTrain(int ID)
	{
		
	}
	
	public void tick(double timeLapse)
	{
		for(int i = 1; i < m_trainID; i++)
		{
			m_trainList.get(Integer(i))
		}
	}
}
