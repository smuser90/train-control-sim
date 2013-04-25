/*
 *		Section.java
 *		This is the Section representation of the loaded Line. The Sections are groups of blocks that
 *		are part of the same section.		
 *		Author: Kai Manuel
 *		Date Created: 04/17/2013
 *		Date Last Updated: 04/21/2013
 */

package TrackModel;

import java.awt.Rectangle;
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

public class Section
{
	private ArrayList<Block> bList;
	private String secName;
	private PanelButton pb;
	private ArrayList<PanelButton> pbs;
	private GraphPanel gp;
	private Line blocks;
	private ArrayList<Section> secsComing;
	private ArrayList<Section> secsGoing;
	private TrackModelPanel tmp;

	/**
	 * Constructor for the Section object.
	 * 
	 * @param n
	 * @param tmp
	 * @param ln
	 */
	public Section(String n, TrackModelPanel tmp, Line ln)
	{
		this.secName = n;
		this.tmp = tmp;
		bList = new ArrayList<Block>();
		this.bList = new ArrayList<Block>();
		secsComing = new ArrayList<Section>();
		secsGoing = new ArrayList<Section>();
		pbs = new ArrayList<PanelButton>();
		blocks = ln;
	}

	/**
	 * Adds a block to the Section
	 * 
	 * @param Block
	 */
	public void addBlock(Block b)
	{
		this.bList.add(b);
		b.addSection(this);
	}

	/**
	 * Checks to see if it's occupied
	 */
	public void check()
	{
		for (int i = 0; i < bList.size(); i++)
		{
			if (bList.get(i).getOccupied())
			{
				for (int j = 0; j < pbs.size(); j++)
				{
					pbs.get(j).toggleOccupied(true);
				}
				return;
			}
		}
		for (int j = 0; j < pbs.size(); j++)
		{
			pbs.get(j).toggleOccupied(false);
		}
	}

	/**
	 * Makes a new Panel Button
	 * 
	 * @param x
	 * @param y
	 */
	public void makeButton(int x, int y)
	{
		pb = new PanelButton(secName, x, y);
		pbs.add(pb);
		pb.addListener(new SectionListener(pb, tmp, this));
	}

	/**
	 * Gets the button at the x, y coordinates given to the method
	 * 
	 * @param x
	 * @param y
	 * @return PanelButton
	 */
	public PanelButton getButton(int x, int y)
	{
		PanelButton p = new PanelButton(secName, x, y);
		pbs.add(p);
		p.addListener(new SectionListener(p, tmp, this));
		return p;
	}

	/**
	 * Gets the button
	 * 
	 * @return
	 */
	public PanelButton getButton()
	{
		return pb;
	}

	/**
	 * Returns a list of the Blocks in the section
	 * 
	 * @return ArrayList<Block>
	 */
	public ArrayList<Block> getBlocks()
	{
		return bList;
	}

	/**
	 * This makes the Panel fot this section
	 */
	public void makePanel()
	{
		gp = new GraphPanel();
		SectionLocator sl = new SectionLocator(bList.size() + secsGoing.size(),
				600, 190);
		ArrayList<Point2D.Double> points = sl.getPoints();
		int butNum = 0;
		for (int i = 0; i < bList.size(); i++)
		{
			bList.get(i).makeButton(
					new Double(points.get(i).getX()).intValue(),
					new Double(points.get(i).getY()).intValue());
			gp.addButton(bList.get(i).getButton());
			butNum = i;
		}

		System.out.println(this.secName
				+ "*************************************************");

		for (int i = 0; i < secsGoing.size(); i++)
		{
			butNum++;
			System.out.println(secsGoing.get(i).getName());
			gp.addButton(secsGoing.get(i).getButton(
					new Double(points.get(butNum).getX()).intValue(),
					new Double(points.get(butNum).getY()).intValue()));

		}

		for (int i = 0; i < secsComing.size(); i++)
		{
			butNum++;
			System.out.println(secsComing.get(i).getName());
			gp.addButton(secsComing.get(i).getButton(
					new Double(points.get(butNum).getX()).intValue(),
					new Double(points.get(butNum).getY()).intValue()));

		}

		for (int i = 0; i < bList.size() - 1; i++)
		{
			gp.addConnection(i, i + 1);
		}

		JButton but = new JButton("Back");
		but.setBounds(10, 170, 70, 30);
		but.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent x)
			{
				tmp.setPanel(blocks.getPanel(), blocks.getName());
			}
		});
		gp.add(but);
		for (int i = 0; i < bList.size(); i++)
		{
			bList.get(i).getButton().setTMP(tmp);
		}
	}

	/**
	 * Returns this panel
	 * 
	 * @return GraphPanel
	 */
	public GraphPanel getPanel()
	{
		return gp;
	}

	/**
	 * Returns the name of this section
	 * 
	 * @return String
	 */
	public String getName()
	{
		return this.secName;
	}

	/**
	 * Add a section incoming to this section
	 * 
	 * @param s
	 */
	public void addComing(Section s)
	{
		if (!s.getName().equals(secName))
			secsComing.add(s);
	}

	/**
	 * Add a section that this section is going to
	 * 
	 * @param s
	 */
	public void addGoing(Section s)
	{
		if (!s.getName().equals(secName))
			secsGoing.add(s);
	}

	/**
	 * returns the blocks in this section
	 * 
	 * @return Line
	 */
	public Line getLine()
	{
		return this.blocks;
	}
}
