/*
 * PanelButton.java
 * Custom button for use in GraphPanel
 * Author: Nikolas Parshook
 * Date Created: 04/19/2013
 * Date Last Updated: 04/21/2013
 */

package TrackDisplay;

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

/**
 * Custom button for use in GraphPanel
 * @author Nikolas Parshook
 *
 */
public class PanelButton extends JPanel 
{
	// Fields
	private JLabel lblNewLabel;
	private BevelBorder bb;
	private int xSize;
	private int ySize = 20;
	private boolean occupied = false;
	protected boolean isHighLighted = false;
	private ArrayList<Integer> goingTo;
	private ArrayList<Integer> cameFrom;
	private GraphPanel parent = null;
	
	/**
	 * Creates the button
	 * @param label String to be displayed on the button
	 * @param x x position
	 * @param y y position
	 */
	public PanelButton(String label, int x, int y) 
	{
		lblNewLabel = new JLabel(label);
		xSize = 10 + lblNewLabel.getFontMetrics(getFont()).stringWidth(lblNewLabel.getText());
		bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
		setBorder(bb);
		setBounds(x, y, xSize, ySize);
		setLayout(null);
		goingTo = new ArrayList<Integer>();
		cameFrom = new ArrayList<Integer>();
		
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, xSize, ySize);
		add(lblNewLabel);
		
	}
	
	/* Add a button index to the list of buttons this button goes to */
	protected void addGoingTo(int connection) 
	{
		goingTo.add(connection);
	}
	
	/* Add a button index to the list of buttons that go to this button */
	protected void addCameFrom(int connection) 
	{
		cameFrom.add(connection);
	}
	
	/* Return the list of button indices that this button goes to */
	protected ArrayList<Integer> goingTo() 
	{
		return goingTo;
	}
	
	/* Return the list of button indices that this button came from */
	protected ArrayList<Integer> cameFrom() 
	{
		return cameFrom;
	}
	
	/* Make the panel act like a button */
	protected void buttonAction() 
	{
		if(bb.getBevelType() == BevelBorder.RAISED) 
		{
			parent.turnOffAll();
			bb = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
			setBorder(bb);
			//isHighLighted = true;
		} else {
			bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
			setBorder(bb);
			//isHighLighted = false;
		}
	}
	
	/* Toggles the occupied state of this button */
	public void toggleOccupied(boolean trainPresent) 
	{
		occupied = trainPresent;
		if(occupied) 
		{
			setBackground(Color.GREEN);
		} else
			setBackground(new Color(240, 240, 240));
	}
	
	/* Sets the parent GraphPanel of this button */
	protected void setParent(GraphPanel gp) 
	{
		parent = gp;
	}
	
	/* Make the button unselected */
	protected void turnOff() 
	{
		bb = new BevelBorder(BevelBorder.RAISED, null, null, null, null);
		setBorder(bb);
		isHighLighted = false;
	}
	
	/* Return wether this button is highlighed or not */
	protected boolean isHighLighted() {
		if(bb.getBevelType() == BevelBorder.LOWERED) {
			return true;
		}
		return isHighLighted;
	}
	
	public void addListener(MouseAdapter ma)
	{
		addMouseListener(ma);
		lblNewLabel.addMouseListener(ma);
	}
	
	public void repaint()
	{
		if(parent != null)
			parent.repaint();
	}
}
