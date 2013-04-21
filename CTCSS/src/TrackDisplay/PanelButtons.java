package TrackDisplay;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.border.EtchedBorder;

public class PanelButtons {

	private JFrame frame;
	private GraphPanel panel;
	private PanelButton pb1;
	private PanelButton pb2;
	private PanelButton pb3;
	private PanelButton pb4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelButtons window = new PanelButtons();
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
	public PanelButtons() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		panel = new GraphPanel();
		//panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(0, 0, 600, 190);
		frame.getContentPane().add(panel);
		SectionLocator sl = new SectionLocator(4, 600, 190);
		ArrayList<Point2D.Double> points = sl.getPoints();
		pb1 = new PanelButton("YARD", new Double(points.get(0).getX()).intValue() , new Double(points.get(0).getY()).intValue());
		pb2 = new PanelButton("AA", new Double(points.get(1).getX()).intValue() , new Double(points.get(1).getY()).intValue());
		pb3 = new PanelButton("BB", new Double(points.get(2).getX()).intValue() , new Double(points.get(2).getY()).intValue());
		pb4 = new PanelButton("C", new Double(points.get(3).getX()).intValue() , new Double(points.get(3).getY()).intValue());
		panel.addButton(pb1);
		panel.addButton(pb2);
		panel.addButton(pb3);
		panel.addButton(pb4);
		
		panel.addConnection(0, 1);
		panel.addConnection(1, 2);
		panel.addConnection(1, 3);
		panel.addConnection(2, 3);
		panel.addConnection(3, 0);
		
	}
}
