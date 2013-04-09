package TrackModel;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class TrackModelPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TrackModelPanel() {
		setLayout(null);
		
		JTextPane txtpnTheTextPane = new JTextPane();
		txtpnTheTextPane.setText("Selected Block Information");
		txtpnTheTextPane.setBounds(10, 259, 630, 46);
		add(txtpnTheTextPane);
		
		JButton btnNewButton = new JButton("Load File...");
		btnNewButton.setBounds(10, 316, 200, 23);
		add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 630, 237);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Red Line", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Green Line", null, panel_1, null);

	}
}
