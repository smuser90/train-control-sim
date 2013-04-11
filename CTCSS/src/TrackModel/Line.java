// this is the class to hold blocks in a directed graph.
// the TrackModel.java class will hold multiple instances of this, red line green line etc.
package TrackModel;
import java.util.ArrayList;

public class Line {
	int V; // vertices
	int E; // edges
	ArrayList<ArrayList<Integer>> trackAdjList;
	
	public Line(int vertices)
	{
		this.V = vertices;
		this.E = 0;
		trackAdjList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < vertices; i ++)
		{
			trackAdjList.add(new ArrayList<Integer>());
		}
	}
	
	// these will be for testing, to make sure stuff loads I suppose
	protected int V() { return V; }
	protected int E() { return E; }
	
	
	public void addEdge(int v, int w)
	{
		trackAdjList.get(v).add(w);
	}
}
