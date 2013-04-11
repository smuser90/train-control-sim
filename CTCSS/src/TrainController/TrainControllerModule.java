package TrainController;

public class TrainControllerModule {
	
private TNCPanel gui = null;
	
	public TrainControllerModule()
	{
		gui = new TNCPanel();
	}
	
	public TNCPanel getPanel()
	{
		return gui;
	}
	
	public TrainController getTrainController(){
		return null;
	}

}
