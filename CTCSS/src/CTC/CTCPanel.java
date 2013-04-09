package CTC;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class CTCPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public CTCPanel() {
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 300, 378);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("CTC Office", null, panel, null);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Select Line");
		label.setBounds(10, 11, 56, 14);
		panel.add(label);
		
		JButton button = new JButton("Perform Train Action");
		button.setBounds(10, 286, 131, 23);
		panel.add(button);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"List of Blocks"}));
		comboBox.setBounds(10, 85, 105, 20);
		panel.add(comboBox);
		
		JLabel label_1 = new JLabel("Block Actions");
		label_1.setBounds(10, 60, 75, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Set Speed Limit");
		label_2.setBounds(39, 116, 86, 14);
		panel.add(label_2);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"List of Block Actions"}));
		comboBox_1.setBounds(125, 85, 119, 20);
		panel.add(comboBox_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(135, 116, 86, 20);
		panel.add(textField);
		
		JButton button_1 = new JButton("Perform Block Action");
		button_1.setBounds(10, 140, 131, 23);
		panel.add(button_1);
		
		JLabel label_3 = new JLabel("Train Actions");
		label_3.setBounds(10, 174, 75, 14);
		panel.add(label_3);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"List of Trains"}));
		comboBox_2.setBounds(10, 199, 105, 20);
		panel.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"List of Stations"}));
		comboBox_3.setBounds(10, 230, 105, 20);
		panel.add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"List of Train Actions"}));
		comboBox_4.setBounds(125, 199, 119, 20);
		panel.add(comboBox_4);
		
		JLabel label_4 = new JLabel("Set Authority");
		label_4.setBounds(46, 261, 69, 14);
		panel.add(label_4);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(125, 258, 86, 20);
		panel.add(textField_1);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"List of Lines"}));
		comboBox_5.setBounds(10, 29, 95, 20);
		panel.add(comboBox_5);

	}
}
