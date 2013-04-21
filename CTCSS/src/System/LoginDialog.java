package System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent.KeyBinding;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginDialog extends JDialog {
	private JTextField textField;
	private JPasswordField passwordField;
	private boolean loggedInSuccess = false;
	
	public LoginDialog(final System_GUI sys) {
		setTitle("Login");
		setBounds(100, 100, 200, 125);
		getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblUsername.setBounds(10, 11, 60, 14);
		getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tryLogin(sys);
				}
			}
		});
		textField.setBounds(80, 11, 86, 17);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 36, 60, 14);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tryLogin(sys);
				}
			}
		});
		passwordField.setBounds(80, 36, 86, 17);
		getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryLogin(sys);
			}
		});
		btnNewButton.setBounds(20, 61, 71, 23);
		getContentPane().add(btnNewButton);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tryLogin(sys);
			}
		});
		btnNewButton_1.setBounds(95, 61, 71, 23);
		getContentPane().add(btnNewButton_1);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	private void tryLogin(System_GUI sys) {
		sys.setLoggedIn(Login.login(textField.getText(), new String(passwordField.getPassword())));
		setVisible(false);
	}
}
