package TrainController;

import java.util.ArrayList;

import TrainModel.TrainModel;

public class TrainControllerModule {
	
private TNCPanel gui = null;
private ArrayList<TrainController> controllers;
	public TrainControllerModule()
	{
		controllers = new ArrayList<TrainController> ();
		gui = new TNCPanel(this, controllers);
		
	}
	
	public TNCPanel getPanel()
	{
		return gui;
	}
	
	public TrainController newTNC(TrainModel tnm) {
//		gui.comboBox.addItem(tnm.trainID);
		TrainController tnc = new TrainController(gui, tnm);
		controllers.add(tnc);
		return tnc;
		
	}

}
