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
import java.awt.Font;
import java.awt.Color;

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
		getContentPane().setBackground(Color.WHITE);
		setTitle("About");
		setBounds(100, 100, 465, 239);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(320, 161, 109, 29);
		getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 300, 200);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(getClass().getResource("images/splash.png")));
		lblNewLabel.setBounds(0, 0, 300, 200);
		panel.add(lblNewLabel);
		
		JLabel lblNikolasParshook = new JLabel("Nikolas Parshook");
		lblNikolasParshook.setHorizontalAlignment(SwingConstants.CENTER);
		lblNikolasParshook.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNikolasParshook.setFont(new Font("Dialog", Font.BOLD, 10));
		lblNikolasParshook.setBounds(320, 36, 109, 14);
		getContentPane().add(lblNikolasParshook);
		
		JLabel lblTeam = new JLabel("Team");
		lblTeam.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeam.setBounds(320, 11, 104, 14);
		getContentPane().add(lblTeam);
		
		JLabel lblChalesMusso = new JLabel("Charles Musso");
		lblChalesMusso.setHorizontalAlignment(SwingConstants.CENTER);
		lblChalesMusso.setFont(new Font("Dialog", Font.BOLD, 10));
		lblChalesMusso.setBounds(320, 61, 109, 14);
		getContentPane().add(lblChalesMusso);
		
		JLabel lblZacharyShelhamer = new JLabel("Zachary Shelhamer");
		lblZacharyShelhamer.setHorizontalAlignment(SwingConstants.CENTER);
		lblZacharyShelhamer.setFont(new Font("Dialog", Font.BOLD, 10));
		lblZacharyShelhamer.setBounds(320, 86, 109, 14);
		getContentPane().add(lblZacharyShelhamer);
		
		JLabel lblKaiManuel = new JLabel("Kai Manuel");
		lblKaiManuel.setHorizontalAlignment(SwingConstants.CENTER);
		lblKaiManuel.setFont(new Font("Dialog", Font.BOLD, 10));
		lblKaiManuel.setBounds(320, 111, 109, 14);
		getContentPane().add(lblKaiManuel);
		
		JLabel lblKeLuo = new JLabel("Ke Luo");
		lblKeLuo.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeLuo.setFont(new Font("Dialog", Font.BOLD, 10));
		lblKeLuo.setBounds(320, 136, 109, 14);
		getContentPane().add(lblKeLuo);
		
		JLabel lblCtc = new JLabel("CTC");
		lblCtc.setHorizontalAlignment(SwingConstants.CENTER);
		lblCtc.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblCtc.setBounds(320, 47, 109, 10);
		getContentPane().add(lblCtc);
		
		JLabel lblTrainModel = new JLabel("Train Model");
		lblTrainModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainModel.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblTrainModel.setBounds(320, 72, 109, 10);
		getContentPane().add(lblTrainModel);
		
		JLabel lblTrackController = new JLabel("Track Controller");
		lblTrackController.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrackController.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblTrackController.setBounds(320, 97, 114, 10);
		getContentPane().add(lblTrackController);
		
		JLabel lblTrackModel = new JLabel("Track Model");
		lblTrackModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrackModel.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblTrackModel.setBounds(320, 121, 109, 10);
		getContentPane().add(lblTrackModel);
		
		JLabel lblTrainModel_1 = new JLabel("Train Model");
		lblTrainModel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainModel_1.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblTrainModel_1.setBounds(320, 147, 109, 10);
		getContentPane().add(lblTrainModel_1);
		setVisible(true);
	}
}
