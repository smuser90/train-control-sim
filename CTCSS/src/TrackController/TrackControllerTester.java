package TrackController;

import TrackModel.Block;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrackControllerTester {

	private JFrame frame;
	private static TrackControllerModule trc;
	public static ArrayList<Block> myBlocks;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		trc = new TrackControllerModule();
		myBlocks = new ArrayList<Block>();
		
		/*
		for (int blockCount = 0; blockCount < 5; blockCount++) {
			Block blk = new Block(blockCount);
			//blk.setBlockNumber(blockCount);
			if(blockCount == 1)
				blk.setType(1);
			if(blockCount == 2)
				blk.setType(2);
			myBlocks.add(blk);
		}
		*/
		
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
		frame.setBounds(100, 100, 680, 504);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnTick = new JButton("Tick");
		btnTick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trc.receiveTick();
			}
		});
		btnTick.setBounds(10, 432, 89, 23);
		frame.getContentPane().add(btnTick);
		
		JPanel panel = trc.getPanel();
		panel.setBounds(0, 0, 650, 350);
		frame.getContentPane().add(panel);
		
		JButton btnGettrack = new JButton("GetTrack");
		btnGettrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trc.getTrack(myBlocks);
			}
		});
		btnGettrack.setBounds(109, 432, 89, 23);
		frame.getContentPane().add(btnGettrack);
		
		JButton btnAddtrain = new JButton("AddTrain");
		btnAddtrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myBlocks.get(1).setOccupied(true);
			}
		});
		btnAddtrain.setBounds(208, 432, 89, 23);
		frame.getContentPane().add(btnAddtrain);
		//waitr(3);
		//TrackControllerPanel.waiting(2);
		
	}

	public static ArrayList<Block> getBlockList() {
		// Create a list of 100 blocks for stand alone testing *TESTING*
				
		return myBlocks;
	}
}
