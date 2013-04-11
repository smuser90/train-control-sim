package TrackController;

public class Block {

	boolean crossingActive;
	int blockNumber;
	boolean isSwitch;
	boolean isCrossing;
	boolean occupied;
	boolean failure;
	int type;
	
	public Block(int bn, boolean swi, boolean cros) {
		this.blockNumber = bn;
		this.isSwitch = swi;
		this.isCrossing = cros;
	}

}
