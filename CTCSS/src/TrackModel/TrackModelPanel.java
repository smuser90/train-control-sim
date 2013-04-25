/*
 *		TrackModelPanel.java
 *		This is the Panel for displaying the Track and Selected Block information.
 *		Author: Kai Manuel
 *		Date Created: 04/07/2013
 *		Date Last Updated: 04/25/2013
 */

package TrackModel;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import TrackDisplay.GraphPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TrackModelPanel extends JPanel
{
	private File file;
	private TrackModelModule tm;
	private JTabbedPane tabbedPane;
	JTextPane txtpnTheTextPane = new JTextPane();

	/**
	 * Create the panel.
	 */
	public TrackModelPanel(final TrackModelModule Tm)
	{
		setLayout(null);
		tm = Tm;

		// txtpnTheTextPane.setEnabled(false);
		// use this to change the pane. set it editable to edit, then uneditable
		// for the display
		txtpnTheTextPane.setEditable(false);
		txtpnTheTextPane.setText("Selected Block Information");
		txtpnTheTextPane.setBounds(10, 259, 630, 46);
		add(txtpnTheTextPane);

		final JFileChooser jfc = new JFileChooser();

		JButton btnNewButton = new JButton("Load File...");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent x)
			{
				int result = jfc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					file = jfc.getSelectedFile();
					tm.getLineFile(file);
				}

			}
		});
		btnNewButton.setBounds(10, 316, 200, 23);
		add(btnNewButton);

		tabbedPane = null;

	}

	/**
	 * Displays the Block information for the selected Block
	 * 
	 * @param b
	 */
	public void displayBlockInfo(Block b)
	{
		StringBuilder s = new StringBuilder();
		String q = "";
		String z = "";
		txtpnTheTextPane.setEditable(true);
		// format: ID | Type | Length | Grade | Speed Limit | Section |
		// aboveGround | Station Name
		// bID nextB bType bLength bGrade bSpLim bSection belowGround
		// stationName
		if (b.isUnderground() == false)
		{
			q = "No";
		}
		else
		{
			q = "Yes";
		}

		if (b.getFailure() == false)
		{
			z = "Block Open";
		}
		else
		{
			z = "Block Closed";
		}

		if (b.getStationName().equals("NULL"))
		{
			s.append("Block ID: " + b.getBlockNumber() + " | Type: "
					+ b.getType() + " | Length: " + b.getLength() + " | Grade:"
					+ b.getGrade() + " | Speed Limit: " + b.getSpeedLimit()
					+ " | Section: " + b.getSection() + " | Underground? " + q
					+ " \n" + z);
		}
		else
		{
			s.append("Block ID: " + b.getBlockNumber() + " | Type: "
					+ b.getType() + " | Length: " + b.getLength() + " | Grade:"
					+ b.getGrade() + " | Speed Limit: " + b.getSpeedLimit()
					+ " | Section: " + b.getSection() + " | Underground? " + q
					+ " | Station: " + b.getStationName() + "\n" + z);
		}

		txtpnTheTextPane.setText(s.toString());
		txtpnTheTextPane.setEditable(false);
	}

	/**
	 * Adds a Line to the Panel
	 * 
	 * @param l
	 */
	protected void addLine(Line l)
	{
		if (tabbedPane == null)
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 11, 630, 237);
			add(tabbedPane);
		}
		tabbedPane.addTab(l.getName(), null, l.getPanel(), null);

	}

	/**
	 * Adds the Panel to the trackModelPanel display.
	 * 
	 * @param gp
	 * @param name
	 */
	public void setPanel(GraphPanel gp, String name)
	{
		int temp = tabbedPane.getSelectedIndex();

		tabbedPane.remove(temp);
		tabbedPane.insertTab(name, null, gp, null, temp);
		tabbedPane.setSelectedIndex(temp);
	}
}
