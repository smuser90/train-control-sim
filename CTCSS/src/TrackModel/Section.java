package TrackModel;

import java.util.ArrayList;

import TrackDisplay.GraphPanel;
import TrackDisplay.PanelButton;
import TrackDisplay.SectionListener;

public class Section {
	private ArrayList<Block> bList;
	private String secName;
	private PanelButton pb;
	private GraphPanel gp;
	private Line blocks;
	private ArrayList<Section> secs;
	
	public Section(String n, Line ln)
	{
		this.secName = n;
		bList = new ArrayList<Block>();
		this.bList = new ArrayList<Block>();
		secs = new ArrayList<Section>();
		blocks = ln;
		for(int x = 0; x < bList.size(); x++)
		{
			for(int y = 0; y < blocks.adj(bList.get(x).getBlockNumber()).size(); y++)
			{
				if(blocks.getBlocks().get(blocks.adj(bList.get(x).getBlockNumber()).get(y)).getSection().equals(secName)) {
					secs.add(blocks.getSection(blocks.getBlocks().get(blocks.adj(bList.get(x).getBlockNumber()).get(y)).getSection()));
				}
			}
		}
	}
	
	public void addBlock(Block b)
	{
		this.bList.add(b);
	}
	
	public void makeButton(int x, int y) {
		pb = new PanelButton(secName, x, y);
		pb.addListener(new SectionListener(pb));
	}
	
	public PanelButton getButton() {
		return pb;
	}
	
	public ArrayList<Block> getBlocks() {
		return bList;
	}
}
