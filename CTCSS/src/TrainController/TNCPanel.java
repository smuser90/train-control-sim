package TrainController;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;
import Log.Log;

public class TNCPanel extends JPanel {
	private JTextField txtSetSpeed;
	private JTextField txtSetTemp;
	private JTextField txtNextStation;
	private JTable table;
	private JTextField textField;
	private String setSpeedInput;
	private String setTemp;
	private TrainController tnc;

	/**
	 * Create the panel.
	 */
	public TNCPanel() {
		
		tnc = getTrainController(001);
		
		setBorder(UIManager.getBorder("Button.border"));
		setLayout(null);
		setBounds(100, 100, 650, 350);
		
		txtSetSpeed = new JTextField();
		txtSetSpeed.setText("");
		txtSetSpeed.setBounds(438, 27, 74, 28);
		add(txtSetSpeed);
		txtSetSpeed.setColumns(10);
		txtSetSpeed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				setSpeedInput = txtSetSpeed.getText();
			}
		});
		
		JButton setSpeed = new JButton("Apply speed");
		setSpeed.setBounds(510, 28, 117, 29);
		add(setSpeed);
		setSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int setPointSpeed = Integer.parseInt(setSpeedInput);
				tnc.setSpeed(setPointSpeed);
				System.out.println("setSpeed" + setPointSpeed);
	//			log.append(0,  "Train Controller Module Loaded\n");
			}
		});
		
		
		JLabel lblSetSpeed = new JLabel("Set speed");
		lblSetSpeed.setBounds(365, 33, 61, 16);
		add(lblSetSpeed);
		
		JLabel lblNewLabel = new JLabel("Set temp");
		lblNewLabel.setBounds(365, 61, 61, 16);
		add(lblNewLabel);
		
		txtSetTemp = new JTextField();
		txtSetTemp.setText("set temp");
		txtSetTemp.setColumns(10);
		txtSetTemp.setBounds(438, 55, 74, 28);
		add(txtSetTemp);
		txtSetTemp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				setTemp = txtSetTemp.getText();
			}
		});
		
		JButton setTemp = new JButton("Apply temp");
		setTemp.setBounds(510, 56, 117, 29);
		add(setTemp);
		setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int temp;
			//	temp = Integer.parseInt(setTemp);
			//	tnc.setTemp(temp);
			}
		});
		
		JLabel lblSetLights = new JLabel("Set Lights");
		lblSetLights.setBounds(365, 89, 67, 16);
		add(lblSetLights);
		
		JLabel lblSetDoors = new JLabel("Set doors");
		lblSetDoors.setBounds(365, 122, 61, 16);
		add(lblSetDoors);
		
		JLabel lblSetBrake = new JLabel("Set Brake");
		lblSetBrake.setBounds(365, 153, 61, 16);
		add(lblSetBrake);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Lights On");
		tglbtnNewToggleButton.setBounds(438, 84, 189, 29);
		add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnDoorsOn = new JToggleButton("Doors On");
		tglbtnDoorsOn.setBounds(438, 117, 189, 29);
		add(tglbtnDoorsOn);
		
		JToggleButton tglbtnSetBrake = new JToggleButton("Set Brake");
		tglbtnSetBrake.setBounds(438, 148, 189, 29);
		add(tglbtnSetBrake);
		
		JToggleButton tglbtnSetEmergencyBrake = new JToggleButton("Set Emergency Brake");
		tglbtnSetEmergencyBrake.setBounds(438, 176, 189, 29);
		add(tglbtnSetEmergencyBrake);
		
		JLabel lblSetEbrake = new JLabel("Set EBrake");
		lblSetEbrake.setBounds(365, 181, 67, 16);
		add(lblSetEbrake);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange()==ItemEvent.SELECTED){
					int ID;
			//		ID = comboBox.getSelectedIndex();
				}
					
					
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Train 001", "Train 002"}));
		comboBox.setBounds(25, 6, 236, 28);
		add(comboBox);
		
		txtNextStation = new JTextField();
		txtNextStation.setText("Next Station\n");
		txtNextStation.setBounds(27, 33, 134, 28);
		add(txtNextStation);
		txtNextStation.setColumns(10);
		
		table = new JTable();
		table.setForeground(Color.BLACK);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBackground(SystemColor.textHighlight);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Current Speed", null},
				{"Set Point Speed", null},
				{"Authority", null},
				{"Lights", null},
				{"Doors", null},
				{"Temperture", null},
				{"Emergency Brake", null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Attribute", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(112);
		table.setBounds(25, 77, 301, 128);
		add(table);
		
		textField = new JTextField();
		textField.setBounds(17, 229, 610, 96);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(31, 211, 61, 16);
		add(lblLog);
		

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private static TrainController getTrainController(int ID){
		return null;
	}
}
