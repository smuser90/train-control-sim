package TrainModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.Color;

public class TrainModelGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelGUI frame = new TrainModelGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrainModelGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 6, 638, 20);
		contentPane.add(comboBox);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(16, 38, 239, 284);
		contentPane.add(textPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(255, 38, 15, 284);
		contentPane.add(scrollBar);
		
		JButton btnEngineFailur = new JButton("Engine Failure");
		btnEngineFailur.setBounds(282, 38, 117, 29);
		contentPane.add(btnEngineFailur);
		
		JLabel engineStatus = new JLabel("Status - ");
		engineStatus.setBounds(292, 63, 61, 16);
		contentPane.add(engineStatus);
		
		JButton btnSignalFailure = new JButton("Signal Failure");
		btnSignalFailure.setBounds(394, 38, 117, 29);
		contentPane.add(btnSignalFailure);
		
		JLabel signalStatus = new JLabel("Status - ");
		signalStatus.setBounds(404, 63, 61, 16);
		contentPane.add(signalStatus);
		
		JButton btnBrakeFailure = new JButton("Brake Failure");
		btnBrakeFailure.setBounds(505, 38, 117, 29);
		contentPane.add(btnBrakeFailure);
		
		JLabel lblStatus = new JLabel("Status - ");
		lblStatus.setBounds(515, 63, 61, 16);
		contentPane.add(lblStatus);
		
		JButton btnEmergencyBrake = new JButton("Emergency Brake");
		btnEmergencyBrake.setBounds(452, 90, 142, 29);
		contentPane.add(btnEmergencyBrake);
		
		JLabel lblStatus_1 = new JLabel("Status - ");
		lblStatus_1.setBounds(486, 116, 61, 16);
		contentPane.add(lblStatus_1);
		
		JButton Position = new JButton("Position: 12.1m");
		Position.setBounds(282, 145, 126, 61);
		contentPane.add(Position);
		
		JButton btnVelocityms = new JButton("Velocity: 2.1m/s");
		btnVelocityms.setBackground(Color.CYAN);
		btnVelocityms.setBounds(405, 145, 126, 61);
		contentPane.add(btnVelocityms);
		
		JButton btnSetpointms = new JButton("Setpoint: 2.5m/s");
		btnSetpointms.setBounds(527, 145, 117, 61);
		contentPane.add(btnSetpointms);
		
		JButton btnAccelMs = new JButton("Doors: CLOSED");
		btnAccelMs.setBounds(527, 202, 117, 61);
		contentPane.add(btnAccelMs);
		
		JButton btnLightsOn = new JButton("Lights: ON");
		btnLightsOn.setBounds(527, 261, 117, 61);
		contentPane.add(btnLightsOn);
		
		JButton btnGrade = new JButton("Grade: 0.2");
		btnGrade.setBounds(404, 202, 127, 61);
		contentPane.add(btnGrade);
		
		JButton btnCrew = new JButton("Crew: 4");
		btnCrew.setBounds(404, 261, 127, 61);
		contentPane.add(btnCrew);
		
		JButton btnAccelms = new JButton("Accel: 0.8m/s2");
		btnAccelms.setBounds(282, 202, 126, 61);
		contentPane.add(btnAccelms);
		
		JButton btnPassengers = new JButton("Passengers: 28");
		btnPassengers.setBounds(282, 261, 126, 61);
		contentPane.add(btnPassengers);
		
		JButton btnMasskg = new JButton("Mass: 8000Kg");
		btnMasskg.setBounds(282, 85, 126, 61);
		contentPane.add(btnMasskg);

	}
}
