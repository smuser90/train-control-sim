package TrainController;

import java.util.ArrayList;

import TrainModel.TrainModel;

public class TrainControllerModule {
	
private TNCPanel gui = null;
private ArrayList<TrainController> controllers;
private TrainController tnc, tnc1;
	public TrainControllerModule()
	{
		controllers = new ArrayList<TrainController> ();
		gui = new TNCPanel(this, controllers);
		
		
		
		// testing without train model
/*		tnc = new TrainController(gui, 0);
		controllers.add(tnc);
		tnc1 = new TrainController(gui, 1);
		controllers.add(tnc1);*/
		
	}
	
	public TNCPanel getPanel()
	{
		return gui;
	}
	
	public TrainController getTrainController() {
//		
		TrainController tnc = new TrainController(gui);
		controllers.add(tnc);
		return tnc;
		
	}

}
