/*
 *		Line.java
 *		This is the Line representation of a set of blocks. It holds a list of the blocks in the Line both as
 *		a graph of the block IDs and a list of the actual Block objects themselves.
 *		Author: Kai Manuel
 *		Date Created: 04/07/2013
 *		Date Last Updated: 04/25/2013
 */

// this is the class to hold blocks in a directed graph, and also hold a list of the related blocks
// the TrackModel.java class will hold multiple instances of this, red line green line etc.
package TrackModel;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import TrackDisplay.GraphPanel;
import TrackDisplay.SectionLocator;

import Log.Log;
import TrainModel.TrainModel;

public class Line {
	private int V; // number of vertices
	private int E; // edges
	private ArrayList<ArrayList<Integer>> trackAdjList; // list of all the vertices and where they link
	private ArrayList<Block> blockList; // list of all the blocks and their data
	private String lineName;
	private HashMap<String, Section> sectionList; // list of all the sections <String sectionName, Section s>
	private HashMap<String, ArrayList<String>> sectionGraph; // graph of all the sections, stored like the trackAdjList 
	private GraphPanel panel;
	private ArrayList<String> sectionKeys;
	private TrackModelPanel tmp;
	public Line() {}
	
	public Line(int numBlocks, String lName, TrackModelPanel tmp)
	{
		this.tmp = tmp;
		this.lineName = lName;
		this.V = numBlocks;
		this.E = 0;
		trackAdjList = new ArrayList<ArrayList<Integer>>();
		blockList = new ArrayList<Block>();
		for (int i = 0; i < numBlocks; i ++)
		{
			trackAdjList.add(new ArrayList<Integer>()); // init all of the lists of blocks that each block references
		}
		panel = new GraphPanel();
	}
	
	// these will be for testing, to make sure stuff loads I suppose
	public int V() { return V; }
	protected int E() { return E; }
	
	public GraphPanel getPanel() {
		return panel;
	}
	
	public ArrayList<Integer> adj(int v) {
		return this.trackAdjList.get(v);
	}
	
	
	public void addEdge(int v, int w) // adds a one-way reference from this block to another 
	{
		trackAdjList.get(v).add(w);
	}
	
	public void addBlock(int bID, int type, int len, double grade, int speedLimit, String sect, int belowGnd, String stationName, int sw, double elev, double cElev, int dt)
	{
		// Block(int len, double gr, int bID, int spLim, int ty, String sec, int belGnd, String statName, int switchedTo, double elevation, double cumElevation)
		Block t = new Block(len, grade, bID, speedLimit, type, sect, belowGnd, stationName, sw, elev, cElev, dt);
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
			if (this.blockList.get(i).getType() == 3)
			{
				track.append("Station " + this.blockList.get(i).getStationName());
			}
			if (this.blockList.get(i).getType() == 1)
			{
				track.append("Switch is switched to block " + this.blockList.get(i).getSwitchedTo());
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
	
	protected ArrayList<ArrayList<Integer>> adjList() {
		return this.trackAdjList;
	}
	
	protected void sectionInit()
	{
		//  private ArrayList<ArrayList<Integer>> trackAdjList; // list of all the vertices and where they link
		//	private ArrayList<Block> blockList; // list of all the blocks and their data
		//  private HashMap<String, Section> sectionList; // list of all the sections <String sectionName, Section s>
		//  private HashMap<String, ArrayList<String>> sectionGraph; // graph of all the sections, stored like the trackAdjList
		//  first pass, get a list of sections (and related blocks?)
		//  second pass, set the vertices of the sections
		String curSecName = "";
		sectionKeys = new ArrayList<String>(); // list of section names
		

		sectionList = new HashMap<String, Section>();
		sectionGraph = new HashMap<String, ArrayList<String>>();
		if(sectionList == null)
			sectionList = new HashMap<String, Section>();
		for (int i = 0; i < blockList.size(); i++)
		{
			curSecName = blockList.get(i).getSection();
			// if we have the section already
			if(sectionList.containsKey(curSecName))
			{
				sectionList.get(curSecName).addBlock(blockList.get(i));
			} 
			else
			{
				Section sec = new Section(curSecName, tmp, this);
				sec.addBlock(blockList.get(i));
				sectionKeys.add(curSecName);
				sectionList.put(curSecName, sec);
			}			
		}
		
		for(int i = 0; i < sectionKeys.size(); i++) {
			ArrayList<Block> blocks = sectionList.get(sectionKeys.get(i)).getBlocks();
			ArrayList<String> goesTo = new ArrayList<String>();
			for(int j = 0; j < blocks.size(); j++) {
				for(int k = 0; k < trackAdjList.get(blocks.get(j).getBlockNumber()).size(); k++) {
					if(!goesTo.contains(trackAdjList.get(blocks.get(j).getBlockNumber()).get(k))) {
						goesTo.add(blockList.get(trackAdjList.get(blocks.get(j).getBlockNumber()).get(k)).getSection());
					}
				}
			}
			sectionGraph.put(sectionKeys.get(i), goesTo);
		}
		SectionLocator sl = new SectionLocator(sectionKeys.size(), 600, 190);
		ArrayList<Point2D.Double> points = sl.getPoints();
		for(int i = 0; i < sectionKeys.size(); i++) {
			sectionList.get(sectionKeys.get(i)).makeButton(new Double(points.get(i).getX()).intValue() , new Double(points.get(i).getY()).intValue());
			this.panel.addButton(sectionList.get(sectionKeys.get(i)).getButton());
			
		}
		for(int i = 0; i < sectionKeys.size(); i++) {
			for(int j = 0; j < sectionGraph.get(sectionKeys.get(i)).size(); j++) {
				this.panel.addConnection(i, sectionKeys.indexOf(sectionGraph.get(sectionKeys.get(i)).get(j)));
				sectionList.get(sectionKeys.get(i)).addGoing(sectionList.get(sectionGraph.get(sectionKeys.get(i)).get(j)));
				sectionList.get(sectionGraph.get(sectionKeys.get(i)).get(j)).addComing(sectionList.get(sectionKeys.get(i)));
				//System.out.print(sectionGraph.get(sectionKeys.get(i)).get(j) + " " + sectionKeys.indexOf(sectionGraph.get(sectionKeys.get(i)).get(j)));
			}
			sectionList.get(sectionKeys.get(i)).makePanel();
			//System.out.println();
		}
	}
	
	public Section getSection(String name) {
		return sectionList.get(name);
	}
	/*
	protected void addSection(String secName)
	{
		// give this a list of blocks and section name?
		
		// create Section, then add it to sectionList
		Section s = new Section(secName);
		this.sectionList.put(secName, s);
	}
	*/
}
