/*
 * 	TMPanel.java
 * 	Train Model Panel - Contains all GUI elements and handles all GUI interaction
 * 	Author: Charles Musso
 * 	Date Created: 04/07/13
 * 	Date Last Updated: 4/25/13
 * 
 */

package TrainModel;

import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JScrollPane;

public class TMPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5672590130944164297L;

	private TrainModelModule 	m_tm;
	private JTable				table;
	private JComboBox 			comboBox;
	private JScrollPane 		scrollPane;
	private JTextPane 			logPane;
	private JButton 			btnEngineFailure;
	private JButton 			btnSignalFailure;
	private JButton 			btnBrakeFailure;
	private JButton 			btnEmergencyBrake;
	private int 				trainSelected = 0;
	private boolean 			writeLog = false;
	
	public TMPanel(TrainModelModule tm)
	{
		m_tm = tm;
		
		setLayout(null);
		setBounds(100, 100, 650, 350);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.BLACK);
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Block", ""},
				{"Position", ""},
				{"Speed Limit", ""},
				{"Setpoint", ""},
				{"Velocity", ""},
				{"Acceleration",""},
				{"Authority",""},
				{"Grade",""},
				{"Doors",""},
				{"Lights",""},
				{"Passengers",""},
				{"Crew",""},
				{"Engine",""},
				{"Signal",""},
				{"Brake",""},
				{"eBrake",""}
			},
			new String[] {
				"Attribute", "Value"
			})
		{
			private static final long serialVersionUID = -6149142910618364640L;

			public boolean isCellEditable(int row, int column)
		      {  
		          return false;  
		      }
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		table.setBounds(280, 50, 360, 260);
		table.setVisible(false);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(1).setCellRenderer( rightRenderer );
		add(table);
		
		comboBox = new JComboBox();
		comboBox.setBounds(5, 5, 640, 30);	
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				update();
				writeLog = true;
			}
		});
		add(comboBox);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 240, 300);
		add(scrollPane);
		
		logPane = new JTextPane();
		logPane.setEditable(false);
		scrollPane.setViewportView(logPane);
		
		
		btnEngineFailure = new JButton("Engine");
		btnEngineFailure.setBounds(278, 315, 80, 25);
		btnEngineFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				TrainModel train = trainList.get(new Integer(trainSelected));
				train.toggleEngineFailure();	
				update();
			}
		});
		add(btnEngineFailure);
		
		btnSignalFailure = new JButton("Signal");
		btnSignalFailure.setBounds(370, 315, 80, 25);
		btnSignalFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				TrainModel train = trainList.get(new Integer(trainSelected));
				train.toggleSignalFailure();				
				update();
			}
		});
		add(btnSignalFailure);
		
		btnBrakeFailure = new JButton("Brake");
		btnBrakeFailure.setBounds(468, 315, 80, 25);
		btnBrakeFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				TrainModel train = trainList.get(new Integer(trainSelected));
				train.toggleBrakeFailure();
				update();
			}
		});
		add(btnBrakeFailure);
		
		btnEmergencyBrake = new JButton("eBrake");
		btnEmergencyBrake.setBounds(560, 315, 80, 25);
		btnEmergencyBrake.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				TrainModel train = trainList.get(new Integer(trainSelected));
				train.toggleEmergencyBrake();
				update();
			}
		});
		add(btnEmergencyBrake);
		
		
		reset();
	}
	
	public void update()
	{
		Map<Integer, TrainModel> trainList = m_tm.getTrainList();
		
		if(comboBox.getSelectedItem().equals("Trains"))
		{
			//hide GUI elements
			reset();
		}
		else
		{		
			//Show GUI elements
			btnSignalFailure.setVisible(true);
			btnBrakeFailure.setVisible(true);
			btnEngineFailure.setVisible(true);
			btnEmergencyBrake.setVisible(true);
			table.setVisible(true);
			logPane.setVisible(true);
			scrollPane.setVisible(true);
			
			//Get Train
			String title = comboBox.getSelectedItem().toString();
			int parseIndex = title.indexOf(" ");
			int trainID = Integer.parseInt(title.substring(0,parseIndex));
			trainSelected = trainID;
			TrainModel train = trainList.get(new Integer(trainSelected));
			
			//Populate Table
			String format = "%3.3f";
			table.setValueAt(""+(train.getBlockIndex())+" / "+(train.getRouteLength()-1), 0, 1);
			table.setValueAt(String.format("%3.1f", train.getPosition())+" / "+train.getBlockLength(), 1, 1);
			table.setValueAt(new String(""+train.getSpeedLimit()), 2, 1);
			table.setValueAt(new String(""+train.getSetpointSpeed()), 3, 1);
			table.setValueAt(String.format(format,train.getVelocity()*3.6), 4, 1);
			table.setValueAt(String.format(format,train.getAcceleration()), 5, 1);
			table.setValueAt(new String(""+train.getAuthority()), 6, 1);
			table.setValueAt(String.format(format,train.getGrade()), 7, 1);
			table.setValueAt(new String(""+train.getPassengers()), 10, 1);
			table.setValueAt(new String(""+train.getCrew()), 11, 1);
			
			String str = "";
			boolean val = train.getDoors();
			if(val) str = "Open";
			else str = "Closed";
			table.setValueAt(str, 8, 1);
			val = train.getLights();
			if(val) str = "On";
			else str = "Off";
			table.setValueAt(str, 9, 1);
			val = train.getEngineStatus();
			if(val) str = "Failure";
			else str = "Functional";
			table.setValueAt(str, 12, 1);
			val = train.getSignalStatus();
			if(val) str = "Failure";
			else str = "Functional";
			table.setValueAt(str, 13, 1);
			val = train.getBrakeStatus();
			if(val) str = "Failure";
			else str = "Functional";
			table.setValueAt(str, 14, 1);
			val = train.getEmergencyBrake();
			if(val) str = "Engaged";
			else str = "Disengaged";
			table.setValueAt(str, 15, 1);
			
			//Write Out Log
			String log = train.getLog().toString();
			if(train.getWriteLog() || writeLog) writeLog(log);
			train.setWriteLog(false);
		}
	}
	
	public void populateBox()
	{
		Set<Integer> keys = m_tm.getTrainIDS();
		Map<Integer, TrainModel> trainList = m_tm.getTrainList();
		int numKeys = keys.size();
		
		String[] def = new String[1];
		def[0] = "Trains";
		comboBox.setModel(new DefaultComboBoxModel(def));
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		Iterator<Integer> it = keys.iterator();
		for(int i = 0; i < numKeys; i++)
		{
			int key = it.next();
			if(trainList.get(key) != null)
				comboBox.addItem(""+key+" - "+trainList.get(key).getLine());
		}
		
	}
	public void reset()
	{
		btnEngineFailure.setVisible(false);
		btnSignalFailure.setVisible(false);
		btnBrakeFailure.setVisible(false);
		btnEmergencyBrake.setVisible(false);
		table.setVisible(false);
		logPane.setVisible(false);
		scrollPane.setVisible(false);
		writeLog("");
		logPane.selectAll();
		int pos = logPane.getSelectionEnd();
		logPane.select(pos, pos);
	}
	
	public void writeLog(String log)
	{
		Document logText = logPane.getDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		try 
		{
			logText.remove(0, logText.getLength());
			StyleConstants.setFontFamily(set, "Monospace");
			StyleConstants.setFontSize(set, 12);
			StyleConstants.setBold(set, false);
			StyleConstants.setItalic(set, false);
			logText.insertString(0, log, set);
			logPane.selectAll();
			int x = logPane.getSelectionEnd();
			logPane.select(x,x);
		} catch (BadLocationException e) {
		}
		writeLog = false;
	}
}