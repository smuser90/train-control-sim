package CTC;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CTCPanel extends JPanel {
	
	// GUI Elements
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JComboBox lineBox;
	private JComboBox blockActionsBox;
	private JComboBox trainBox;
	private JComboBox stationBox;
	private JComboBox trainActionsBox;
	private JComboBox blocksBox;
	private JButton trainButton;
	private JButton blockButton;
	private JTextField speedField;
	private JTextField authorityField;
	private JLabel lineLabel;
	private JLabel blockLabel;
	private JLabel speedLabel;
	private JLabel trainLabel;
	private JLabel authorityLabel;
	
	// Fields
	private CTCModule _ctc;
	
	/**
	 * Create the panel.
	 */
	public CTCPanel(CTCModule ctc) {
		_ctc = ctc;
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 300, 378);
		add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("CTC Office", null, panel, null);
		panel.setLayout(null);
		
		lineLabel = new JLabel("Select Line");
		lineLabel.setBounds(10, 11, 275, 14);
		panel.add(lineLabel);
		
		trainButton = new JButton("Perform Train Action");
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		trainButton.setBounds(10, 286, 275, 23);
		panel.add(trainButton);
		
		blocksBox = new JComboBox();
		blocksBox.setModel(new DefaultComboBoxModel(new String[] {"Blocks"}));
		blocksBox.setBounds(10, 85, 136, 20);
		panel.add(blocksBox);
		
		blockLabel = new JLabel("Block Actions");
		blockLabel.setBounds(10, 60, 275, 14);
		panel.add(blockLabel);
		
		speedLabel = new JLabel("Set Speed Limit");
		speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		speedLabel.setBounds(10, 116, 136, 14);
		panel.add(speedLabel);
		
		blockActionsBox = new JComboBox();
		blockActionsBox.setModel(new DefaultComboBoxModel(new String[] {"Block Actions", "Speed Limit", "Close", "Open"}));
		blockActionsBox.setBounds(156, 85, 129, 20);
		panel.add(blockActionsBox);
		
		speedField = new JTextField();
		speedField.setColumns(10);
		speedField.setBounds(158, 113, 127, 20);
		panel.add(speedField);
		
		blockButton = new JButton("Perform Block Action");
		blockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		blockButton.setBounds(10, 140, 275, 23);
		panel.add(blockButton);
		
		trainLabel = new JLabel("Train Actions");
		trainLabel.setBounds(10, 174, 275, 14);
		panel.add(trainLabel);
		
		trainBox = new JComboBox();
		trainBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		trainBox.setBounds(10, 199, 136, 20);
		panel.add(trainBox);
		
		stationBox = new JComboBox();
		stationBox.setModel(new DefaultComboBoxModel(new String[] {"Stations"}));
		stationBox.setBounds(10, 230, 136, 20);
		panel.add(stationBox);
		
		trainActionsBox = new JComboBox();
		trainActionsBox.setModel(new DefaultComboBoxModel(new String[] {"Train Actions", "Schedule Train", "Route Train"}));
		trainActionsBox.setBounds(156, 199, 129, 20);
		panel.add(trainActionsBox);
		
		authorityLabel = new JLabel("Set Authority");
		authorityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		authorityLabel.setBounds(10, 261, 136, 14);
		panel.add(authorityLabel);
		
		authorityField = new JTextField();
		authorityField.setColumns(10);
		authorityField.setBounds(156, 258, 129, 20);
		panel.add(authorityField);
		
		lineBox = new JComboBox();
		lineBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(lineBox.getSelectedItem());
			}
		});
		lineBox.setModel(new DefaultComboBoxModel(new String[] {"Lines", "Green", "Red"}));
		lineBox.setBounds(10, 29, 275, 20);
		panel.add(lineBox);

	}
}
