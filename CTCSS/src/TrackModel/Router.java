/*
 * Router.java
 * Class for routing trains
 * Author: Nikolas Parshook
 * Date Created: 04/18/2013
 * Date Last Updated: 04/21/2013
 */

package TrackModel;

import java.util.ArrayList;

import TrainModel.TrainModel;

/**
 * Class for routing trains
 * 
 * @author Nikolas Parshook
 * 
 */
public class Router
{
	private static Line line;
	private static ArrayList<Block> blocks;
	private static boolean[] marked;
	private static boolean[] marked2;
	private static boolean done = false;

	/* Route a train */
	protected static void route(TrainModel train, int start, int end, Line l)
	{
		line = l;
		blocks = line.getBlocks();
		ArrayList<Block> r = new ArrayList<Block>();
		marked = new boolean[blocks.size()];
		marked2 = new boolean[blocks.size()];
		done = false;
		// System.out.println(start);
		route(r, start, end);
		if (done)
			train.setRouteInfo(r);
		else if (!done && blocks.get(start).getType() == 3)
		{
			r.add(blocks.get(start));
			train.setRouteInfo(r);
		}
	}

	/* recursive DFS for routing */
	private static void route(ArrayList<Block> r, int start, int end)
	{
		// System.out.println("Start:" + start);
		if ((!marked[start] || (blocks.get(start).getDirection() == 1 && !marked2[start]))
				&& !blocks.get(start).getFailure())
		{
			if (marked[start])
			{
				marked2[start] = true;
			}
			else
			{
				marked[start] = true;
			}
			if (start == end)
				done = true;
			r.add(blocks.get(start));
			for (Integer i : line.adj(start))
			{
				if (done)
					break;
				route(r, i, end);
			}
			if (!done)
			{
				r.remove(r.size() - 1);
			}
		}
	}
}
