// this is the class to hold blocks in a directed graph, and also hold a list of the related blocks
// the TrackModel.java class will hold multiple instances of this, red line green line etc.
package TrackModel;
import java.util.ArrayList;

import Log.Log;

public class Line {
	private int V; // number of vertices
	private int E; // edges
	private ArrayList<ArrayList<Integer>> trackAdjList; // list of all the vertices and where they link
	private ArrayList<Block> blockList; // list of all the blocks and their data
	private String lineName;
	
	public Line() {}
	
	public Line(int numBlocks, String lName)
	{
		this.lineName = lName;
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
	public int V() { return V; }
	
	public ArrayList<Integer> adj(int v) {
		return this.trackAdjList.get(v);
	}
	protected int E() { return E; }
	
	
	public void addEdge(int v, int w) // adds a one-way reference from this block to another
	{
		trackAdjList.get(v).add(w);
	}
	
	public void addBlock(int bID, int type, int len, double grade, int speedLimit, String sect)
	{
		// Block(int len, double gr, int bID, int spLim, int ty, String sec)
		Block t = new Block(len, grade, bID, speedLimit, type, sect);
		this.blockList.add(t);
		
	}
	
	protected void print() {
		StringBuilder track = new StringBuilder("\n");
		track.append(this.lineName + "\n");
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
	
	public String getName() {
		return this.lineName;
	}
	
	public Block getBlock(int blockID){
		return this.blockList.get(blockID);
	}
}
