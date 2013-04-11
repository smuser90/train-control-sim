package TrackModel;

public class Block {
	private int length; // length of this block
	private int number; // the ID of the block
	private int trainID; // the ID of the train on this block
	private double grade; // the grade of this block
	private int speedLimit; // speed limit on this block
	private boolean occupied; // if there is a train on the block or not
	private boolean open; // if the block is open or not
	private boolean failure; // if this section fails
	private int type; // the type of this block, remember to check that it is either 0, 1, 2, or 3
	/*
	 * 0 - regular
	 * 1 - switch
	 * 2 - crossing
	 * 3 - station
	 */
	// trainDirection <- do we need this?
	// no default or alt switch dir needed due to the graph implementation
	
	public Block(int len, double gr, int bID, int spLim, int t)
	{
		this.length = len;
		this.grade = gr;
		this.failure = false;
		this.number = bID;  
		this.occupied = false;
		this.open = true;
		this.speedLimit = spLim; 
		this.trainID = 0 ;
		this.type = t;
	}
	
	public void setSpeedLimit(int s)
	{
		this.speedLimit = s;
		// print updated speed limit to the log
	}
	
	public int blockTrainID()
	{
		return this.trainID;
	}
	
	public void closeBlock()
	{
		this.open = false;
	}
	
	public void openBlock()
	{
		this.open = true;
	}
	
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
	
	
	
	
}
