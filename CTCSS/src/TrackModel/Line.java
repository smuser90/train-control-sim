// this is the class to hold blocks in a directed graph, and also hold a list of the related blocks
// the TrackModel.java class will hold multiple instances of this, red line green line etc.
package TrackModel;
import java.util.ArrayList;

import Log.Log;

public class Line {
	int V; // number of vertices
	int E; // edges
	ArrayList<ArrayList<Integer>> trackAdjList; // list of all the vertices and where they link
	ArrayList<Block> blockList; // list of all the blocks and their data
	
	public Line(int numBlocks)
	{
		this.V = numBlocks;
		this.E = 0;
		trackAdjList = new ArrayList<ArrayList<Integer>>();
		blockList = new ArrayList<Block>();
		
		for (int i = 0; i < numBlocks; i ++)
		{
			trackAdjList.add(new ArrayList<Integer>()); // init all of the lists of blocks that each block references
		}
	}
	
	// these will be for testing, to make sure stuff loads I suppose
	protected int V() { return V; }
	protected int E() { return E; }
	
	
	public void addEdge(int v, int w) // adds a one-way reference from this block to another
	{
		trackAdjList.get(v).add(w);
	}
	
	public void addBlock(int bID, int type, int size)
	{
		Block t = new Block(bID);
		t.setType(type);
		t.setSize(size);
		this.blockList.add(t);
		
	}
	
	protected void print() {
		StringBuilder track = new StringBuilder("\n");
		for(int i = 0; i < this.trackAdjList.size(); i++) {
			track.append(i + " : ");
			for(int j = 0; j < this.trackAdjList.get(i).size(); j++) {
				track.append(this.trackAdjList.get(i).get(j) + " ");
			}
			track.append("\n");
		}
		Log.Instance().append(0, track.toString());
	}
	
	public ArrayList<Block> getBlocks() {
		return this.blockList;
	}
}
