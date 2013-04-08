package Log;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class LogTextPane extends JTextPane {
	
	/**
	 * Create the panel.
	 */
	public LogTextPane() {
		super();

	}
	
	public void append(int severity, String s) {
		Color c = null;
		switch (severity) {
		case 1:
			c = Color.ORANGE;
			break;
		case 2:
			c = Color.RED;
			break;
		default:
			c = Color.BLACK;
		}
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		int len = getDocument().getLength();
		setCaretPosition(len); // place caret at the end (with no selection)
		setCharacterAttributes(aset, false);
		replaceSelection(s); // there is no selection, so inserts at caret
	}
}
