package TrainModel;


public class TrainModelModule 
{
	private TMPanel gui = null;
	
	public TrainModelModule()
	{
		gui = new TMPanel();
	}
	
	public TMPanel getPanel()
	{
		return gui;
	}
}
