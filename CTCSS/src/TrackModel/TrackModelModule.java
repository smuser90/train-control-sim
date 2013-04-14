package TrackModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrackModelModule {
	private TrackModelPanel gui = null;
	private Track tr;
	private boolean hasTrack = false;
	
	public TrackModelModule() 
	{
		gui = new TrackModelPanel(this);
		tr = new Track();
	}
		
	public TrackModelPanel getPanel() {
		return gui;
	}
	
	public void getLineFile(File f)
	{
		hasTrack = tr.getLineFile(f);
		addLine(tr.getNewLine());
	}
	
	public boolean hasTrack() {
		return hasTrack;
	}
	
	public void gotTrack() {
		hasTrack = false;
	}
	
	private void addLine(Line l) {
		gui.addLine(l);
	}
}
