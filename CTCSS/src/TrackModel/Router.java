package TrackModel;

import java.util.ArrayList;

import TrainModel.TrainModel;

public class Router {
	private static Line line;
	private static ArrayList<Block> blocks;
	private static boolean[] marked;
	private static boolean done = false;
	
	protected static void route(TrainModel train, int start, int end, Line l) {
		line = l;
		blocks = line.getBlocks();
		ArrayList<Block> r = new ArrayList<Block>();
		marked = new boolean[blocks.size()];
		done = false;
		route(r, start, end);
		if(done)
			train.setRouteInfo(r);
		else if(blocks.get(start).getType() == 3)
			train.setRouteInfo(null);
	}
	
	private static void route(ArrayList<Block> r, int start, int end) {
		if(!marked[start] && !blocks.get(start).getFailure()) {
			marked[start] = true;
			if(start == end) 
				done = true;
			r.add(blocks.get(start));
			for(Integer i : line.adj(start)) {
				if(done)
					break;
				route(r, i, end);
			}
			if(!done) {
				r.remove(r.size() - 1);
			}
		}
	}
}
