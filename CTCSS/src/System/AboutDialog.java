package System;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle("About");
		setBounds(100, 100, 465, 260);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(320, 161, 119, 50);
		getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 300, 200);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("images/splash.png"));
		lblNewLabel.setBounds(0, 0, 300, 200);
		panel.add(lblNewLabel);
		
		JLabel lblNikolasParshook = new JLabel("Nikolas Parshook");
		lblNikolasParshook.setBounds(320, 36, 104, 14);
		getContentPane().add(lblNikolasParshook);
		
		JLabel lblTeam = new JLabel("Team");
		lblTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeam.setBounds(320, 11, 104, 14);
		getContentPane().add(lblTeam);
		
		JLabel lblChalesMusso = new JLabel("Charles Musso");
		lblChalesMusso.setBounds(320, 61, 104, 14);
		getContentPane().add(lblChalesMusso);
		
		JLabel lblZacharyShelhamer = new JLabel("Zachary Shelhamer");
		lblZacharyShelhamer.setBounds(320, 86, 119, 14);
		getContentPane().add(lblZacharyShelhamer);
		
		JLabel lblKaiManuel = new JLabel("Kai Manuel");
		lblKaiManuel.setBounds(320, 111, 104, 14);
		getContentPane().add(lblKaiManuel);
		
		JLabel lblKeLuo = new JLabel("Ke Luo");
		lblKeLuo.setBounds(320, 136, 104, 14);
		getContentPane().add(lblKeLuo);
		setVisible(true);
	}
}
