package Simulator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpeedDialog extends JDialog {
	private JTextField textField;
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
	public SpeedDialog(final Simulator s) {
		setTitle("Set Simulation Speed");
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(null);
		
		JButton btnSetSpeed = new JButton("Set Speed");
		btnSetSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpeed(s);
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
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setSpeed(s);
				}
			}
		});
		textField.setBounds(110, 36, 212, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	private void hideSD() {
		textField.setText("");
		setVisible(false);
	}
	
	private void setSpeed(Simulator s) {
		try {
			int t = Integer.parseInt(textField.getText());
			if(t > 0 && t <= 10) {
				s.setSimSpeed(t);
			} else {
				s.setSimSpeed(-1);
			}
		} catch (NumberFormatException ex) {
			s.setSimSpeed(-1);
		}
		hideSD();
	}
}
