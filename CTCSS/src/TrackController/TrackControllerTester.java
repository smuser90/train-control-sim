package TrackController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TrackControllerTester {

	private JFrame frame;
	private static TrackControllerModule trc;
	public static ArrayList<Block> myBlocks = new ArrayList<Block>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		for (int blockCount = 0; blockCount < 4; blockCount++) {
			Block blk = new Block(blockCount, false, false);
			myBlocks.add(blk);
		}
		trc = new TrackControllerModule();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackControllerTester window = new TrackControllerTester();
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
	public TrackControllerTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = trc.getPanel();
		panel.setBounds(0, 0, 650, 350);
		frame.getContentPane().add(panel);
		//waitr(3);
		//TrackControllerPanel.waiting(2);
		
	}

	public static ArrayList<Block> getBlockList() {
		// Create a list of 100 blocks for stand alone testing *TESTING*
				
		return myBlocks;
	}
	
}
