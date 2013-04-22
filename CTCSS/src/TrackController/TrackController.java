package TrackController;

import java.util.ArrayList;

import TrackModel.Block;
import TrackModel.Line;

public class TrackController {
	private int blocks;
	private int number;
	private int track;
	private int trains;
	protected boolean isChanged = false;
	protected ArrayList<Block> blocksControlled = new ArrayList<Block>();
	protected ArrayList<Integer> brokenRails;
	protected ArrayList<Block> crossingsControlled = new ArrayList<Block>();
	protected ArrayList<Block> switchesControlled = new ArrayList<Block>();
	protected String line;
	protected ArrayList<Block> trainsControlled = new ArrayList<Block>();

	static int index;
	static int index_2;
	
	public TrackController(int tcIndex, ArrayList<Block> controlled, String lineName){
		this.blocksControlled = controlled;
		this.number = tcIndex;
		brokenRails = new ArrayList<Integer>();
		crossingsControlled = buildCrossingList(controlled);
		switchesControlled = buildSwitchList(controlled);
		line = lineName;
	}
	
	//DOESNT HANDLE IF THERE ARE NO CROSSINGS
	public ArrayList<Block> buildCrossingList(ArrayList<Block> blocks){
		ArrayList<Block> tempCrossingList = new ArrayList<Block>();
		for(int x = 0; x < blocks.size(); x++)
		{
			if(blocks.get(x).getType() == 2){
				tempCrossingList.add(blocks.get(x));
			}
		}
		return tempCrossingList;
	}
	
	//DOESNT HANDLE IF THERE ARE NO SWITCHES
	public ArrayList<Block> buildSwitchList(ArrayList<Block> blocks){
		ArrayList<Block> tempSwitchList = new ArrayList<Block>();
		for(int x = 0; x < blocks.size(); x++)
		{
			if(blocks.get(x).getType() == 1){
				tempSwitchList.add(blocks.get(x));
			}
		}
		return tempSwitchList;
	}
	
	public static void reportBrokenRail(int blockNumber) {
		//reportBrokenRailToCTC(blockNumber);
	}
	
	public static void runPLC(ArrayList<ArrayList<TrackController>> trackControllerList, ArrayList<Line> lines){
		for(index = 0; index < trackControllerList.size(); index++)
		{
			for(index_2 = 0; index_2 < trackControllerList.get(index).size(); index_2++)
			{	
				PLC.runPLC(trackControllerList.get(index).get(index_2), lines.get(index).getBlocks());
			}
		}
	}
	
	public void setNumTrains(int numTrains) {
		trains = numTrains;
	}
	
	public int getNumTrains() {
		return trains;
	}
	
	public void setNumBlocks(int numBlocks) {
		blocks = numBlocks;
	}
	
	public int getNumBlocks() {
		return this.blocksControlled.size();
	}
	
	public int getTrackNum() {
		return 0;
	}
	
	public int getID() {
		return number;
	}
	
	public void setBrokenRail(ArrayList<Integer> brokenRail){
		brokenRails = brokenRail;
	}
	
	public ArrayList<Integer> getBrokenRail(){
		return brokenRails;
	}
	
	public String getLine(){
		return line;
	}
	
	public void setTrainsControlled(ArrayList<Block> _trainsControlled)
	{
		trainsControlled = _trainsControlled;
	}
	
	public ArrayList<Block> getTrainsControlled()
	{
		return trainsControlled;
	}
}