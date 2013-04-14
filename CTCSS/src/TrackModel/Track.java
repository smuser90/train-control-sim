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
            		cLine = new Line(Integer.parseInt(br.readLine()), lineName);
            		index++;
            	} else {
            		String [] lineItms = line.split(" ");
            		if(curBlock == -1) {
            			cLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]));
            			cLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		} else {
            			if(curBlock != Integer.parseInt(lineItms[0])) {
            				cLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]));
            			}
            			cLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		}
            	}
            }
            br.close();
            lines.add(cLine);
            newLine = cLine;
            cLine.print();
			} catch (IOException q)
			{return false;}
		
		return true;
	}
	
	protected Line getNewLine() {
		return newLine;
	}
}
