package CTC;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CTCTester {

	private JFrame frame;
	private static CTCModule ctc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ctc = new CTCModule();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CTCTester window = new CTCTester();
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
	public CTCTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 360, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = ctc.getPanel();
		panel.setBounds(10, 11, 300, 378);
		frame.getContentPane().add(panel);
	}
}
