package TrackController;

import java.util.ArrayList;

import TrackModel.Block;
import TrackModel.Line;


public class TCListMaker {
	
	private static boolean[] marked;
	
	public static ArrayList<TrackController> makeTCList(Line myLine){
		int id = 0;
		ArrayList<TrackController> tcList = new ArrayList<TrackController>();
		for(int i = 0; i < myLine.V(); i++){
			marked = new boolean[myLine.V()];
			if(myLine.getBlock(i).getType() == 1) {
				makeBlockList(i, myLine);
				ArrayList<Block> blockList = new ArrayList<Block>();
				for(int j = 0; j < marked.length; j++){
					if(marked[j]){
						blockList.add(myLine.getBlock(j));
					}
				}
				tcList.add(new TrackController(id++, blockList));
			}
		}
		if(tcList.size() == 0)
			tcList.add(new TrackController(id, myLine.getBlocks()));
		return tcList ;
	}
	
	private static void makeBlockList(int blockID, Line myLine){
		marked[blockID] = true;
		for(int b : myLine.adj(blockID)) {
			if(myLine.getBlock(b).getType() != 1 && !marked[b]){
				makeBlockList(b, myLine);
			}
		}
	}
	
}
