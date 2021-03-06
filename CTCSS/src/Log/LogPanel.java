/*
 * LogPanel.java
 * Displays the log in a custom JPanel
 * Author: Nikolas Parshook
 * Date Created: 04/07/2013
 * Date Last Updated: 04/21/2013
 */

package Log;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.Rectangle;

/**
 * JPanel for displaying the log
 * @author Optimus
 *
 */
public class LogPanel extends JPanel 
{

	private JTabbedPane tabbedPane = null;
	private JPanel panel = null;
	private JScrollPane scrollPane = null;
	private JTextPane textPane = null;
	
	/**
	 * Create the panel.
	 */
	public LogPanel() 
	{
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 964, 130);
		add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("Log", null, panel, null);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 960, 102);
		panel.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(new Rectangle(0, 0, 960, 102));
		scrollPane.setViewportView(textPane);

	}
	
	/**
	 * Append to the logs display
	 * @param severity
	 * @param s
	 */
	public void append(int severity, String s) 
	{
		Color c = null;
		switch (severity) 
		{
		case 1:
			c = Color.GREEN;
			break;
		case 2:
			c = Color.BLUE;
			break;
		case 3:
			c = Color.RED;
			break;
		default:
			c = Color.BLACK;
		}
		textPane.setEditable(true);
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		int len = textPane.getDocument().getLength();
		textPane.setCaretPosition(len); // place caret at the end (with no selection)
		textPane.setCharacterAttributes(aset, false);
		textPane.replaceSelection(s); // there is no selection, so inserts at caret
		textPane.setEditable(false);
		textPane.selectAll();
		int x = textPane.getSelectionEnd();
		textPane.select(x,x);
	}
	
	/**
	 * Clears the display
	 */
	public void clear() 
	{
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(new Rectangle(0, 0, 960, 102));
		scrollPane.setViewportView(textPane);
	}
}
