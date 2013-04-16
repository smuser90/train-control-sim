package TrackController;

import TrackModel.Block;
import TrackModel.Line;
/*************************************
 * For block.type
 * 0 regular
 * 1 switch
 * 2 crossing
 */

import java.util.ArrayList;

public class TrackControllerModule {
	
	private TrackControllerPanel currentPanel;
	private ArrayList<TrackController> trackControllerList;
	private ArrayList<Block> myBlocks;//new ArrayList<Block>();
	private ArrayList<Integer> switchList;
	private ArrayList<Integer> trainList;
	private ArrayList<Integer> crossingList;
	private int upperLimit;
	private int lowerLimit;
	private int index = 1;
	private int count = 0;
	private boolean hasTrack = false;
	/**
	 * Create the frame.
	 */
	public TrackControllerModule() {
		myBlocks = new ArrayList<Block>();
		switchList = new ArrayList<Integer>();
		trainList = new ArrayList<Integer>();
		crossingList = new ArrayList<Integer>();
		trackControllerList = new ArrayList<TrackController>();
		currentPanel = new TrackControllerPanel(this);
	}
	/************************************************************************************************
	 CALL THIS TO WAKE ME UP************************************************************************/
	public void getTrack(Line track){
		myBlocks = track.getBlocks();
		for (int listCount = 0; listCount < myBlocks.size(); listCount++) {
			if (myBlocks.get(listCount).getType() == 1) {
				switchList.add(listCount);
<<<<<<< OURS
=======
				//System.out.println("Switch on block: " + listCount);
>>>>>>> THEIRS
			}
			if (myBlocks.get(listCount).getOccupied()) {
				trainList.add(listCount);
			}
			if (myBlocks.get(listCount).getType() == 2)
				crossingList.add(listCount);
		}
		trackControllerList = TCListMaker.makeTCList(track);
		// Set some of the blocks as switches *TESTING*
//		myBlocks.get(8).isSwitch = true;
//		myBlocks.get(17).isSwitch = true;
//		myBlocks.get(25).isSwitch = true;
//		myBlocks.get(41).isSwitch = true;
//		myBlocks.get(53).isSwitch = true;
//		myBlocks.get(70).isSwitch = true;
//		myBlocks.get(84).isSwitch = true;
//		myBlocks.get(92).isSwitch = true;

		// Add some trains *TESTING*
		// myBlocks.get(85).occupied = true;
//		myBlocks.get(16).occupied = true;
//		myBlocks.get(53).occupied = true;
//		myBlocks.get(99).occupied = true;
//		myBlocks.get(85).occupied = true;
		
		/***********************************************************************************
		 * GET BLOCKS HERE 
		 ***********************************************************************************/
		//myBlocks = TrackControllerTester.getBlockList();
		
		
		//myBlocks.get(0).occupied = true;
		// Create lists depending on the block properties
		
		
		//TrackControllerPanel.test3();
		// Here we go through and create all the track controllers and assign
		// them their blocks
//		if (switchList.size() != 0) {
//			for (index = 0; index < switchList.size(); index++) {
//				TrackController tc = new TrackController(index + 1, 0, 0, 0);
//
//				if (index == 0 && index != switchList.size() - 1) {
//					upperLimit = switchList.get(switchList.size() - 1);
//					lowerLimit = (switchList.get((index + 1)));
//					//lowerLimit = (switchList.get((index)));
//					for (count = 0; count < lowerLimit; count++) {
//						tc.blocksControlled.add(count);
//					}
//
//					//for (count = upperLimit + 1; count < 101; count++) {
//					//	tc.blocksControlled.add(count);
//					//}
//				}
//
//				else if (index == switchList.size() - 1) {
//					lowerLimit = switchList.get(0);
//					//upperLimit = switchList.get(index - 1);
//					upperLimit = switchList.get(index);
//
//					for (count = 0; count < lowerLimit + 1; count++) {
//						tc.blocksControlled.add(count);
//					}
//
//					for (count = upperLimit + 1; count < myBlocks.size(); count++) {
//						tc.blocksControlled.add(count);
//					}
//				}
//
//				else {
//					upperLimit = switchList.get(index + 1);
//					lowerLimit = switchList.get(index - 1);
//					for (count = lowerLimit + 1; count < upperLimit; count++)
//						tc.blocksControlled.add(count);
//				}
//				tc.setNumBlocks(tc.blocksControlled.size());
//				trackControllerList.add(tc);
//			}
//		}
//		else{
//			TrackController tc = new TrackController(1, 0, 0, 0);
//			for(count = 0; count < myBlocks.size(); count++){
//				tc.blocksControlled.add(count);
//				//tc.blocksControlled.add(count + 1);
//			}
//			tc.setNumBlocks(tc.blocksControlled.size());
//			trackControllerList.add(tc);
//		}
		hasTrack = true;
		PLC.setup(myBlocks, crossingList);
		currentPanel.displayChange();

		// For the initial list, set up any trains that are placed on the track
//		for (index = 0; index < trackControllerList.size(); index++) {
//			for (count = 0; count < trainList.size(); count++) {
//				if (trainList.get(count) == 100
//						&& trackControllerList.get(index).blocksControlled
//								.contains(100)) {
//					trackControllerList.get(index).trains++;
//					break;
//				}
//				if (trackControllerList.get(index).blocksControlled
//						.contains(trainList.get(count))) {
//					trackControllerList.get(index).trains++;
//				}
//			}
//		}
		//if(currentPanel != null)
		
		
		//currentPanel.test(trackControllerList, trainList);
	}

	public TrackControllerPanel getPanel() {
		return this.currentPanel;
	}
	
	public void receiveTick() {
		if(hasTrack) {
			runPLC();
			currentPanel.displayChange();
		}
	}
	
	/**************************************************************************************
	 * CALL ME ONCE PER TICK**************************************************************/
	public void runPLC(){
		
		TrackController.runPLC(trackControllerList);
	}

	protected ArrayList<Block> getBlockList() {
		return myBlocks;
	}
	
	protected ArrayList<Integer> getTrainList() {
		return trainList;
	}
	
	protected ArrayList<Integer> getSwitchList() {
		return switchList;
	}
	
	protected ArrayList<TrackController> getTrCList() {
		return trackControllerList;
	}
	protected ArrayList<Integer> getCrossingList() {
		return crossingList;
	}
}
