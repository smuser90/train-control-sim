package TrainModel;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;

public class TMPanel extends JPanel
{
	public TMPanel()
	{
		setLayout(null);
		setBounds(100, 100, 650, 350);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 6, 638, 20);
		add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 38, 239, 284);
		add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton btnEngineFailur = new JButton("Engine Failure");
		btnEngineFailur.setBounds(282, 38, 117, 29);
		add(btnEngineFailur);
		
		JLabel engineStatus = new JLabel("Status - ");
		engineStatus.setBounds(292, 63, 61, 16);
		add(engineStatus);
		
		JButton btnSignalFailure = new JButton("Signal Failure");
		btnSignalFailure.setBounds(394, 38, 117, 29);
		add(btnSignalFailure);
		
		JLabel signalStatus = new JLabel("Status - ");
		signalStatus.setBounds(404, 63, 61, 16);
		add(signalStatus);
		
		JButton btnBrakeFailure = new JButton("Brake Failure");
		btnBrakeFailure.setBounds(505, 38, 117, 29);
		add(btnBrakeFailure);
		
		JLabel lblStatus = new JLabel("Status - ");
		lblStatus.setBounds(515, 63, 61, 16);
		add(lblStatus);
		
		JButton btnEmergencyBrake = new JButton("Emergency Brake");
		btnEmergencyBrake.setBounds(452, 90, 142, 29);
		add(btnEmergencyBrake);
		
		JLabel lblStatus_1 = new JLabel("Status - ");
		lblStatus_1.setBounds(486, 116, 61, 16);
		add(lblStatus_1);
		
		JButton Position = new JButton("Position: 12.1m");
		Position.setBounds(282, 145, 126, 61);
		add(Position);
		
		JButton btnVelocityms = new JButton("Velocity: 2.1m/s");
		btnVelocityms.setBackground(Color.CYAN);
		btnVelocityms.setBounds(405, 145, 126, 61);
		add(btnVelocityms);
		
		JButton btnSetpointms = new JButton("Setpoint: 2.5m/s");
		btnSetpointms.setBounds(527, 145, 117, 61);
		add(btnSetpointms);
		
		JButton btnAccelMs = new JButton("Doors: CLOSED");
		btnAccelMs.setBounds(527, 202, 117, 61);
		add(btnAccelMs);
		
		JButton btnLightsOn = new JButton("Lights: ON");
		btnLightsOn.setBounds(527, 261, 117, 61);
		add(btnLightsOn);
		
		JButton btnGrade = new JButton("Grade: 0.2");
		btnGrade.setBounds(404, 202, 127, 61);
		add(btnGrade);
		
		JButton btnCrew = new JButton("Crew: 4");
		btnCrew.setBounds(404, 261, 127, 61);
		add(btnCrew);
		
		JButton btnAccelms = new JButton("Accel: 0.8m/s2");
		btnAccelms.setBounds(282, 202, 126, 61);
		add(btnAccelms);
		
		JButton btnPassengers = new JButton("Passengers: 28");
		btnPassengers.setBounds(282, 261, 126, 61);
		add(btnPassengers);
		
		JButton btnMasskg = new JButton("Mass: 8000Kg");
		btnMasskg.setBounds(282, 85, 126, 61);
		add(btnMasskg);
	}
}