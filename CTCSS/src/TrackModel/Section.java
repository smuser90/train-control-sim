package TrackModel;

import java.util.ArrayList;

import panelButtons.PanelButton;

public class Section {
	private ArrayList<Block> bList;
	private String secName;
	private PanelButton pb;
	
	public Section(String n)
	{
		this.secName = n;
		bList = new ArrayList<Block>();
		this.bList = new ArrayList<Block>();
	}
	
	public void addBlock(Block b)
	{
		this.bList.add(b);
	}
	
	public void makeButton(int x, int y) {
		pb = new PanelButton(secName, x, y);
	}
	
	public PanelButton getButton() {
		return pb;
	}
	
	public ArrayList<Block> getBlocks() {
		return bList;
	}
}
