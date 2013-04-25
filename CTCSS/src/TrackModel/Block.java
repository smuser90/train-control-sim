/*
 * Block.java
 * This is the object representation for the Blocks on the line.
 * Author: Kai Manuel
 * Date Created: 04/07/2013
 * Date Last Updated: 4/25/2013  
 */
package TrackModel;

import TrackDisplay.BlockListener;
import TrackDisplay.PanelButton;

public class Block
{
	private int length; // length of this block
	private int number; // the ID of the block
	private int trainID = 0; // the ID of the train on this block
	private double grade; // the grade of this block
	private int speedLimit; // speed limit on this block
	private boolean occupied = false; // if there is a train on the block or not
	private boolean open = true; // if the block is open or not
	private boolean failure = false; // if this block fails, aka this block is
										// broken!
	private int type; // the type of this block, remember to check that it is
						// either 0, 1, 2, or 3
	private String section;
	private String stationName;
	private boolean crossingUp = true; // if true, the crossing is up, if false
										// then they're down
	private int isBelowGround = 0; // 0 for above ground, 1 for below ground
	private int switchedTo;
	private PanelButton pb;
	// need these to be added to the file
	// also need to make the file work with added elevation plus cumulative
	// elevation
	// negative elevation does not imply that the train is underground
	private double elevation;
	private double cumulativeElevation;
	private Section s;
	private int dirType; // 0 for one direction, 1 for two directions

	/*
	 * Text File organization bID nextB bType bLength bGrade bSpLim bSection
	 * belowGround stationName elevation cElevation dirType
	 */

	/*
	 * remember, blocks with ID 0 are yard blocks 0 - regular 1 - switch 2 -
	 * crossing 3 - station
	 */

	// no default or alt switch dir needed due to the graph implementation

	/**
	 * Constructor for Block object.
	 * 
	 * @param len
	 * @param gr
	 * @param bID
	 * @param spLim
	 * @param ty
	 * @param sec
	 * @param belGnd
	 * @param statName
	 * @param swiTo
	 * @param ele
	 * @param cele
	 * @param dType
	 */
	public Block(int len, double gr, int bID, int spLim, int ty, String sec,
			int belGnd, String statName, int swiTo, double ele, double cele,
			int dType)
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

	/**
	 * Method to add a section to the block.
	 * 
	 * @param sec
	 */
	public void addSection(Section sec)
	{
		s = sec;
	}

	/**
	 * Returns the length of this Block.
	 * 
	 * @return
	 */
	public int getLength()
	{
		return this.length;
	}

	/**
	 * Returns the Block ID(Number) of the block.
	 * 
	 * @return int
	 */
	public int getBlockNumber()
	{
		return this.number;
	}

	/**
	 * Returns the failure status of the block
	 * 
	 * @return boolean
	 */
	public boolean getFailure()
	{
		return this.failure;
	}

	/**
	 * Sets the block to broken or fixed
	 * 
	 * @param v
	 */
	public void setFailure(boolean v)
	{
		this.failure = v;
	}

	/**
	 * Returns the type of the block
	 * 
	 * @return int
	 */
	public int getType()
	{
		return this.type;
	}

	/**
	 * Returns true if this block is occupied by a train
	 * 
	 * @return boolean
	 */
	public boolean getOccupied()
	{
		return this.occupied;
	}

	/**
	 * Sets this block's occupied status.
	 * 
	 * @param g
	 */
	public void setOccupied(boolean g)
	{
		this.occupied = g;
		pb.toggleOccupied(g);
		s.check();
	}

	/**
	 * Returns the status of the crossing.
	 * 
	 * @return boolean
	 */
	public boolean getCrossing()
	{
		return this.crossingUp;
	}

	/**
	 * Sets the crossing to up or down
	 * 
	 * @param b
	 */
	public void setCrossing(boolean b)
	{
		this.crossingUp = b;
	}

	/**
	 * Sets the speedLimit
	 * 
	 * @param s
	 */
	public void setSpeedLimit(int s)
	{
		this.speedLimit = s;
		// print updated speed limit to the log
	}

	/**
	 * Gets the Speed Limit of the block
	 * 
	 * @return int
	 */
	public int getSpeedLimit()
	{
		return this.speedLimit;
	}

	/**
	 * Returns the ID of the train currently on the block
	 * 
	 * @return
	 */
	public int blockTrainID()
	{
		return this.trainID;
	}

	/*
	 * these methods are not needed, block open/close is equal to block fail/not
	 * fail public void closeBlock() { this.open = false; }
	 * 
	 * public void openBlock() { this.open = true; }
	 */

	/**
	 * This will break the block
	 */
	public void breakBlock()
	{
		this.failure = true;
	}

	/**
	 * This will fix the block
	 */
	public void fixBlock()
	{
		this.failure = false;
	}

	/**
	 * Sets this block to be occupied and gives the block the trainID
	 * 
	 * @param trainID
	 */
	public void trainOnBlock(int trainID)
	{
		this.occupied = true;
		this.trainID = trainID;
	}

	/**
	 * Sets this block to be cleared, occupancy is false and trainID is 0
	 */
	public void trainOffBlock()
	{
		this.occupied = false;
		this.trainID = 0;
	}

	/**
	 * Lets you know if the train is above or below ground
	 * 
	 * @return boolean
	 */
	public boolean isUnderground()
	{
		if (this.isBelowGround == 0)
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns the station name of this block if this is a station block
	 * 
	 * @return
	 */
	public String getStationName()
	{
		return this.stationName;
	}

	/**
	 * Returns what this block(if it is a switch block) is switched to
	 * 
	 * @return int
	 */
	public int getSwitchedTo()
	{
		return this.switchedTo;
	}

	/**
	 * Set this block switched to the ID passed to it.
	 * 
	 * @param s
	 */
	public void setSwitchedTo(int s)
	{
		this.switchedTo = s;
	}

	/**
	 * Returns the Section that this is a part of
	 * 
	 * @return String
	 */
	public String getSection()
	{
		return this.section;
	}

	/**
	 * makes a button out of this Block with the parameters given to it
	 * 
	 * @param x
	 * @param y
	 */
	public void makeButton(int x, int y)
	{
		pb = new PanelButton("" + this.number, x, y);
		pb.addMouseListener(new BlockListener(pb));
		pb.setBlock(this);
	}

	/**
	 * Returns the button held by this block
	 * 
	 * @return PanelButton
	 */
	public PanelButton getButton()
	{
		return pb;
	}

	/**
	 * Returns 0 if this track allows trains to go in one direction, or 1 for
	 * two ways
	 * 
	 * @return
	 */
	public int getDirection()
	{
		return this.dirType;
	}

	/**
	 * returns the grade of this block
	 * 
	 * @return
	 */
	public double getGrade()
	{
		return this.grade;
	}

}
