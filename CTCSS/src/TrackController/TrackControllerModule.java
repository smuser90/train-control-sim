package TrackController;

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
	private TrackControllerPanel currentPanel;
	JLabel lblNewLabel = new JLabel("New label");
	static ArrayList<TrackController> trackControllerList = new ArrayList<TrackController>();
	static ArrayList<Block> myBlocks = new ArrayList<Block>();
	static ArrayList<Integer> switchList = new ArrayList<Integer>();
	static ArrayList<Integer> trainList = new ArrayList<Integer>();
	static ArrayList<Integer> crossingList = new ArrayList<Integer>();
	static int upperLimit;
	static int lowerLimit;
	int index = 1;
	int count = 0;

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
		//this.setContentPane(currentPanel);

		// Create a list of 100 blocks for stand alone testing *TESTING*
		for (int blockCount = 1; blockCount < 101; blockCount++) {
			Block blk = new Block(blockCount, false, false);
			myBlocks.add(blk);
		}

		// Set some of the blocks as switches *TESTING*
		myBlocks.get(8).isSwitch = true;
		myBlocks.get(17).isSwitch = true;
		myBlocks.get(25).isSwitch = true;
		myBlocks.get(41).isSwitch = true;
		myBlocks.get(53).isSwitch = true;
		myBlocks.get(70).isSwitch = true;
		myBlocks.get(84).isSwitch = true;
		myBlocks.get(92).isSwitch = true;

		// Add some trains *TESTING*
		// myBlocks.get(85).occupied = true;
		myBlocks.get(16).occupied = true;
		myBlocks.get(53).occupied = true;
		myBlocks.get(99).occupied = true;
		myBlocks.get(85).occupied = true;
		myBlocks.get(0).occupied = true;

		// Create lists depending on the block properties
		for (int listCount = 0; listCount < myBlocks.size(); listCount++) {
			if (myBlocks.get(listCount).isSwitch) {
				switchList.add(listCount + 1);
			}
			if (myBlocks.get(listCount).occupied) {
				trainList.add(listCount + 1);
			}
			if (myBlocks.get(listCount).isCrossing)
				crossingList.add(listCount + 1);
		}

		// Here we go through and create all the track controllers and assign
		// them their blocks
		if (switchList != null) {
			for (index = 0; index < switchList.size(); index++) {
				TrackController tc = new TrackController(index + 1, 0, 0, 0);

				if (index == 0) {
					upperLimit = switchList.get(switchList.size() - 1);
					lowerLimit = (switchList.get((index + 1)));
					for (count = 1; count < lowerLimit; count++) {
						tc.blocksControlled.add(count);
					}

					for (count = upperLimit + 1; count < 101; count++) {
						tc.blocksControlled.add(count);
					}
				}

				else if (index == switchList.size() - 1) {
					lowerLimit = switchList.get(0);
					upperLimit = switchList.get(index - 1);

					for (count = 1; count < lowerLimit; count++) {
						tc.blocksControlled.add(count);
					}

					for (count = upperLimit + 1; count < 101; count++) {
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
			for(count = 0; count < myBlocks.size(); count++){
				
			}
		}

		// For the initial list, set up any trains that are placed on the track
		for (index = 0; index < trackControllerList.size(); index++) {
			for (count = 0; count < trainList.size(); count++) {
				if (trainList.get(count) == 100
						&& trackControllerList.get(index).blocksControlled
								.contains(100)) {
					trackControllerList.get(index).trains++;
					break;
				}
				if (trackControllerList.get(index).blocksControlled
						.contains(trainList.get(count))) {
					trackControllerList.get(index).trains++;
				}
			}
		}

		currentPanel.displayChange();
		currentPanel.test(trackControllerList, trainList);

	}

	public TrackControllerPanel getPanel() {
		return this.currentPanel;
	}
}
