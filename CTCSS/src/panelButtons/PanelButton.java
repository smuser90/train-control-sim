package panelButtons;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;

public class PanelButton extends JPanel {

	private JLabel lblNewLabel;
	private BevelBorder bb;
	private int xSize;
	private int ySize = 20;
	private boolean occupied = false;
	private boolean isHighLighted = false;
	private ArrayList<Integer> goingTo;
	private ArrayList<Integer> cameFrom;
	private GraphPanel parent = null;
	/**
	 * Create the panel.
	 */
	public PanelButton(String label, int x, int y) {
		lblNewLabel = new JLabel(label);
		xSize = 10 + lblNewLabel.getFontMetrics(getFont()).stringWidth(lblNewLabel.getText());
		//xSize = 20*label.length();
		//ySize = 20*label.length();
		bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
		setBorder(bb);
		setBounds(x, y, xSize, ySize);
		setLayout(null);
		goingTo = new ArrayList<Integer>();
		cameFrom = new ArrayList<Integer>();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				toggleOccupied();
				isHighLighted = true;
				parent.repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				toggleOccupied();
				isHighLighted = false;
				parent.repaint();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				buttonAction();
				parent.repaint();
			}
		});
		
		
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//buttonAction();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				toggleOccupied();
				isHighLighted = true;
				parent.repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				toggleOccupied();
				isHighLighted = false;
				parent.repaint();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				buttonAction();
				
				parent.repaint();
			}
		});
		lblNewLabel.setBounds(0, 0, xSize, ySize);
		add(lblNewLabel);
		
	}
	
	public void addGoingTo(int connection) {
		goingTo.add(connection);
	}
	
	public void addCameFrom(int connection) {
		cameFrom.add(connection);
	}
	protected ArrayList<Integer> goingTo() {
		return goingTo;
	}
	
	protected ArrayList<Integer> cameFrom() {
		return cameFrom;
	}
	
	public void buttonAction() {
		if(bb.getBevelType() == BevelBorder.RAISED) {
			parent.turnOffAll();
			bb = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
			setBorder(bb);
			isHighLighted = true;
		} else {
			bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
			setBorder(bb);
			isHighLighted = false;
		}
	}
	
	protected void toggleOccupied() {
		//occupied = !occupied;
		if(occupied) {
			setBackground(Color.GREEN);
		} else
			setBackground(new Color(240, 240, 240));
	}

	public boolean isHighLighted() {
		return this.isHighLighted;
	}
	
	public void setParent(GraphPanel gp) {
		parent = gp;
	}
	
	public void turnOff() {
		bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
		setBorder(bb);
		isHighLighted = false;
	}
}
