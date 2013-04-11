package TrainModel;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
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
	private TrainModelModule m_tm;
	
	private JComboBox comboBox;
	private JScrollPane scrollPane;
	private JTextPane textPane;
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
	
	public TMPanel(TrainModelModule tm)
	{
		m_tm = tm;
		
		setLayout(null);
		setBounds(100, 100, 650, 350);
		
		comboBox = new JComboBox();
		comboBox.setBounds(6, 6, 638, 20);	
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Trains"}));
		add(comboBox);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 38, 239, 284);
		add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		btnEngineFailure = new JButton("Engine Failure");
		btnEngineFailure.setBounds(282, 38, 117, 29);
		btnEngineFailure.setEnabled(false);
		btnEngineFailure.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
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
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				
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
				Map<Integer, TrainModel> trainList = m_tm.getTrainList();
				
			}
		});
		add(btnEmergencyBrake);
		
		eBrakeStatus = new JLabel("Status - ");
		eBrakeStatus.setBounds(486, 116, 61, 16);
		add(eBrakeStatus);
		
		position = new JButton("Position: ");
		position.setBounds(282, 145, 126, 61);
		add(position);
		
		velocity = new JButton("Velocity: ");
		velocity.setBounds(405, 145, 126, 61);
		add(velocity);
		
		setpoint = new JButton("Setpoint: ");
		setpoint.setBounds(527, 145, 117, 61);
		add(setpoint);
		
		doors = new JButton("Doors: ");
		doors.setBounds(527, 202, 117, 61);
		add(doors);
		
		lights = new JButton("Lights: ");
		lights.setBounds(527, 261, 117, 61);
		add(lights);
		
		grade = new JButton("Grade: ");
		grade.setBounds(404, 202, 127, 61);
		add(grade);
		
		crew = new JButton("Crew: ");
		crew.setBounds(404, 261, 127, 61);
		add(crew);
		
		acceleration = new JButton("Accel: ");
		acceleration.setBounds(282, 202, 126, 61);
		add(acceleration);
		
		passengers = new JButton("Passengers: ");
		passengers.setBounds(282, 261, 126, 61);
		add(passengers);
		
		mass = new JButton("Mass: ");
		mass.setBounds(282, 85, 126, 61);
		add(mass);
	}
	
	public void update()
	{
		Set<Integer> keys = m_tm.getTrainIDS();
		Map<Integer, TrainModel> trainList = m_tm.getTrainList();
		int numKeys = keys.size();
		
		Iterator<Integer> it = keys.iterator();
		for(int i = 0; i < numKeys; i++)
		{
			comboBox.addItem(it.next().toString());
		}
		
		if(comboBox.getSelectedItem().equals("Trains"))
			reset();
		else
		{
			int trainID = Integer.parseInt(comboBox.getSelectedItem().toString());
			TrainModel train = trainList.get(new Integer(trainID));
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
		}
		
	}
	
	public void reset()
	{
		position.setText("Position: ");
		velocity.setText("Velocity: ");
		setpoint.setText("Setpoint: ");
		acceleration.setText("Acceleration: ");
		lights.setText("Lights: ");
		grade.setText("Grade: ");
		crew.setText("Crew: ");
		doors.setText("Doors: ");
		passengers.setText("Passengers: ");
		mass.setText("Mass: ");
		engineStatus.setText("Status - ");
		signalStatus.setText("Status - ");
		brakeStatus.setText("Status - ");
	}
}