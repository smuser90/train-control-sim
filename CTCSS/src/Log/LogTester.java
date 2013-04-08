package Log;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogTester {

	private JFrame frame;
	private JTextField textField;
	private static Log l = null;
	private JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					l = Log.Instance();
					LogTester window = new LogTester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 182);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Normal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				l.append(0, textField.getText() + "\n");
				textField.setText(null);
			}
		});
		btnNewButton.setBounds(10, 113, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Medium");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.append(1, textField.getText() + "\n");
				textField.setText(null);
			}
		});
		btnNewButton_1.setBounds(109, 113, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("High");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.append(2, textField.getText() + "\n");
				textField.setText(null);
			}
		});
		btnNewButton_2.setBounds(208, 113, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(406, 114, 527, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		panel = l.getPanel();
		panel.setBounds(0, 0, 943, 102);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = l.getPanel();;
		panel_1.setBounds(0, 0, 964, 102);
		frame.getContentPane().add(panel_1);
		
		JButton btnWriteOut = new JButton("Write Out");
		btnWriteOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.writeToFile(textField.getText());
				textField.setText(null);
			}
		});
		btnWriteOut.setBounds(307, 113, 89, 23);
		frame.getContentPane().add(btnWriteOut);
	}
}
