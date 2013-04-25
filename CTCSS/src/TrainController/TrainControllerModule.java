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


/**
 * train controller module
 * @author keluo
 *
 */
public class TrainControllerModule {
	
private TNCPanel gui = null;	// gui
//arraylist to store train controller list
private ArrayList<TrainController> controllers;		

	/**
	 * train controller constructor
	 */
	public TrainControllerModule()
	{
		controllers = new ArrayList<TrainController> ();
		gui = new TNCPanel(this, controllers);
	}
	
	/**
	 * get GUI
	 * @return
	 */
	public TNCPanel getPanel()
	{
		return gui;
	}
	
	/**
	 * get train controller
	 * @return
	 */
	public TrainController getTrainController() {	
		TrainController tnc = new TrainController(gui);
		controllers.add(tnc);
		return tnc;
		
	}

}
