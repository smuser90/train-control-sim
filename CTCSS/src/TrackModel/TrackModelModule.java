package TrackModel;

public class TrackModelModule {
private TrackModelPanel gui = null;
	
	public TrackModelModule() {
		gui = new TrackModelPanel();
	}
		
	public TrackModelPanel getPanel() {
		return gui;
	}
}
