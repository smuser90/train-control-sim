 /*
 * TrainControllerModule.java
 * Description: TrainController module
 * Author: KE LUO
 * Date Created: 07/04/2013
 * Date Last Updated: 24/04/2013
 * 
 */

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
	}
	
	public TNCPanel getPanel()
	{
		return gui;
	}
	
	public TrainController getTrainController() {	
		TrainController tnc = new TrainController(gui);
		controllers.add(tnc);
		return tnc;
		
	}

}
