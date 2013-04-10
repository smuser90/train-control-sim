package CTC;

import java.util.ArrayList;

public class CTCModule {
	// Fields
	private CTCPanel gui = null;
	
	public CTCModule() {
		gui = new CTCPanel(this);
	}
		
	public CTCPanel getPanel() {
		return this.gui;
	}
	
	protected ArrayList<Integer> getBlockIDs(int line) {
		return null;
	}
}
