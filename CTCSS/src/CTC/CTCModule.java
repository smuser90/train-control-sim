package CTC;

import java.util.ArrayList;

import Simulator.Simulator;

public class CTCModule {
	// Fields
	private CTCPanel gui;
	private Simulator sim;
	
	public CTCModule(Simulator s) {
		gui = new CTCPanel(this);
		sim = s;
	}
		
	public CTCPanel getPanel() {
		return this.gui;
	}
	
	protected void scheduleTrain() {
		sim.scheduleTrain();
	}
}
