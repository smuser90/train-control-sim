package TrackController;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;

public class TCPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TCPanel() {
		setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Properties");
		lblNewLabel_3.setBounds(10, 111, 77, 14);
		add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Prev TC");
		btnNewButton.setBounds(10, 250, 125, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Next TC");
		btnNewButton_1.setBounds(10, 279, 125, 23);
		add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		scrollPane.setBounds(192, 28, 448, 45);
		add(scrollPane);
		
		JTextArea txtrTrainId = new JTextArea();
		txtrTrainId.setEditable(false);
		scrollPane.setViewportView(txtrTrainId);
		txtrTrainId.setText("Train ID - On Block: 17 Speed: 55 Authority: 1\r\nTrain ID - On Block: 19 Speed: 50 Authority: 7\r\nTrain ID - On Block: 20 Speed: 45 Authority: 3\r\nTrain ID - On Block: 13 Speed: 65 Authority: 4");
		scrollPane.getVerticalScrollBar().setValue(0);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		scrollPane_1.setBounds(192, 104, 448, 45);
		add(scrollPane_1);
		
		JTextArea txtrSwitchAtBlock = new JTextArea();
		txtrSwitchAtBlock.setEditable(false);
		scrollPane_1.setViewportView(txtrSwitchAtBlock);
		txtrSwitchAtBlock.setText("Switch at Block 12: ACTIVE\r\nSwitch at Block 16: ACTIVE\r\nSwitch at Block 18: ACTIVE");
		scrollPane_1.getVerticalScrollBar().setValue(0);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		scrollPane_2.setBounds(192, 188, 448, 45);
		add(scrollPane_2);
		
		JTextArea txtrBrokenRailAt = new JTextArea();
		txtrBrokenRailAt.setEditable(false);
		scrollPane_2.setViewportView(txtrBrokenRailAt);
		txtrBrokenRailAt.setText("Broken Rail at Block: 14");
		scrollPane_2.getVerticalScrollBar().setValue(0);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		scrollPane_3.setBounds(192, 268, 448, 45);
		add(scrollPane_3);
		
		JTextArea txtrCrossingAtBlock = new JTextArea();
		txtrCrossingAtBlock.setEditable(false);
		scrollPane_3.setViewportView(txtrCrossingAtBlock);
		txtrCrossingAtBlock.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		txtrCrossingAtBlock.setText("Crossing at Block 17: INACTIVE\r\nCrossing at Block 20: ACTIVE\r\nCrossing at Block 17: INACTIVE");
		scrollPane_3.getVerticalScrollBar().setValue(0);
		
		JLabel lblTrains = new JLabel("Trains");
		lblTrains.setBounds(192, 11, 46, 14);
		add(lblTrains);
		
		JLabel lblSwitches = new JLabel("Switches");
		lblSwitches.setBounds(192, 90, 46, 14);
		add(lblSwitches);
		
		JLabel lblBrokenRails = new JLabel("Broken Rails");
		lblBrokenRails.setBounds(192, 170, 77, 14);
		add(lblBrokenRails);
		
		JLabel lblCrossings = new JLabel("Crossings");
		lblCrossings.setBounds(192, 254, 46, 14);
		add(lblCrossings);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		panel.setBounds(10, 124, 123, 85);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(" Track Controller: 1");
		lblNewLabel.setBounds(10, 11, 92, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel(" Number of Trains: 2");
		lblNewLabel_2.setBounds(10, 36, 104, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel(" Number of Blocks: 13");
		lblNewLabel_1.setBounds(10, 60, 115, 14);
		panel.add(lblNewLabel_1);
		
		JRadioButton rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.setBounds(10, 31, 109, 23);
		add(rdbtnGreen);
		
		JRadioButton rdbtnRed = new JRadioButton("Red");
		rdbtnRed.setBounds(10, 50, 109, 23);
		add(rdbtnRed);
		
		JLabel lblSelectLine = new JLabel("Select Line");
		lblSelectLine.setBounds(10, 11, 77, 14);
		add(lblSelectLine);

	}
}
