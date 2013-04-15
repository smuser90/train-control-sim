package TrainModel;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
	private boolean 			locked = true;
	private JTable				table;
	private JComboBox 			comboBox;
	private JScrollPane scrollPane;
	private JTextPane logPane;
	private JButton btnEngineFailure;
	private JLabel engineStatus;
	private JButton btnSignalFailure;
	private JLabel signalStatus;
	private JButton btnBrakeFailure;
	private JLabel brakeStatus;
	private JButton btnEmergencyBrake;
	private JLabel eBrakeStatus;
	private JButton position;
	private JButton velocity;
	private JButton setpoint;
	private JButton acceleration;
	private JButton lights;
	private JButton grade;
	private JButton crew;
	private JButton doors;
	private JButton passengers;
	private JButton mass;
	private int trainSelected = 0;
	
	public TMPanel(TrainModelModule tm)
	{
		m_tm = tm;
		
		setLayout(null);
		setBounds(100, 100, 650, 350);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.WHITE);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.DARK_GRAY);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Block", "N/A"},
				{"Position", "N/A"},
				{"Speed Limit", "N/A"},
				{"Setpoint", "N/A"},
				{"Velocity", "N/A"},
				{"Acceleration","N/A"},
				{"Authority","N/A"},
				{"Grade","N/A"},
				{"Doors","N/A"},
				{"Lights","N/A"},
				{"Passengers","0"},
				{"Crew","0"},
			},
			new String[] {
				"Attribute", "Value"
			}
		) 
		{
			private static final long serialVersionUID = 687140540938402495L;
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		table.setBounds(282, 50, 360, 192);
		add(table);
		
		comboBox = new JComboBox();
		comboBox.setBounds(5, 5, 640, 30);	
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				update();
			}
		});
		add(comboBox);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 239, 284);
		add(scrollPane);
		
		logPane = new JTextPane();
		scrollPane.setViewportView(logPane);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e){
				if(locked)
					e.getAdjustable().setValue(e.getAdjustable().getMaximum());
				else
					e.getAdjustable().setValue(e.getValue());
		}	});
		btnEngineFailure = new JButton("Engine Failure");
		btnEngineFailure.setBounds(282, 38, 117, 29);
		btnEngineFailure.setEnabled(false);
		btnEngineFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
			}
		});
		add(btnEngineFailure);
		
		engineStatus = new JLabel("Status - ");
		engineStatus.setBounds(292, 63, 61, 16);
		add(engineStatus);
		
		btnSignalFailure = new JButton("Signal Failure");
		btnSignalFailure.setBounds(394, 38, 117, 29);
		btnSignalFailure.setEnabled(false);
		btnSignalFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				
			}
		});
		add(btnSignalFailure);
		
		signalStatus = new JLabel("Status - ");
		signalStatus.setBounds(404, 63, 61, 16);
		add(signalStatus);
		
		btnBrakeFailure = new JButton("Brake Failure");
		btnBrakeFailure.setBounds(505, 38, 117, 29);
		btnBrakeFailure.setEnabled(false);
		btnBrakeFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				TrainModel train = trainList.get(new Integer(trainSelected));
				train.toggleBrakeFailure();
				
			}
		});
		add(btnBrakeFailure);
		
		brakeStatus = new JLabel("Status - ");
		brakeStatus.setBounds(515, 63, 61, 16);
		add(brakeStatus);
		
		btnEmergencyBrake = new JButton("Emergency Brake");
		btnEmergencyBrake.setBounds(452, 90, 142, 29);
		btnEmergencyBrake.setEnabled(false);
		btnEmergencyBrake.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				
			}
		});
		add(btnEmergencyBrake);
		
		eBrakeStatus = new JLabel("Status - ");
		eBrakeStatus.setBounds(486, 116, 61, 16);
		add(eBrakeStatus);
		
		position = new JButton("Position: ");
		position.setEnabled(false);
		position.setBounds(282, 145, 140, 61);
		add(position);
		
		velocity = new JButton("Velocity: ");
		velocity.setEnabled(false);
		velocity.setBounds(405, 145, 140, 61);
		add(velocity);
		
		setpoint = new JButton("Setpoint: ");
		setpoint.setEnabled(false);
		setpoint.setBounds(527, 145, 117, 61);
		add(setpoint);
		
		doors = new JButton("Doors: ");
		doors.setEnabled(false);
		doors.setBounds(527, 202, 117, 61);
		add(doors);
		
		lights = new JButton("Lights: ");
		lights.setEnabled(false);
		lights.setBounds(527, 261, 117, 61);
		add(lights);
		
		grade = new JButton("Grade: ");
		grade.setEnabled(false);
		grade.setBounds(418, 202, 127, 61);
		add(grade);
		
		crew = new JButton("Crew: ");
		crew.setEnabled(false);
		crew.setBounds(404, 261, 127, 61);
		add(crew);
		
		acceleration = new JButton("Accel: ");
		acceleration.setEnabled(false);
		acceleration.setBounds(282, 202, 140, 61);
		add(acceleration);
		
		passengers = new JButton("Passengers: ");
		passengers.setEnabled(false);
		passengers.setBounds(282, 261, 126, 61);
		add(passengers);
		
		mass = new JButton("Mass: ");
		mass.setEnabled(false);
		mass.setBounds(282, 85, 126, 61);
		add(mass);
		
		reset();
	}
	
	public void toggleLock()
	{
		locked = !locked;
	}
	
	public void update()
	{
		Set<Integer> keys = m_tm.getTrainIDS();
		Map<Integer, TrainModel> trainList = m_tm.getTrainList();
		int numKeys = keys.size();
		
		if(comboBox.getSelectedItem().equals("Trains"))
			reset();
		else
		{
			/*
			position.setVisible(true);
			velocity.setVisible(true);
			setpoint.setVisible(true);
			acceleration.setVisible(true);
			lights.setVisible(true);
			grade.setVisible(true);
			crew.setVisible(true);
			doors.setVisible(true);
			passengers.setVisible(true);
			mass.setVisible(true);
			engineStatus.setVisible(true);
			signalStatus.setVisible(true);
			brakeStatus.setVisible(true);
			eBrakeStatus.setVisible(true);
			btnEngineFailure.setVisible(true);
			btnSignalFailure.setVisible(true);
			btnBrakeFailure.setVisible(true);
			btnEmergencyBrake.setVisible(true);
			*/
			logPane.setVisible(true);
			scrollPane.setVisible(true);
			int trainID = Integer.parseInt(comboBox.getSelectedItem().toString());
			trainSelected = trainID;
			TrainModel train = trainList.get(new Integer(trainID));
			String log = train.getLog().toString();
			position.setText("Position: "+train.getPosition());
			velocity.setText("Velocity: "+train.getVelocity());
			setpoint.setText("Setpoint: "+train.getSetpointSpeed());
			acceleration.setText("Acceleration: "+train.getAcceleration());
			lights.setText("Lights: "+train.getLights());
			grade.setText("Grade: "+train.getGrade());
			crew.setText("Crew: "+train.getCrew());
			doors.setText("Doors: "+train.getDoors());
			passengers.setText("Passengers: "+train.getPassengers());
			mass.setText("Mass: "+train.getMass());
			engineStatus.setText("Status - "+train.getEngineStatus());
			signalStatus.setText("Status - "+train.getEngineStatus());
			brakeStatus.setText("Status - "+train.getBrakeStatus());
			btnEngineFailure.setEnabled(true);
			btnSignalFailure.setEnabled(true);
			btnBrakeFailure.setEnabled(true);
			btnEmergencyBrake.setEnabled(true);
			writeLog(log);
		}
	}
	
	public void populateBox()
	{
		Set<Integer> keys = m_tm.getTrainIDS();
		Map<Integer, TrainModel> trainList = m_tm.getTrainList();
		int numKeys = keys.size();
		int listSize = comboBox.getItemCount();
		
		for(int i = 1; i < listSize; i++)
		{
			System.out.println("Items In Box: "+listSize);
			comboBox.remove(i);
		}
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		Iterator<Integer> it = keys.iterator();
		for(int i = 0; i < numKeys; i++)
		{
			int key = it.next();
			if(trainList.get(key) != null)
				comboBox.addItem(""+key);
		}
		
	}
	public void reset()
	{
		position.setVisible(false);
		velocity.setVisible(false);
		setpoint.setVisible(false);
		acceleration.setVisible(false);
		lights.setVisible(false);
		grade.setVisible(false);
		crew.setVisible(false);
		doors.setVisible(false);
		passengers.setVisible(false);
		mass.setVisible(false);
		engineStatus.setVisible(false);
		signalStatus.setVisible(false);
		brakeStatus.setVisible(false);
		eBrakeStatus.setVisible(false);
		btnEngineFailure.setVisible(false);
		btnSignalFailure.setVisible(false);
		btnBrakeFailure.setVisible(false);
		btnEmergencyBrake.setVisible(false);
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
		} catch (BadLocationException e) {
		}
		
	}
}