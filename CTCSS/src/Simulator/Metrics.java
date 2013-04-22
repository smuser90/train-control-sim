package Simulator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Metrics extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			Metrics dialog = new Metrics(0.0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Metrics(double pph) 
	{
		setTitle("Metrics");
		setBounds(100, 100, 300, 100);
		getContentPane().setLayout(null);
		{
			JButton btnOk = new JButton("Ok");
			btnOk.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					setVisible(false);
				}
			});
			btnOk.setHorizontalTextPosition(SwingConstants.CENTER);
			btnOk.setBounds(100, 36, 89, 23);
			getContentPane().add(btnOk);
		}
		
		JLabel lblNewLabel = new JLabel("Passengers Per Hour: " + pph);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 264, 14);
		getContentPane().add(lblNewLabel);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
