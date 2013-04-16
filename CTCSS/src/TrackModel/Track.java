// this will use the Line and Block classes to create the Red and Green Lines 
package TrackModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Log.Log;

public class Track {
	
	private ArrayList<Line> lines;
	private Line newLine;
	public Track()
	{
		lines = new ArrayList<Line>();
	}
	
	public boolean getLineFile(File f)
	{
		Log.Instance().append(1, "Track File: " + f.getName() + " selected\n");
		boolean goodLine = true;
		try
		{
			Line cLine = new Line();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String lineName;
            int index = 0;
            int numBlocks = 0;
            int curBlock = -1;
            while ((line = br.readLine()) != null) {
            	if (index == 0)
            	{
            		lineName = line; // get name of the line from the file
            		// check the name of the line. If it exists GTFO 
            		if (checkLineName(lineName))
            		{
            			cLine = new Line(Integer.parseInt(br.readLine()), lineName);
                		index++;
            		} else {
            			goodLine = false;
            			Log.Instance().append(3, "Line already exists\n");
            			break;
            		}
            	} else {
            		String [] lineItms = line.split(" ");
            		if(curBlock == -1) {
            			// addBlock(int bID, int type, int len, double grade, int speedLimit, String sect, int belowGnd, String stationName)
            			cLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]) , Double.parseDouble(lineItms[4]) , Integer.parseInt(lineItms[5]) , lineItms[6] , Integer.parseInt(lineItms[7]) , lineItms[8] );
            			cLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		} else {
            			if(curBlock != Integer.parseInt(lineItms[0])) {
            				cLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]) , Double.parseDouble(lineItms[4]) , Integer.parseInt(lineItms[5]) , lineItms[6] , Integer.parseInt(lineItms[7]) , lineItms[8] );
            				//cLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]) , Double.parseDouble(lineItms[4]) , Integer.parseInt(lineItms[5]) , lineItms[6] );
            			}
            			cLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		}
            	}
            }
            br.close();
            if(goodLine) {
	            lines.add(cLine);
	            newLine = cLine;
	            cLine.print();
            }
			} catch (IOException q)
			{return false;}
		
		return goodLine;
	}
	
	private boolean checkLineName(String lName) {
		// make sure that the track wasn't already loaded
		for(int i = 0; i < lines.size(); i++)
		{
			if(lName.equals(lines.get(i).getName()))
			{
				return false;
			}
		}
		return true;
	}

	protected Line getNewLine() {
		return newLine;
	}
	
	public Line getLine(String lName)
	{
		for(int i = 0; i < lines.size(); i++)
		{
			if(lName.equals(lines.get(i).getName()))
			{
				return lines.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Line> getLines() {
		return lines;
	}
}
