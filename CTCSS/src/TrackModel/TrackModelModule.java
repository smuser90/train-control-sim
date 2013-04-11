package TrackModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrackModelModule {
	private TrackModelPanel gui = null;
	private Track tr;
	
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
		tr.getLineFile(f);
	}
	
	public ArrayList<Block> getGLine() {
		return this.tr.getGLine();
	}
}
