package TrackController;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class TrackControllerPanel extends JPanel {

	private JTextArea txtrCrossingAtBlock = new JTextArea();
	private JTextArea txtrTrainId = new JTextArea();
	private JTextArea txtrSwitchAtBlock = new JTextArea();
	private JLabel lblNewLabel = new JLabel("");
	private JLabel lblNewLabel_1 = new JLabel("");
	private JLabel lblNewLabel_2 = new JLabel("");
	
	// Fields
	private TrackControllerModule _tcm;
	private int currentController = 0;
	private int trackID = 0;
	private ArrayList<TrackController> trackControllerList;
	private ArrayList<Block> blockList;;
	private ArrayList<Integer> numberSwitch;
	private ArrayList<Integer> trainList;
	private int index = 0;
	private String myTrainList = "";
	private String mySwitchList = "";
	/**
	 * Create the panel.
	 */
	public TrackControllerPanel(TrackControllerModule tcm) {
		setLayout(null);
		_tcm = tcm;
		blockList = _tcm.getBlockList();
		trainList = _tcm.getTrainList();
		numberSwitch = _tcm.getSwitchList();
		trackControllerList = _tcm.getTrCList();
		JLabel lblNewLabel_3 = new JLabel("Properties");
		lblNewLabel_3.setBounds(10, 111, 77, 14);
		add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Prev TC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//currentController--;
				displayChange();
				
				// TrackControllerModule.nextTC();
			}
		});
		btnNewButton.setBounds(10, 250, 125, 23);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Next TC");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//currentController++;
				
				//This section used to test that trains would be detected properly
				//blockList.get(7).occupied = false;
				//blockList.get(28).occupied = true;
				//PLC.detectTrains();
				
				_tcm.getTrack();
				_tcm.runPLC();
				//currentController++;
				//displayChange();
				blockList.get(2).occupied = true;
				//TrackControllerTester.myBlocks.get(0).occupied = false;
				//TrackControllerTester.myBlocks.get(1).occupied = false;
				// TrackControllerModule.nextTC();
			}
		});
		btnNewButton_1.setBounds(10, 279, 125, 23);
		add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,
				0, 0)));
		scrollPane.setBounds(192, 28, 448, 45);
		add(scrollPane);

		
		txtrTrainId.setEditable(false);
		scrollPane.setViewportView(txtrTrainId);
		// txtrTrainId.setText("Train ID - On Block: 17 Speed: 55 Authority: 1\r\nTrain ID - On Block: 19 Speed: 50 Authority: 7\r\nTrain ID - On Block: 20 Speed: 45 Authority: 3\r\nTrain ID - On Block: 13 Speed: 65 Authority: 4");
		scrollPane.getVerticalScrollBar().setValue(0);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,
				0, 0)));
		scrollPane_1.setBounds(192, 104, 448, 45);
		add(scrollPane_1);

		
		txtrSwitchAtBlock.setEditable(false);
		scrollPane_1.setViewportView(txtrSwitchAtBlock);
		// txtrSwitchAtBlock.setText("Switch at Block 12: ACTIVE\r\nSwitch at Block 16: ACTIVE\r\nSwitch at Block 18: ACTIVE");
		scrollPane_1.getVerticalScrollBar().setValue(0);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,
				0, 0)));
		scrollPane_2.setBounds(192, 188, 448, 45);
		add(scrollPane_2);

		JTextArea txtrBrokenRailAt = new JTextArea();
		txtrBrokenRailAt.setEditable(false);
		scrollPane_2.setViewportView(txtrBrokenRailAt);
		// txtrBrokenRailAt.setText("Broken Rail at Block: 14");
		scrollPane_2.getVerticalScrollBar().setValue(0);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,
				0, 0)));
		scrollPane_3.setBounds(192, 268, 448, 45);
		add(scrollPane_3);

		txtrCrossingAtBlock.setEditable(false);
		scrollPane_3.setViewportView(txtrCrossingAtBlock);
		txtrCrossingAtBlock.setBorder(new MatteBorder(0, 0, 0, 0,
				(Color) new Color(0, 0, 0)));
		// txtrCrossingAtBlock.setText("Crossing at Block 17: INACTIVE\r\nCrossing at Block 20: ACTIVE\r\nCrossing at Block 17: INACTIVE");
		scrollPane_3.getVerticalScrollBar().setValue(0);

		JLabel lblTrains = new JLabel("Trains");
		lblTrains.setBounds(192, 11, 125, 14);
		add(lblTrains);

		JLabel lblSwitches = new JLabel("Switches");
		lblSwitches.setBounds(192, 90, 125, 14);
		add(lblSwitches);

		JLabel lblBrokenRails = new JLabel("Broken Rails");
		lblBrokenRails.setBounds(192, 170, 178, 14);
		add(lblBrokenRails);

		JLabel lblCrossings = new JLabel("Crossings");
		lblCrossings.setBounds(192, 254, 178, 14);
		add(lblCrossings);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		panel.setBounds(10, 124, 123, 85);
		add(panel);
		panel.setLayout(null);

		// JLabel lblNewLabel = new JLabel(" Track Controller: 1");
		lblNewLabel.setBounds(10, 11, 92, 14);
		panel.add(lblNewLabel);

		// JLabel lblNewLabel_2 = new JLabel(" Number of Trains: 2");
		lblNewLabel_2.setBounds(10, 36, 104, 14);
		panel.add(lblNewLabel_2);

		// JLabel lblNewLabel_1 = new JLabel(" Number of Blocks: 13");
		lblNewLabel_1.setBounds(10, 60, 115, 14);
		panel.add(lblNewLabel_1);

		JLabel lblSelectLine = new JLabel("Select Line");
		lblSelectLine.setBounds(10, 11, 77, 14);
		add(lblSelectLine);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboBox.setBounds(10, 32, 125, 20);
		comboBox.addItem("Green");
		comboBox.addItem("Red");
		//comboBox.
		add(comboBox);

	}

	protected void displayChange() {
		// txtrCrossingAtBlock.setText("Crossing at Block 17: INACTIVE\r\nCrossing at Block 20: ACTIVE\r\nCrossing at Block 17: INACTIVE");
		//for (int i = 0; i < trackControllerList.size(); i++) {
			if (currentController == trackControllerList.size())
				currentController = 0;
			else if(currentController == -1)
				currentController = 7;
			// trackID == 0 means we are on the green track
			if (trackID == 0) {
				if (trackControllerList.get(currentController).getTrackNum() == 0) {
					//txtrCrossingAtBlock.setText("Track Controller : "
						//	+ trackControllerList.get(currentController).number + " With "
						//	+ trackControllerList.get(currentController).blocks + " blocks and is track "
						//	+ trackControllerList.get(currentController).track);
					lblNewLabel.setText("Controller: " + trackControllerList.get(currentController).getID());
					lblNewLabel_2.setText("Blocks: " + trackControllerList.get(currentController).getNumBlocks());
					lblNewLabel_1.setText("Trains: " + trackControllerList.get(currentController).getNumTrains());
					//break;
				} else {
					currentController++;
				}
			} else {
				if (trackControllerList.get(currentController).getTrackNum() == 1) {
					//txtrCrossingAtBlock.setText("Track Controller : "
						//	+ trackControllerList.get(currentController).number + " With "
						//	+ trackControllerList.get(currentController).blocks + " blocks and is track "
						//	+ trackControllerList.get(currentController).track);
					lblNewLabel.setText("Controller: " + trackControllerList.get(currentController).getID());
					lblNewLabel_2.setText("Blocks: " + trackControllerList.get(currentController).getNumBlocks());
					lblNewLabel_1.setText("Trains: " + trackControllerList.get(currentController).getNumTrains());

					//break;
				} else {
					currentController++;
				}
			}
		//}
		//txtrCrossingAtBlock.setText(""+ trackControllerList.get(0).blocksControlled.size());
		
		//txtrCrossingAtBlock.setText("" +trackControllerList.get(0).blocksControlled.get(2));
		
		//For each of the controlled blocks of the current Track Controller, printing out the occupied blocks
		//currentController = 0;
		for(index = 0; index < (trackControllerList.get(currentController).blocksControlled.size()); index++)
		{
        //	if(trainList.contains(trackControllerList.get(currentController).blocksControlled.get(index)))
	    //	{
		//		myTrainList = myTrainList + "Train on Block: " + (trackControllerList.get(currentController).blocksControlled.get(index)) + "\n";
		//  }
		//	if(trackControllerList.get(currentController).blocksControlled.get(index) == 100)
		//	{
		//		if(blockList.get(99).occupied)
		//		{
		//			//myTrainList = myTrainList + "Train on Block: 100 \n";
		//		}
		//	}
		//	
		//	else if(trackControllerList.get(currentController).blocksControlled.get(index) == 1)
		//	{
		//		if(blockList.get(0).occupied)
		//		{
		//			myTrainList = myTrainList + "Train on Block: 1 \n";
		//		}
		//	}
			
		//	else if(blockList.get(trackControllerList.get(currentController).blocksControlled.get(index)) == occupied)

			if(trackControllerList.get(currentController).blocksControlled.contains(blockList.get(index).blockNumber) && blockList.get(index).occupied)
			{
				myTrainList = myTrainList + "Train on Block: " + blockList.get(index).blockNumber + "\n";
				//txtrCrossingAtBlock.setText("here with are with final index " + index);
				//myTrainList = myTrainList + "Train on Block: " + (trackControllerList.get(currentController).blocksControlled.get(index) + 1 ) + "\n";
				
			}
			//else{}
		}
		txtrTrainId.setText(myTrainList);
		myTrainList = "";
		for(index = 0; index < numberSwitch.size(); index++)
		{
			if(trackControllerList.get(currentController).blocksControlled.contains(numberSwitch.get(index)))
			{
				mySwitchList = mySwitchList + "Switch on Block " + numberSwitch.get(index).intValue() + " switched to Block " + (numberSwitch.get(index).intValue() + 1) +"\n";
			}
		}
		//txtrSwitchAtBlock.setText("Here i am "+ numberSwitch.size());
		txtrSwitchAtBlock.setText(mySwitchList);
		mySwitchList = "";
			
		
		//Goes through each controlled block for each track controller
		//for(Iterator<Integer> i = trackControllerList.get(currentController).blocksControlled.iterator();i.hasNext();)
		//{
			//lblNewLabel.setText("Hello " + i.next());
		//}
		
		//lblNewLabel.setText("Howdy " + up);
	}
	
	/*public void test(ArrayList<TrackController> me, ArrayList<Integer> trainList){
		txtrSwitchAtBlock.setText("" + numberSwitch.toString());//blockList.get(trackControllerList.get(6).blocksControlled.get(14)).occupied);
		
	}


	public private void test3() {
		txtrSwitchAtBlock.setText("" + blockList.size());
		
	}*/


}
