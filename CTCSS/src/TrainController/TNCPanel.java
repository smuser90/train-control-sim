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
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

public class TNCPanel extends JPanel {
	private JTextField txtSetSpeed;
	private JTextField txtSetTemp;
	private JTextField txtNextStation;
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
		panel_1.setBounds(347, 84, 274, 217);
		add(panel_1);
		panel_1.setLayout(null);
		
		
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
		JButton setSpeed = new JButton("Apply speed");
		setSpeed.setBounds(151, 23, 117, 29);
		panel_1.add(setSpeed);
		
		JButton setTemp = new JButton("Apply temp");
		setTemp.setBounds(151, 51, 117, 29);
		panel_1.add(setTemp);
		
		
		// labels
		
		JLabel lblSetSpeed = new JLabel("Set speed");
		lblSetSpeed.setBounds(6, 28, 61, 16);
		panel_1.add(lblSetSpeed);
		
		JLabel lblNewLabel = new JLabel("Set temp");
		lblNewLabel.setBounds(6, 56, 61, 16);
		panel_1.add(lblNewLabel);
		
		JLabel lblSetLights = new JLabel("Set Lights");
		lblSetLights.setBounds(6, 84, 67, 16);
		panel_1.add(lblSetLights);
		
		JLabel lblSetDoors = new JLabel("Set doors");
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
		
		
		// emergency toggle
		tglbtnSetEmergencyBrake = new JToggleButton("Set Emergency Brake");
		tglbtnSetEmergencyBrake.setBounds(79, 171, 189, 29);
		panel_1.add(tglbtnSetEmergencyBrake);
		tglbtnSetEmergencyBrake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tglbtnSetEmergencyBrake.isSelected()){	
					if(tnc!=null){
						tnc.setEBrake(true);
						table.setValueAt("Pull", 6, 1);
					}
					else{
						table.setValueAt("N/A", 6, 1);
					}
				}
				else {
					if (tnc!=null){
						tnc.setEBrake(false);
						table.setValueAt("N/A", 6, 1);
					}
					else{
						table.setValueAt("N/A", 6, 1);
					}
				}
				
			}
		});
		SetBrake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(SetBrake.isSelected()){
					if (tnc!=null){
						tnc.setBrake(true);
						table.setValueAt("Pull", 7, 1);
					}
					else{
						table.setValueAt("N/A", 7, 1);
					}
				}
				else {
					if(tnc!=null){
						tnc.setBrake(false);
						table.setValueAt("N/A", 7, 1);
					}
					else{
						table.setValueAt("N/A", 7, 1);
					}
				}
				
			}
		});
		doorToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(doorToggle.isSelected()){
					if (tnc!=null){
						tnc.setDoors(true);
						table.setValueAt("Open", 4, 1);
					}
					else {
						table.setValueAt("N/A", 4, 1);
					}
				}
				else {
					if (tnc!=null){
						tnc.setDoors(false);
						table.setValueAt("Close", 4, 1);
					}
					else{
						table.setValueAt("N/A", 4, 1);
					}
					
				}
				
			}
		});
		lightToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lightToggle.isSelected()){
					if (tnc!=null){
						tnc.setLights(true);
						table.setValueAt("On", 3, 1);
					}
					else {
						table.setValueAt("N/A", 3, 1);
					}
				}
				else {
					if(tnc!=null){
						tnc.setLights(false);
						table.setValueAt("Off", 3, 1);
					}
					else{
						table.setValueAt("N/A", 3, 1);
					}
				}
				
			}
		});
		setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		});
		setSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double setPointSpeed = Double.parseDouble(txtSetSpeed.getText());
				System.out.println("setSpeed = " + setPointSpeed);
				if (tnc!=null){
					tnc.setSpeed(setPointSpeed);
				}
				else {
					setPointSpeed =0;
				}
				table.setValueAt(setPointSpeed, 1, 1);
			}
		});
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Attribute Overview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(25, 84, 313, 220);
		add(panel);
		panel.setLayout(null);
		
		
		
		// table
		table = new JTable();
		table.setBounds(6, 22, 301, 192);
		panel.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.BLACK);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBackground(SystemColor.textHighlight);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Current Speed", "N/A"},
				{"Set Point Speed", "N/A"},
				{"Authority", "N/A"},
				{"Lights", "off"},
				{"Doors", "Close"},
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
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange()==ItemEvent.SELECTED){
					int ID;
					ID = comboBox.getSelectedIndex();
//					System.out.println("Selected ID: "+ID);
//					System.out.println("list empty: "+list.isEmpty());
					
					if (!list.isEmpty()){
						tnc = list.get(ID);
					
						
					
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
					else{
						
					}
					
					
				}					
			}
		});
		
		txtNextStation = new JTextField();
		txtNextStation.setText("Next Station\n");
		txtNextStation.setBounds(35, 50, 573, 28);
		add(txtNextStation);
		txtNextStation.setColumns(10);
		
		
		// testing
			/*		tnc = new TrainController(this);
					list.add(tnc);
					comboBox.addItem(tnc.trainID);
					tnc1 = new TrainController(this);
					tnc1.trainID=1;
					tnc1.currSpeed=50;
					list.add(tnc1);
					comboBox.addItem(tnc1.trainID);*/
	}
	

/*	private static void addPopup(Component component, final JPopupMenu popup) {
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
	}*/
}
