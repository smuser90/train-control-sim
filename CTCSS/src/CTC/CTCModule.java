package CTC;

public class CTCModule {
	private CTCPanel gui = null;
	
	public CTCModule() {
		gui = new CTCPanel();
	}
		
	public CTCPanel getPanel() {
		return gui;
	}
}
