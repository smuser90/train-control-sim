package TrackModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import TrackDisplay.GraphPanel;
import TrackDisplay.PanelButton;
import TrackDisplay.SectionListener;
import TrackDisplay.SectionLocator;

public class Section {
	private ArrayList<Block> bList;
	private String secName;
	private PanelButton pb;
	private GraphPanel gp;
	private Line blocks;
	private ArrayList<Section> secs;
	private TrackModelPanel tmp;
	
	public Section(String n, TrackModelPanel tmp, Line ln)
	{
		this.secName = n;
		this.tmp = tmp;
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
		pb.addListener(new SectionListener(pb, tmp, this));
	}
	
	public PanelButton getButton() {
		return pb;
	}
	
	public ArrayList<Block> getBlocks() {
		return bList;
	}
	
	public void makePanel()
	{
		gp = new GraphPanel();
		SectionLocator sl = new SectionLocator(bList.size(), 600, 190);
		ArrayList<Point2D.Double> points = sl.getPoints();
		for(int i = 0; i < bList.size(); i++)
		{
			System.out.println(bList.get(i).getBlockNumber());
			bList.get(i).makeButton(new Double(points.get(i).getX()).intValue() , new Double(points.get(i).getY()).intValue());
			gp.addButton(bList.get(i).getButton());
		}

		for(int i = 0; i < bList.size()-1; i++)
		{
			gp.addConnection(i, i+1);
		}
		
		JButton but = new JButton("Back");
		but.setBounds(10, 170, 70, 30);
		but.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent x) {
				tmp.setPanel(blocks.getPanel(), blocks.getName());
			}
		});
		gp.add(but);
	}
	
	public GraphPanel getPanel()
	{
		return gp;
	}
	
	public String getName() 
	{
		return this.secName;
	}
}
