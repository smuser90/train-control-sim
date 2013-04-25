 
/* 
 * TNCPanel.java
 * Description: TNCPanel is the GUI of train controller.
 * Author: KE LUO
 * Date Created: 07/04/2013
 * Date Last Updated: 24/04/2013
 * 
 */

package TrainController;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;

public class TNCPanel extends JPanel {
	private JTextField txtSetSpeed;
	private JTextField txtSetTemp;
	public JTable table;
	private String setSpeedInput;
	private String temp;
	public TrainController tnc;
	private TrainControllerModule _tcm;
	public JComboBox comboBox;
	public ArrayList<TrainController> list;
	public JToggleButton tglbtnSetEmergencyBrake, SetBrake, doorToggle, lightToggle;
	private JPanel panel;
	private JPanel panel_1;
	public JTextArea nextStation;


	/**
	 * Create the panel.
	 */
	public TNCPanel(TrainControllerModule tcm, ArrayList<TrainController> l) {
		_tcm = tcm;
		list = l;
		tnc=null;		

		setBorder(UIManager.getBorder("Button.border"));
		setLayout(null);
		setBounds(100, 100, 650, 350);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Control Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(347, 95, 274, 220);
		add(panel_1);
		panel_1.setLayout(null);


		// next station field
		nextStation = new JTextArea();
		nextStation.setEditable(false);
		nextStation.setBounds(125, 56, 486, 22);
		add(nextStation);
		nextStation.setText("Wait for route info");

		JLabel lblNextStation = new JLabel("Next Station");
		lblNextStation.setBounds(35, 56, 78, 22);
		add(lblNextStation);


		// text field
		txtSetSpeed = new JTextField();
		txtSetSpeed.setBounds(79, 22, 74, 28);
		panel_1.add(txtSetSpeed);
		txtSetSpeed.setText("");
		txtSetSpeed.setColumns(10);

		txtSetTemp = new JTextField();
		txtSetTemp.setBounds(79, 50, 74, 28);
		panel_1.add(txtSetTemp);
		txtSetTemp.setText("");
		txtSetTemp.setColumns(10);

		// buttons
		JButton setSpeed = new JButton("Apply Speed");
		setSpeed.setBounds(151, 23, 117, 29);
		panel_1.add(setSpeed);

		JButton setTemp = new JButton("Apply Temp");
		setTemp.setBounds(151, 51, 117, 29);
		panel_1.add(setTemp);


		// labels

		JLabel lblSetSpeed = new JLabel("Set Speed");
		lblSetSpeed.setBounds(6, 28, 61, 16);
		panel_1.add(lblSetSpeed);

		JLabel lblNewLabel = new JLabel("Set Temp");
		lblNewLabel.setBounds(6, 56, 61, 16);
		panel_1.add(lblNewLabel);

		JLabel lblSetLights = new JLabel("Set Lights");
		lblSetLights.setBounds(6, 84, 67, 16);
		panel_1.add(lblSetLights);

		JLabel lblSetDoors = new JLabel("Set Doors");
		lblSetDoors.setBounds(6, 117, 61, 16);
		panel_1.add(lblSetDoors);

		JLabel lblSetBrake = new JLabel("Set Brake");
		lblSetBrake.setBounds(6, 148, 61, 16);
		panel_1.add(lblSetBrake);

		JLabel lblSetEbrake = new JLabel("Set EBrake");
		lblSetEbrake.setBounds(6, 176, 67, 16);
		panel_1.add(lblSetEbrake);


		// light toggle

		lightToggle = new JToggleButton("Lights On");
		lightToggle.setBounds(79, 79, 189, 29);
		panel_1.add(lightToggle);

		// door toggle

		doorToggle = new JToggleButton("Doors On");
		doorToggle.setBounds(79, 112, 189, 29);
		panel_1.add(doorToggle);


		// brake toggle
		SetBrake = new JToggleButton("Set Brake");
		SetBrake.setBounds(79, 143, 189, 29);
		panel_1.add(SetBrake);


		/* emergency toggle */
		tglbtnSetEmergencyBrake = new JToggleButton("Set Emergency Brake");
		tglbtnSetEmergencyBrake.setBounds(79, 171, 189, 29);
		panel_1.add(tglbtnSetEmergencyBrake);

		/* emergency toggle listener */
		tglbtnSetEmergencyBrake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List")){
					if(tglbtnSetEmergencyBrake.isSelected() ){	
						if(tnc!=null){
							tnc.setEBrakeType(2);
							tnc.setEBrake(true);
							table.setValueAt("Manually Pull", 6, 1);
						}
						else{
							table.setValueAt("N/A", 6, 1);
						}
					}
					else {
						if (tnc!=null){
							tnc.setSpeed(tnc.getSpeedLimit());
							tnc.setEBrakeType(0);
							tnc.setEBrake(false);
							table.setValueAt("Release", 6, 1);
						}
						else{
							table.setValueAt("N/A", 6, 1);
						}
					}

				}
			}
		});

		/* service brake toggle */
		SetBrake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List")){
					if(SetBrake.isSelected()){
						if (tnc!=null){
							tnc.setBrakeType(4);
							tnc.setBrake(true);
							table.setValueAt("Manually Pull", 7, 1);
						}
						else{
							table.setValueAt("N/A", 7, 1);
						}
					}
					else {
						if(tnc!=null){
							tnc.setBrakeType(0);
							tnc.setBrake(false);
							table.setValueAt("Release", 7, 1);
						}
						else{
							table.setValueAt("N/A", 7, 1);
						}
					}

				}
			}
		});

		/* door toggle listener */
		doorToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List") && tnc.getDoorType()!=1){
					if(doorToggle.isSelected()){
						if (tnc!=null){
							tnc.setDoorType(2);
							tnc.setDoors(true);
							table.setValueAt("Open", 4, 1);
						}
						else {
							table.setValueAt("N/A", 4, 1);
						}
					}
					else {
						if (tnc!=null){
							tnc.setDoorType(2);
							tnc.setDoors(false);
							table.setValueAt("Close", 4, 1);
						}
						else{
							table.setValueAt("N/A", 4, 1);
						}

					}

				}
			}
		});

		/* light toggle listener */
		lightToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List") && tnc.getLightType()!=1){
					if(lightToggle.isSelected()){
						if (tnc!=null){
							tnc.setLightType(2);
							tnc.setLights(true);
							table.setValueAt("On", 3, 1);
						}
						else {
							table.setValueAt("N/A", 3, 1);
						}
					}
					else {
						if(tnc!=null){
							tnc.setLightType(2);
							tnc.setLights(false);
							table.setValueAt("Off", 3, 1);
						}
						else{
							table.setValueAt("N/A", 3, 1);
						}
					}

				}
			}
		});


		/* set temp listener */
		setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List")){
					int temperature;
					temperature = Integer.parseInt(txtSetTemp.getText());
					if (tnc!=null){
						tnc.setTemp(temperature);
					}
					else{
						temperature = 0;
					}
					table.setValueAt(temperature, 5, 1);
				}
			}
		});

		/* set speed listener */
		setSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Train List")){
					double setPointSpeed = Double.parseDouble(txtSetSpeed.getText());
	//				System.out.println("setSpeed = " + setPointSpeed);
					if (tnc!=null){
						tnc.setSpeed(setPointSpeed);
					}
					else {
						setPointSpeed =0;
					}
	//				table.setValueAt(setPointSpeed, 1, 1);
				}
			}
		});

		/* attributes overview border */
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Attribute Overview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(25, 95, 313, 220);
		add(panel);
		panel.setLayout(null);



		/* table */
		table = new JTable();
		table.setBounds(6, 22, 301, 192);
		panel.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.BLACK);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.LIGHT_GRAY);

		table.setModel(new DefaultTableModel(
				new Object[][] {
						{"Current Speed", "N/A"},
						{"Set Point Speed", "N/A"},
						{"Authority", "N/A"},
						{"Lights", "N/A"},
						{"Doors", "N/A"},
						{"Temperture", "N/A"},
						{"Emergency Brake", "N/A"},
						{"Brake", "N/A"},
						{"Acceleration", "N/A"},
						{"Output Power", "N/A"},
						{"SpeedLimit", "N/A"},
						{"Max Power", "N/A"},


				},
				new String[] {
						"Attribute", "Value"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 687140540938402494L;
			boolean[] columnEditables = new boolean[] {
					false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(112);


		// comboBox
		comboBox = new JComboBox();	
		comboBox.setBounds(25, 16, 596, 28);
		add(comboBox);
		comboBox.addItem("Train List");



		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange()==ItemEvent.SELECTED){
					if(comboBox.getSelectedItem().equals("Train List")){
						//rest gui 
						for (int i=0; i<12; i++){
							table.setValueAt("N/A", i, 1);
						}
					}



					else if (!list.isEmpty()){
						int ID;
						ID = comboBox.getSelectedIndex();

						tnc = list.get(ID-1);



						table.setValueAt(tnc.currSpeed, 0, 1);
						table.setValueAt(tnc.setPointSpeed, 1, 1);
						table.setValueAt(tnc.authority, 2, 1);
						if (tnc.lights == false){
							lightToggle.setSelected(false);
							table.setValueAt("Off", 3, 1);
						}
						else {
							lightToggle.setSelected(true);
							table.setValueAt("On", 3, 1);
						}
						if (tnc.doors == false){
							doorToggle.setSelected(false);
							table.setValueAt("Close", 4, 1);
						}
						else {
							doorToggle.setSelected(true);
							table.setValueAt("Open", 4, 1);
						}
						table.setValueAt(tnc.temp, 5, 1);

						if (tnc.brake == false){			
							SetBrake.setSelected(false);
							table.setValueAt("N/A", 6, 1);
						}
						else {
							SetBrake.setSelected(true);
							table.setValueAt("Pull", 6, 1);
						}

						if (tnc.eBrake == false){
							tglbtnSetEmergencyBrake.setSelected(false);
							table.setValueAt("N/A", 7, 1);
						}
						else {
							tglbtnSetEmergencyBrake.setSelected(true);
							table.setValueAt("Pull", 7, 1);
						}
					}


				}					
			}
		});
		
		
	}
}
