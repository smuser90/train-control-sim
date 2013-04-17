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
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SpeedDialog extends JDialog {
	JSlider slider;
	JLabel lblEnterTheSimulation;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SpeedDialog dialog = new SpeedDialog(null, 1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SpeedDialog(final Simulator s, int speed) {
		setTitle("Set Simulation Time");
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(null);
		
		JButton btnSetSpeed = new JButton("Set Time");
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
		if(speed == 1) {
			lblEnterTheSimulation = new JLabel("Real Time");
		} else {
			lblEnterTheSimulation = new JLabel(speed + "x Real Time");
		}
		lblEnterTheSimulation.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterTheSimulation.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEnterTheSimulation.setBounds(10, 11, 414, 14);
		getContentPane().add(lblEnterTheSimulation);
		
		slider = new JSlider();
		slider.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setSpeed(s);
				}
			}
		});
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(slider.getValue() == 1) {
					lblEnterTheSimulation.setText("Real Time");
				} else {
					lblEnterTheSimulation.setText(slider.getValue() + "x Real Time");
				}
			}
		});
		slider.setMinimum(1);
		slider.setMaximum(10);
		slider.setValue(speed);
		slider.setMinorTickSpacing(1);
		//slider.setPaintTicks(true);
		slider.setBounds(10, 36, 414, 23);
		getContentPane().add(slider);
	}
	
	private void hideSD() {
		setVisible(false);
	}
	
	private void setSpeed(Simulator s) {
		s.setSimSpeed(slider.getValue());
		hideSD();
	}
}
