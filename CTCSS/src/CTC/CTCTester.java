package CTC;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import TrackModel.Block;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class CTCTester {

	private JFrame frame;
	private static CTCModule ctc;
	private static ArrayList<Block> myBlocks;
	private static ArrayList<Integer> tids;
	private Random r = new Random();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ctc = new CTCModule();
		myBlocks = new ArrayList<Block>();
		tids = new ArrayList<Integer>();
		/*for (int blockCount = 0; blockCount < 5; blockCount++) {
			Block blk = new Block(blockCount);
			//blk.setBlockNumber(blockCount);
			if(blockCount == 1)
				blk.setType(1);
			if(blockCount == 2)
				blk.setType(2);
			if(blockCount == 4)
				blk.setType(3);
			myBlocks.add(blk);
		}*/
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
		frame.setBounds(100, 100, 360, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = ctc.getPanel();
		panel.setBounds(10, 11, 300, 378);
		frame.getContentPane().add(panel);
		
		JButton btnGetTrack = new JButton("Get Track");
		btnGetTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ctc.setGLine(myBlocks);
			}
		});
		btnGetTrack.setBounds(10, 473, 89, 23);
		frame.getContentPane().add(btnGetTrack);
		
		JButton btnAddTrain = new JButton("Add Train");
		btnAddTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tids.add(r.nextInt(10));
				ctc.setGLTrains(tids);
			}
		});
		btnAddTrain.setBounds(109, 473, 89, 23);
		frame.getContentPane().add(btnAddTrain);
	}
}
