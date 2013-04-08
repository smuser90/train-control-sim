package TrackController;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Point;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class JPanel_Example extends JPanel {
	
	//Initialize the Buttons
	private final JButton btnPrevTc = new JButton("Prev TC");
	private final JButton btnNextTc = new JButton("Next TC");
	
	//Initialize the Combo Box
	private final JComboBox trackSelect = new JComboBox();
	
	//Initialize the Text Areas
	private final JTextArea tcProperties = new JTextArea();
	private final JTextArea listOfTrains = new JTextArea();
	private final JTextArea listOfSwitches = new JTextArea();
	private final JTextArea listOfBrokenRails = new JTextArea();
	private final JTextArea listOfCrossings = new JTextArea();
	
	//Initialize the Scroll Panes
	private final JScrollPane tcPropertiesScrollPane = new JScrollPane();
	private final JScrollPane listOfTrainScrollPane = new JScrollPane();
	private final JScrollPane listOfSwitchesScrollPane = new JScrollPane();
	private final JScrollPane listOfBrokenRailsScrollPane = new JScrollPane();
	private final JScrollPane listOfCrossingsScrollPane = new JScrollPane();
	
	//Initialize the Labels
	private final JLabel lblTrains = new JLabel("TRAINS");
	private final JLabel lblSwitches = new JLabel("SWITCHES");
	private final JLabel lblBrokenRails = new JLabel("BROKEN RAILS");
	private final JLabel lblCrossings = new JLabel("CROSSINGS");
	private final JLabel lblTrack = new JLabel("TRACK");
	private final JLabel lblTCProperties = new JLabel("TC Properties");
	
	//Generate Some TC Properties for Preliminary Testing Without Other Modules
	private int trackControllerNum = 1;
	private int numberOfBlocks = 13;
	private int numberOfTrains = 2;
	
	//Create the Panel
	public JPanel_Example() {
		setBackground(Color.LIGHT_GRAY);
		setUpPanel();
	}
	
	//After Setup Runs, Wait for User to Interact with GUI
	public void waitForInput(){
		
		//User Clicks Next TC Button
		btnNextTc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackControllerIncrement();
				//listOfTrains.setText("You clicked next TC");
			}
		});
		
		//User Clicks Prev TC Button
		btnPrevTc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackControllerDecrement();
				//listOfTrains.setText("You clicked Prev TC");
			}
		});
		
		//User Changes Selected Track Using ComboBox
		trackSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listOfTrains.setText("You changed the combo box to " + trackSelect.getSelectedItem().toString());
			}
		});
	}
	
	//User clicks Next TC
	private void trackControllerIncrement(){
		if(trackControllerNum == 5)
		{
			trackControllerNum = 1;
			numberOfBlocks = 13;
			numberOfTrains = 2;
			tcProperties.setText("Track Controller: "+ trackControllerNum +"\n\n Blocks: " + numberOfBlocks + "\n\n Trains: "+ numberOfTrains + "");
		}
		
		else
		{
			trackControllerNum++;
			numberOfBlocks = numberOfBlocks + 3;//numberOfBlocks%12;
			numberOfTrains = numberOfTrains+1;//trackControllerNum%3+1;
			tcProperties.setText("Track Controller: "+ trackControllerNum +"\n\n Blocks: " + numberOfBlocks + "\n\n Trains: "+ numberOfTrains + "");
		}
	}
	
	//User clicks Prev TC
	private void trackControllerDecrement(){
		if(trackControllerNum == 1)
		{
			trackControllerNum = 5;
			numberOfBlocks = 28;
			numberOfTrains = 3;
		}
		
		else
		{
			trackControllerNum--;
			numberOfBlocks = numberOfBlocks - 3;//numberOfBlocks - numberOfBlocks%12;
			numberOfTrains = numberOfTrains-1;//trackControllerNum%3-1;
			tcProperties.setText("Track Controller: "+ trackControllerNum +"\n\n Blocks: " + numberOfBlocks + "\n\n Trains: "+ numberOfTrains + "");
		}
		
	}
	
	//Represents a Change in TC
	private void trackControllerChange(){
		detectTrains();
		receiveSpeedLimit();
		receiveAuthority();
		
	}
	
	//Used to Detect Trains in the Current Track Controllers Range
	private void detectTrains(){
		
		//Generate list of detected trains
		
	}
	
	//Used to Receive Speed Limit for Each Train
	private void receiveSpeedLimit(){
		
		//Associate speed limit with each train from list
		
	}
	
	//Used to Receive Authority for Each Train
	private void receiveAuthority(){
		
		//Associate authority with each train from list
		
	}
	
	//Set up JPanel
	private void setUpPanel(){
		setLayout(null);
		
		//Add Buttons
		btnPrevTc.setBounds(49, 25, 89, 23);
		add(btnPrevTc);
		
		btnNextTc.setBounds(552, 25, 89, 23);
		add(btnNextTc);
		
		//Add Combo Box
		trackSelect.addItem("RED");
		trackSelect.addItem("GREEN");
		trackSelect.setBounds(552, 174, 95, 20);
		add(trackSelect);	
		
		//Add Scroll Panes and Text Areas
		tcPropertiesScrollPane.setBounds(169, 24, 301, 104);
		add(tcPropertiesScrollPane);
		tcPropertiesScrollPane.setViewportView(tcProperties);
		tcProperties.setText("Track Controller: "+ trackControllerNum +"\n\n Blocks: " + numberOfBlocks + "\n\n Trains: "+ numberOfTrains + "");
		tcProperties.setLineWrap(true);
		tcProperties.setEditable(false);
		
		listOfTrainScrollPane.setBounds(49, 172, 465, 102);
		add(listOfTrainScrollPane);
		listOfTrainScrollPane.setViewportView(listOfTrains);
		listOfTrains.setText("Train: ID# - Block: # Speed: # Authority: #");
		listOfTrains.setLineWrap(true);
		listOfTrains.setEditable(false);
		
		listOfSwitchesScrollPane.setBounds(49, 300, 463, 100);
		add(listOfSwitchesScrollPane);
		listOfSwitchesScrollPane.setViewportView(listOfSwitches);
		listOfSwitches.setText("Switch at Block #: ACTIVE");
		listOfSwitches.setLineWrap(true);
		listOfSwitches.setEditable(false);
		
		listOfBrokenRailsScrollPane.setBounds(49, 427, 461, 98);
		add(listOfBrokenRailsScrollPane);
		listOfBrokenRailsScrollPane.setViewportView(listOfBrokenRails);
		listOfBrokenRails.setText("Broken Rail at Block #");
		listOfBrokenRails.setLineWrap(true);
		listOfBrokenRails.setEditable(false);
		
		listOfCrossingsScrollPane.setBounds(49, 553, 459, 96);
		add(listOfCrossingsScrollPane);
		listOfCrossingsScrollPane.setViewportView(listOfCrossings);
		listOfCrossings.setText("Crossing at Block #: INACTIVE");
		listOfCrossings.setLineWrap(true);
		listOfCrossings.setEditable(false);
				
		//Add Labels
		lblTrack.setBounds(552, 156, 89, 14);
		add(lblTrack);
		
		lblTCProperties.setBounds(169, 11, 130, 14);
		add(lblTCProperties);
		
		lblTrains.setBounds(49, 156, 89, 14);
		add(lblTrains);
		
		lblSwitches.setBounds(49, 286, 89, 14);
		add(lblSwitches);
		
		lblBrokenRails.setBounds(49, 411, 89, 14);
		add(lblBrokenRails);
		
		lblCrossings.setBounds(49, 539, 89, 14);
		add(lblCrossings);
		
		//With setup done, wait for the user to interact with the GUI
		waitForInput();
		
	}
}
