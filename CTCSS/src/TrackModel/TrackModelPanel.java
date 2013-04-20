package TrackModel;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TrackModelPanel extends JPanel {
	private File file;
	private TrackModelModule tm;
	private JTabbedPane tabbedPane;
	/**
	 * Create the panel.
	 */
	public TrackModelPanel(final TrackModelModule Tm) {
		setLayout(null);
		tm = Tm;
		JTextPane txtpnTheTextPane = new JTextPane();
		txtpnTheTextPane.setText("Selected Block Information");
		txtpnTheTextPane.setBounds(10, 259, 630, 46);
		add(txtpnTheTextPane);
		
		final JFileChooser jfc = new JFileChooser();
		
		JButton btnNewButton = new JButton("Load File...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent x) {
				int result = jfc.showOpenDialog(null);
				if (result==JFileChooser.APPROVE_OPTION) {
                    file = jfc.getSelectedFile();
                    tm.getLineFile(file);
				}
			
			}
		});
		btnNewButton.setBounds(10, 316, 200, 23);
		add(btnNewButton);
		
		tabbedPane = null;

	}
	
	protected void addLine(Line l) {
		if(tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 11, 630, 237);
			add(tabbedPane);
		}
		tabbedPane.addTab(l.getName(), null, l.getPanel(), null);
		
	}
}
