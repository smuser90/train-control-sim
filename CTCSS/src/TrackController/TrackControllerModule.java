package TrackController;

/*************************************
 * For block.type
 * 0 regular
 * 1 switch
 * 2 crossing
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class TrackControllerModule extends JFrame {

	private JPanel contentPane;
	static TrackControllerPanel currentPanel;
	JLabel lblNewLabel = new JLabel("New label");
	static ArrayList<TrackController> trackControllerList = new ArrayList<TrackController>();
	static ArrayList<Block> myBlocks = new ArrayList<Block>();//new ArrayList<Block>();
	static ArrayList<Integer> switchList = new ArrayList<Integer>();
	static ArrayList<Integer> trainList = new ArrayList<Integer>();
	static ArrayList<Integer> crossingList = new ArrayList<Integer>();
	static int upperLimit;
	static int lowerLimit;
	static int index = 1;
	static int count = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerModule frame = new TrackControllerModule();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrackControllerModule() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 00, 650, 350);
		currentPanel = new TrackControllerPanel();
		
		for (int blockCount = 0; blockCount < 5; blockCount++) {
			Block blk = new Block(blockCount, false, false);
			if(blockCount == 1)
				blk.type = 1;
			if(blockCount == 3)
				blk.type = 2;
			myBlocks.add(blk);
			
				
		}
		for (int listCount = 0; listCount < myBlocks.size(); listCount++) {
			if (myBlocks.get(listCount).type == 1) {
				switchList.add(listCount);
			}
			if (myBlocks.get(listCount).occupied) {
				trainList.add(listCount );
			}
			if (myBlocks.get(listCount).type == 2)
				crossingList.add(listCount);
		}
		//this.setContentPane(currentPanel);


	}
	/************************************************************************************************
	 CALL THIS TO WAKE ME UP************************************************************************/
	public static void getTrack(){
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
		if (switchList.size() != 0) {
			for (index = 0; index < switchList.size(); index++) {
				TrackController tc = new TrackController(index + 1, 0, 0, 0);

				if (index == 0 && index != switchList.size() - 1) {
					upperLimit = switchList.get(switchList.size() - 1);
					lowerLimit = (switchList.get((index + 1)));
					//lowerLimit = (switchList.get((index)));
					for (count = 0; count < lowerLimit; count++) {
						tc.blocksControlled.add(count);
					}

					//for (count = upperLimit + 1; count < 101; count++) {
					//	tc.blocksControlled.add(count);
					//}
				}

				else if (index == switchList.size() - 1) {
					lowerLimit = switchList.get(0);
					//upperLimit = switchList.get(index - 1);
					upperLimit = switchList.get(index);

					for (count = 0; count < lowerLimit + 1; count++) {
						tc.blocksControlled.add(count);
					}

					for (count = upperLimit + 1; count < myBlocks.size(); count++) {
						tc.blocksControlled.add(count);
					}
				}

				else {
					upperLimit = switchList.get(index + 1);
					lowerLimit = switchList.get(index - 1);
					for (count = lowerLimit + 1; count < upperLimit; count++)
						tc.blocksControlled.add(count);
				}
				tc.blocks = tc.blocksControlled.size();
				trackControllerList.add(tc);
			}
		}
		else{
			TrackController tc = new TrackController(1, 0, 0, 0);
			for(count = 0; count < myBlocks.size(); count++){
				tc.blocksControlled.add(count);
				//tc.blocksControlled.add(count + 1);
			}
			tc.blocks = tc.blocksControlled.size();
			trackControllerList.add(tc);
		}

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
		currentPanel.displayChange();
		
		//currentPanel.test(trackControllerList, trainList);
	}

	public TrackControllerPanel getPanel() {
		return this.currentPanel;
	}
	
	
	/**************************************************************************************
	 * CALL ME ONCE PER TICK**************************************************************/
	public static void runPLC(){
		//currentPanel.test2();
		TrackController.runPLC(trackControllerList);
	}

	public static ArrayList<Block> getBlockList() {
		return myBlocks;
	}
}
