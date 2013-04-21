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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private String [] trainsADef= new String[] {"Train Actions", "Schedule Train"};
	private String [] trainsHADef= new String[] {"Train Actions", "Schedule Train", "Route Train", "Set Authority"};
	private String [] stationsDef= new String[] {"Stations"};
	private String [] lineDef= new String[] {"Lines"};
	private Log log = Log.Instance();
	/**
	 * Create the panel.
	 */
	public CTCPanel(CTCModule ctc) 
	{
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
		trainButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				trainActions();
			}
		});
		trainButton.setBounds(10, 286, 275, 23);
		panel.add(trainButton);
		
		blocksBox = new JComboBox();
		blocksBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(blockActionsBox.getSelectedItem().equals("Set Speed Limit")) 
				{
					speedField.requestFocus();
				}
			}
		});
		blocksBox.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					blockActions();
				}
			}
		});
		blocksBox.setModel(new DefaultComboBoxModel(blockDef));
		blocksBox.setBounds(10, 85, 133, 20);
		panel.add(blocksBox);
		
		blockLabel = new JLabel("Block Actions");
		blockLabel.setBounds(10, 60, 275, 14);
		panel.add(blockLabel);
		
		speedLabel = new JLabel("Speed Limit");
		speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		speedLabel.setBounds(10, 116, 133, 14);
		panel.add(speedLabel);
		
		blockActionsBox = new JComboBox();
		blockActionsBox.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					blockActions();
				}
			}
		});
		blockActionsBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(blockActionsBox.getSelectedItem().equals("Set Speed Limit")) 
				{
					speedField.requestFocus();
					speedField.setEnabled(true);
				} else {
					speedField.setEnabled(false);
				}
			}
		});
		blockActionsBox.setModel(new DefaultComboBoxModel(blockADef));
		blockActionsBox.setBounds(152, 85, 133, 20);
		panel.add(blockActionsBox);
		
		speedField = new JTextField();
		speedField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					blockActions();
				}
			}
		});
		speedField.setColumns(10);
		speedField.setBounds(153, 113, 133, 20);
		panel.add(speedField);
		
		blockButton = new JButton("Perform Block Action");
		blockButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				blockActions();
			}
		});
		blockButton.setBounds(10, 140, 275, 23);
		panel.add(blockButton);
		
		trainLabel = new JLabel("Train Actions");
		trainLabel.setBounds(10, 174, 275, 14);
		panel.add(trainLabel);
		
		trainBox = new JComboBox();
		trainBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(trainActionsBox.getSelectedItem().equals("Set Authority")) 
				{
					authorityField.requestFocus();
				}
			}
		});
		trainBox.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					trainActions();
				}
			}
		});
		trainBox.setModel(new DefaultComboBoxModel(trainsDef));
		trainBox.setBounds(10, 199, 133, 20);
		panel.add(trainBox);
		
		stationBox = new JComboBox();
		stationBox.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					trainActions();
				}
			}
		});
		stationBox.setModel(new DefaultComboBoxModel(stationsDef));
		stationBox.setBounds(10, 230, 133, 20);
		panel.add(stationBox);
		
		trainActionsBox = new JComboBox();
		trainActionsBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(trainActionsBox.getSelectedItem().equals("Set Authority")) 
				{
					authorityField.setEnabled(true);
					authorityField.requestFocus();
					stationBox.setEnabled(false);
				} 
				else if(trainActionsBox.getSelectedItem().equals("Route Train"))
				{
					stationBox.setEnabled(true);
					authorityField.setEnabled(false);
				} 
				else 
				{
					stationBox.setEnabled(false);
					authorityField.setEnabled(false);
				}
			}
		});
		trainActionsBox.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					trainActions();
				}
			}
		});
		trainActionsBox.setModel(new DefaultComboBoxModel(trainsADef));
		trainActionsBox.setBounds(152, 199, 133, 20);
		panel.add(trainActionsBox);
		
		authorityLabel = new JLabel("Set Authority");
		authorityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		authorityLabel.setBounds(10, 261, 133, 14);
		panel.add(authorityLabel);
		
		authorityField = new JTextField();
		authorityField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					trainActions();
				}
			}
		});
		authorityField.setColumns(10);
		authorityField.setBounds(152, 255, 133, 20);
		panel.add(authorityField);
		
		lineBox = new JComboBox();
		lineBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				update();
			}
		});
		lineBox.setModel(new DefaultComboBoxModel(lineDef));
		lineBox.setBounds(10, 29, 275, 20);
		panel.add(lineBox);
		
		toggle(false);
	}
	
	protected void update() 
	{
		int tempPos = lineBox.getSelectedIndex();
		reset();
		lineBox.setSelectedIndex(tempPos);
		if(lineBox.getSelectedItem().equals("Lines")) 
		{
			return;
		} 
		else 
		{
			/* Block Element Updates ************************************************/
			ArrayList<Block> t = _ctc.getLine((String) lineBox.getSelectedItem());
			if(t == null)
				return;
			ArrayList<String> t2 = new ArrayList<String>();
			ArrayList<String> t3 = new ArrayList<String>();
			ArrayList<String> t5 = new ArrayList<String>();
			t3.add("Stations");
			for(int i = 0; i < t.size(); i++) 
			{
				if(i == 0) {
					t2.add("Blocks");
					t3.add(t.get(i).getStationName());
				} 
				else 
				{
					if(t.get(i).getType() == 3) 
					{
						t3.add(t.get(i).getStationName());
					} 
					else 
					{
						t2.add(Integer.toString(t.get(i).getBlockNumber()));
					}
				}
			}
			
			String [] temp = new String [t2.size()];
			t2.toArray(temp);
			blocksBox.setModel(new DefaultComboBoxModel(temp));
			
			/* Train Element Updates ************************************************/
			ArrayList<Integer> t4 = _ctc.getLineTrains((String)lineBox.getSelectedItem());
			t5.add("Trains");
			for(int i = 0; i < t4.size(); i++) 
			{
				t5.add(t4.get(i).toString());
			}
			
			temp = new String [t3.size()];
			t3.toArray(temp);
			stationBox.setModel(new DefaultComboBoxModel(temp));
			temp = new String [t5.size()];
			t5.toArray(temp);
			trainBox.setModel(new DefaultComboBoxModel(temp));
			toggle(true);
			if(t4.size() > 0) 
			{
				trainBox.setEnabled(true);
				trainActionsBox.setModel(new DefaultComboBoxModel(trainsHADef));
			}
		}
	}
	
	private void reset() 
	{
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
	
	public void trainActions() 
	{
		if(!trainActionsBox.getSelectedItem().equals("Train Actions")) 
		{
			if(trainActionsBox.getSelectedItem().equals("Schedule Train")) 
			{
					_ctc.scheduleTrain((String)lineBox.getSelectedItem());
					trainActionsBox.setSelectedIndex(0);
			} 
			else if(!trainBox.getSelectedItem().equals("Trains") && trainActionsBox.getSelectedItem().equals("Route Train")) 
			{
				if(stationBox.getSelectedItem().equals("Stations")) 
				{
					log.append(3, "Must select a station\n");
				} 
				else 
				{
					_ctc.routeTrain(Integer.parseInt((String)trainBox.getSelectedItem()), (String)stationBox.getSelectedItem(), (String)lineBox.getSelectedItem());
					trainActionsBox.setSelectedIndex(0);
					trainBox.setSelectedIndex(0);
					stationBox.setSelectedIndex(0);
				}
			}
			else if(!trainBox.getSelectedItem().equals("Trains")) 
			{
				try 
				{
					int temp = Integer.parseInt(this.authorityField.getText());
					if(temp > 0) 
					{
						_ctc.setAuthority(Integer.parseInt((String)trainBox.getSelectedItem()), temp);
						authorityField.setText("");
						trainActionsBox.setSelectedIndex(0);
						trainBox.setSelectedIndex(0);
					} 
					else 
					{
						authorityField.setText("");
						log.append(3, "Authority must be an integer s.t. x > 0\n");
					}
				} 
				catch(Exception e) 
				{
					this.authorityField.setText("");
					log.append(3, "Authority must be an integer s.t. x > 0\n");
				}
			} 
			else 
			{
				log.append(3, "Must select a train\n");
			}
		} 
		else 
		{
			log.append(3, "Must select a train action\n");
		}	
	}
	
	public void blockActions() {
		if(!blocksBox.getSelectedItem().equals("Blocks") && !blockActionsBox.getSelectedItem().equals("Block Actions")) 
		{
			if(blockActionsBox.getSelectedItem().equals("Close")) 
			{
				_ctc.closeBLock((String)lineBox.getSelectedItem(), Integer.parseInt((String)blocksBox.getSelectedItem()));
				blocksBox.setSelectedIndex(0);
				blockActionsBox.setSelectedIndex(0);
				
			} 
			else if(blockActionsBox.getSelectedItem().equals("Open")) 
			{
				_ctc.openBLock((String)lineBox.getSelectedItem(), Integer.parseInt((String)blocksBox.getSelectedItem()));
				blocksBox.setSelectedIndex(0);
				blockActionsBox.setSelectedIndex(0);
			}
			else 
			{
				try 
				{
					int temp = Integer.parseInt(this.speedField.getText());
					// IT COULD BE OVER 9000!!!
					if((temp > 0 && temp <= 70) || temp == 9001) 
					{
						if(temp == 9001)
							temp = 70;
						_ctc.setSpeedLimit((String)lineBox.getSelectedItem(), Integer.parseInt((String)blocksBox.getSelectedItem()), temp);
						speedField.setText("");
						blocksBox.setSelectedIndex(0);
						blockActionsBox.setSelectedIndex(0);
					} 
					else 
					{
						speedField.setText("");
						log.append(3, "Speed Limit must be an integer s.t. 0 < x <= 70\n");
					}
				} 
				catch(Exception e) 
				{
					this.speedField.setText("");
					log.append(3, "Speed Limit must be an integer s.t. 0 < x <= 70\n");
				}
			}
			
		} 
		else 
		{
			log.append(3, "Must select a Block and a Block Action\n");
		}	
	}
	
	public void toggle(boolean onoff) 
	{
		blockActionsBox.setEnabled(onoff);
		trainBox.setEnabled(false);
		stationBox.setEnabled(false);
		trainActionsBox.setEnabled(onoff);
		blocksBox.setEnabled(onoff);
		trainButton.setEnabled(onoff);
		blockButton.setEnabled(onoff);
		speedField.setEnabled(false);
		authorityField.setEnabled(false);
	}
}
