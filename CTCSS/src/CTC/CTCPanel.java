package CTC;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Log.Log;
import TrackModel.Block;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
	private String [] blockDef= new String[] {"Blocks"};
	private String [] blockADef= new String[] {"Block Actions", "Set Speed Limit", "Close", "Open"};
	private String [] trainsDef= new String[] {"Trains"};
	private String [] trainsADef= new String[] {"Train Actions", "Schedule Train", "Route Train", "Authority"};
	private String [] stationsDef= new String[] {"Stations"};
	private String [] lineDef= new String[] {"Lines"};
	private Log log = Log.Instance();
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
				trainActions();
			}
		});
		trainButton.setBounds(10, 286, 275, 23);
		panel.add(trainButton);
		
		blocksBox = new JComboBox();
		blocksBox.setModel(new DefaultComboBoxModel(blockDef));
		blocksBox.setBounds(10, 85, 136, 20);
		panel.add(blocksBox);
		
		blockLabel = new JLabel("Block Actions");
		blockLabel.setBounds(10, 60, 275, 14);
		panel.add(blockLabel);
		
		speedLabel = new JLabel("Speed Limit");
		speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		speedLabel.setBounds(10, 116, 136, 14);
		panel.add(speedLabel);
		
		blockActionsBox = new JComboBox();
		blockActionsBox.setModel(new DefaultComboBoxModel(blockADef));
		blockActionsBox.setBounds(156, 85, 129, 20);
		panel.add(blockActionsBox);
		
		speedField = new JTextField();
		speedField.setColumns(10);
		speedField.setBounds(158, 113, 127, 20);
		panel.add(speedField);
		
		blockButton = new JButton("Perform Block Action");
		blockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				blockActions();
			}
		});
		blockButton.setBounds(10, 140, 275, 23);
		panel.add(blockButton);
		
		trainLabel = new JLabel("Train Actions");
		trainLabel.setBounds(10, 174, 275, 14);
		panel.add(trainLabel);
		
		trainBox = new JComboBox();
		trainBox.setModel(new DefaultComboBoxModel(trainsDef));
		trainBox.setBounds(10, 199, 136, 20);
		panel.add(trainBox);
		
		stationBox = new JComboBox();
		stationBox.setModel(new DefaultComboBoxModel(stationsDef));
		stationBox.setBounds(10, 230, 136, 20);
		panel.add(stationBox);
		
		trainActionsBox = new JComboBox();
		trainActionsBox.setModel(new DefaultComboBoxModel(trainsADef));
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
				update();
			}
		});
		lineBox.setModel(new DefaultComboBoxModel(lineDef));
		lineBox.setBounds(10, 29, 275, 20);
		panel.add(lineBox);
		
		toggle(false);
	}
	
	protected void update() {
		int tempPos = lineBox.getSelectedIndex();
		reset();
		lineBox.setSelectedIndex(tempPos);
		if(lineBox.getSelectedItem().equals("Lines")) {
			return;
		} else {
			ArrayList<Block> t = _ctc.getLine((String) lineBox.getSelectedItem());
			if(t == null)
				return;
			ArrayList<String> t2 = new ArrayList<String>();
			ArrayList<String> t3 = new ArrayList<String>();
			ArrayList<Integer> t4 = _ctc.getGLTrains();
			ArrayList<String> t5 = new ArrayList<String>();
			t3.add("Stations");
			for(int i = 0; i < t.size(); i++) {
				if(i == 0) {
					t2.add("Blocks");
				} else {
					if(t.get(i).getType() == 3) {
						t3.add(Integer.toString(t.get(i).getBlockNumber()));
					} else {
						t2.add(Integer.toString(t.get(i).getBlockNumber()));
					}
				}
			}
			t5.add("Trains");
			for(int i = 0; i < t4.size(); i++) {
				t5.add(t4.get(i).toString());
			}
			String [] temp = new String [t2.size()];
			t2.toArray(temp);
			blocksBox.setModel(new DefaultComboBoxModel(temp));
			temp = new String [t3.size()];
			t3.toArray(temp);
			stationBox.setModel(new DefaultComboBoxModel(temp));
			temp = new String [t5.size()];
			t5.toArray(temp);
			trainBox.setModel(new DefaultComboBoxModel(temp));
			toggle(true);
		}
	}
	
	private void reset() {
		blocksBox.setModel(new DefaultComboBoxModel(blockDef));
		blockActionsBox.setModel(new DefaultComboBoxModel(blockADef));
		trainBox.setModel(new DefaultComboBoxModel(trainsDef));
		stationBox.setModel(new DefaultComboBoxModel(stationsDef));
		trainActionsBox.setModel(new DefaultComboBoxModel(trainsADef));
		ArrayList<String> t = _ctc.getLines();
		String [] temp = new String [t.size()];
		t.toArray(temp);
		lineBox.setModel(new DefaultComboBoxModel(temp));
		toggle(false);
	}
	
	public void trainActions() {
		if(!trainActionsBox.getSelectedItem().equals("Train Actions")) {
			if(trainActionsBox.getSelectedItem().equals("Schedule Train")) {
				if(lineBox.getSelectedItem().equals("Green"))
					_ctc.scheduleTrain(0);
				else
					_ctc.scheduleTrain(1);
			} else {
				log.append(3, "Not Yet Supported\n");
			}
		} else {
			log.append(3, "Must Select a a train action\n");
		}	
	}
	
	public void blockActions() {
		if(!blocksBox.getSelectedItem().equals("Blocks") && !blocksBox.getSelectedItem().equals("Block Actions")) {
			log.append(3, "Not Yet Supported\n");
		} else {
			log.append(3, "Not Yet Supported\n");
		}	
	}
	
	public void toggle(boolean onoff) {
		blockActionsBox.setEnabled(onoff);
		trainBox.setEnabled(onoff);
		stationBox.setEnabled(onoff);
		trainActionsBox.setEnabled(onoff);
		blocksBox.setEnabled(onoff);
		trainButton.setEnabled(onoff);
		blockButton.setEnabled(onoff);
		speedField.setEnabled(onoff);
		authorityField.setEnabled(onoff);
	}
}
