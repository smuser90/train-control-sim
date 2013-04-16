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
		if(hasTrack)
			addLine(tr.getNewLine());
	}
	
	public boolean hasTrack() {
		return hasTrack;
	}
	
	public Line gotTrack() {
		hasTrack = false;
		return tr.getNewLine();
	}
	
	private void addLine(Line l) {
		gui.addLine(l);
	}
	
	public void closeBlock(int bNum, String lineName)
	{
		tr.getLine(lineName).getBlock(bNum).closeBlock();
	}
}
