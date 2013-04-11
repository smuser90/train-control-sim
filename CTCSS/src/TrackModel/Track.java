// this will use the Line and Block classes to create the Red and Green Lines 
package TrackModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Log.Log;

public class Track {
	
	private Line gLine;
	
	public boolean getLineFile(File f)
	{
		Log.Instance().append(1, "Track File: " + f.getName() + " selected\n");
		try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            int index = 0;
            int numBlocks = 0;
            int curBlock = -1;
            while ((line = br.readLine()) != null) {
            	if (index == 0)
            	{
            		gLine = new Line(Integer.parseInt(line));
            		index++;
            	} else {
            		String [] lineItms = line.split(" ");
            		if(curBlock == -1) {
            			gLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]));
            			gLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		} else {
            			if(curBlock != Integer.parseInt(lineItms[0])) {
            				gLine.addBlock(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[2]) , Integer.parseInt(lineItms[3]));
            			}
            			gLine.addEdge(Integer.parseInt(lineItms[0]), Integer.parseInt(lineItms[1]));
            			curBlock = Integer.parseInt(lineItms[0]);
            		}
            	}
            }
            br.close();
			} catch (IOException q)
			{return false;}
		gLine.print();
		return true;
	}
	
	public ArrayList<Block> getGLine() {
		return this.gLine.getBlocks();
	}
}
