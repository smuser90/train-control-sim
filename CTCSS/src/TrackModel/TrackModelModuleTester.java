package TrackModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TrackModelModuleTester {

	private JFrame frame;
	private static TrackModelModule tm;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		tm = new TrackModelModule();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackModelModuleTester window = new TrackModelModuleTester();
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
	public TrackModelModuleTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 845, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = tm.getPanel();
		panel.setBounds(10, 11, 809, 335);
		frame.getContentPane().add(panel);
	}
}
