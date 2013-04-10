package Simulator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpeedDialog extends JDialog {
	private JTextField textField;
	private Simulator sim;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SpeedDialog dialog = new SpeedDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SpeedDialog(Simulator s) {
		sim = s;
		setTitle("Set Simulation Speed");
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(null);
		
		JButton btnSetSpeed = new JButton("Set Speed");
		btnSetSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int t = Integer.parseInt(textField.getText());
					if(t > 0 && t <= 10) {
						sim.setSimSpeed(t);
					} else {
						sim.setSimSpeed(-1);
					}
				} catch (NumberFormatException ex) {
					sim.setSimSpeed(-1);
				}
				hideSD();
			}
		});
		btnSetSpeed.setBounds(10, 78, 200, 23);
		getContentPane().add(btnSetSpeed);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideSD();
			}
		});
		btnCancel.setBounds(224, 78, 200, 23);
		getContentPane().add(btnCancel);
		
		JLabel lblEnterTheSimulation = new JLabel("Enter the Simulation Speed (1 for real time)");
		lblEnterTheSimulation.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterTheSimulation.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEnterTheSimulation.setBounds(10, 11, 414, 14);
		getContentPane().add(lblEnterTheSimulation);
		
		textField = new JTextField();
		textField.setBounds(110, 36, 212, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	private void hideSD() {
		textField.setText("");
		setVisible(false);
	}
}
