package TrackModel;

import java.util.ArrayList;

public class Section {
	private ArrayList<Block> bList;
	private String secName;
	
	public Section(String n)
	{
		this.secName = n;
	}
	
	public void addBlock(Block b)
	{
		this.bList.add(b);
	}
}
