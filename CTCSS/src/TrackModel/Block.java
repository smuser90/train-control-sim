package TrackModel;

import TrackDisplay.BlockListener;
import TrackDisplay.PanelButton;

public class Block{
	private int length; // length of this block
	private int number; // the ID of the block
	private int trainID = 0; // the ID of the train on this block
	private double grade; // the grade of this block
	private int speedLimit; // speed limit on this block
	private boolean occupied = false; // if there is a train on the block or not
	private boolean open = true; // if the block is open or not
	private boolean failure = false; // if this block fails, aka this block is broken!
	private int type; // the type of this block, remember to check that it is either 0, 1, 2, or 3
	private String section;
	private String stationName;
	private boolean crossingUp = true; // if true, the crossing is up, if false then they're down
	private int isBelowGround = 0; // 0 for above ground, 1 for below ground
	private int switchedTo;
	private PanelButton pb;
	// need these to be added to the file
	// also need to make the file work with added elevation plus cumulative elevation
	// negative elevation does not imply that the train is underground
	private double elevation;
	private double cumulativeElevation;
	private Section s;
	private int dirType; // 0 for one direction, 1 for two directions
	/*
	 * in text file:
	 * bID nextB bType bLength bGrade bSpLim bSection belowGround stationName elevation cElevation
	 */
	
	
	/*
	 * remember, blocks with ID 0 are yard blocks
	 * 0 - regular
	 * 1 - switch
	 * 2 - crossing
	 * 3 - station
	 */
	// trainDirection <- do we need this?
	// no default or alt switch dir needed due to the graph implementation
	
	
	// prototype: only int block number
	/*
	public Block(int blockNum)
	{
		this.number = blockNum;
		crossingUp = false;
	}	
	*/
	
	
	
	// final: all this shit
	
	
	public Block(int len, double gr, int bID, int spLim, int ty, String sec, int belGnd, String statName, int swiTo, double ele, double cele, int dType)
	{
		this.number = bID;
		this.type = ty;
		this.length = len;
		this.grade = gr;		
		this.speedLimit = spLim;
		this.section = sec;
		this.isBelowGround = belGnd;
		this.stationName = statName;
		this.switchedTo = swiTo;
		this.elevation = ele;
		this.cumulativeElevation = cele;
		this.dirType = dType;
	}
	
	public void addSection(Section sec) {
		s = sec;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public int getBlockNumber()
	{
		return this.number;
	}
	
	public boolean getFailure()
	{
		return this.failure;
	}
	
	public void setFailure(boolean v)
	{
		this.failure = v;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public boolean getOccupied()
	{
		return this.occupied;
	}
	
	public void setOccupied(boolean g)
	{
		this.occupied = g;
		pb.toggleOccupied(g);
		s.check();
	}
	
	public boolean getCrossing()
	{
		return this.crossingUp;
	}
	
	public void setCrossing(boolean b)
	{
		this.crossingUp = b;
	}
	
	
	
	

	public void setSpeedLimit(int s)
	{
		this.speedLimit = s;
		// print updated speed limit to the log
	}
	
	public int getSpeedLimit()
	{
		return this.speedLimit;
	}
	
	public int blockTrainID()
	{
		return this.trainID;
	}
	
	/*
	 * these methods are not needed, block open/close is equal to block fail/not fail
	public void closeBlock()
	{
		this.open = false;
	}
	
	public void openBlock()
	{
		this.open = true;
	} 
	*/
	public void breakBlock()
	{
		this.failure = true;
	}
	
	public void fixBlock()
	{
		this.failure = false;
	}
	
	public void trainOnBlock(int trainID)
	{
		this.occupied = true;
		this.trainID = trainID;
	}
	
	public void trainOffBlock()
	{
		this.occupied = false;
		this.trainID = 0;
	}
	
	public boolean isUnderground()
	{
		if (isBelowGround == 0)
		{
			return false;
		}
		return true;
	}
	
	public String getStationName()
	{
		return this.stationName;
	}
	
	public int getSwitchedTo()
	{
		return this.switchedTo;
	}
	
	public void setSwitchedTo(int s)
	{
		this.switchedTo = s;
	}
	
	public String getSection()
	{
		return this.section;
	}
	
	public void makeButton(int x, int y)
	{
		pb = new PanelButton(""+this.number, x, y);
		pb.addMouseListener(new BlockListener(pb));
	}
	
	public PanelButton getButton()
	{
		return pb;
	}
	
	public int getDirection()
	{
		return this.dirType;
	}
	
}
