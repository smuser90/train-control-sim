/*
 * TC List Maker
 * Makes the list of track controllers and which blocks they control
 * Author: Zachary Shelhamer
 * Date Created: 4/10/2013
 * Date Last Updated: 4/14/2013
 */

package TrackController;

import java.util.ArrayList;

import TrackModel.Block;
import TrackModel.Line;

/**
 * Makes the list of track controllers and which blocks they control
 * 
 * @author Zachary Shelhamer
 * 
 */
public class TCListMaker
{

	private static boolean[] marked;

	/**
	 * Create the List of track controllers
	 * 
	 * @param myLine
	 *            Line to create track controllers for
	 * @return List of Track Controllers
	 */
	public static ArrayList<TrackController> makeTCList(Line myLine)
	{
		int id = 0;
		ArrayList<TrackController> tcList = new ArrayList<TrackController>();
		for (int i = 0; i < myLine.V(); i++)
		{
			marked = new boolean[myLine.V()];
			if (myLine.getBlock(i).getType() == 1)
			{
				makeBlockList(i, myLine);
				ArrayList<Block> blockList = new ArrayList<Block>();
				for (int j = 0; j < marked.length; j++)
				{
					if (marked[j])
					{
						blockList.add(myLine.getBlock(j));
					}
				}
				tcList.add(new TrackController(id++, blockList, myLine
						.getName()));
			}
		}
		if (tcList.size() == 0)
			tcList.add(new TrackController(id, myLine.getBlocks(), myLine
					.getName()));
		return tcList;
	}

	private static void makeBlockList(int blockID, Line myLine)
	{
		marked[blockID] = true;
		for (int b : myLine.adj(blockID))
		{
			if (myLine.getBlock(b).getType() != 1 && !marked[b])
			{
				makeBlockList(b, myLine);
			}
		}
	}

}
