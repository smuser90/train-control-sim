package TrainController;

import java.util.ArrayList;

import TrainModel.*;
import TrackModel.Block;

public class TrainController 
{
	public TrainModel train;		// train model 
	public int trainID;				// train ID
	public double currSpeed;		// current speed	
	public double speedLimit;		// speed limit
	public double setPointSpeed;	// set point speed	
	public int authority;			// authority
	public Boolean lights;	// lights ON: true OFF: false
	public Boolean doors;	// doors  Open: true Close: false
	public int temp;				// temperature
	public Boolean brake;			// brake 
	public Boolean eBrake;			// emergency brake 
	private TNCPanel panel;			// Gui 
	private ArrayList<Block> routeInfo = null;	// route information
	private Block currBlock;		// current clock the train occupied
	private String nextStationName = "N/A";		// station name
	private Block nextStation;			// station block 
	private double distToNextStation;			// distance to next station		
	private double authLen;		// authorized length
	private double eBrakeDist;		// distance to stop with emergency brake
	private Boolean tick;		// tick 
	private int brakeType;		// 0-free 1-near station 2-auth check 3-speed check 4-manual
	private int eBrakeType;;	// 0-free 1-auth check 2-manual 
	private int tickCounter=0;

	public static final double INTEGRAL_INITIAL = 0.0f;		// integral initial
	public static final double ERROR_INITIAL = 0.0f;		// error initial
	public static final double PROPORTIONAL_GAIN = 10000f;	// set proportional gain 10000
	public static final double INTEGRAL_GAIN = 2f;			// set integral gain 2
	private double integralLast = INTEGRAL_INITIAL;			// track last integral
	private double errorLast = ERROR_INITIAL;				// track last error

	/******** constructor ***********/
	public TrainController(TNCPanel gui){
		panel = gui;						// reference gui
		// initialize train status
		currSpeed = 0;						
		speedLimit = 0;						
		authority = 0;						
		lights = false;						
		doors = false;						
		temp = 0;							
		brake = false;					
		eBrake = false;
		eBrakeDist = 0;
		brakeType = 0;
		eBrakeType = 0;
		authLen = 0;
		currBlock = null;
		nextStationName = "N/A";
		nextStation = null;
		tick = true;
	}

	/******** set train model ***********/
	public void setTrainModel(TrainModel tm)
	{
		train = tm;
		if (train!=null){
			// update train status to specific train model
			trainID = tm.getTrainID();
			currSpeed = tm.getVelocity();
			speedLimit = tm.getSpeedLimit();
			authority = tm.getAuthority();
			lights = tm.getLights();
			doors =tm.getDoors();
			temp = tm.getTemperature();		
			brake = tm.getBrake();
			eBrake = tm.getEmergencyBrake();
//			routeInfo = tm.getRouteInfo();
			
			// add new item into combo box
			panel.comboBox.addItem(tm.getTrainID());
		}

	}

	/******** set speed  ***********/
	public void setSpeed(double s){
		// if set point speed is over speed limit, set to set point speed to speed limit
		if (train.getSetpointSpeed()>train.getSpeedLimit()){	
			setPointSpeed = train.getSpeedLimit()/3.6;	 		
		}
		else {
			setPointSpeed = s/3.6;
		}
		train.setSetpointSpeed(setPointSpeed*3.6);
	}

	/******** set lights ***********/
	public void setLights(Boolean l){
		lights = l;
		train.toggleLights();
	}
	
	/******** set doors ***********/
	public void setDoors(Boolean d){
		doors = d;
		train.toggleDoors();
	}

	/******** set temperature ***********/
	public void setTemp(int t){
		temp = t;
		train.setTemperature(temp);
	}

	/******** set brake ***********/
	public void setBrake(Boolean b){
		brake = b;
		train.setBrake(brake);
	}

	/******** set emergency brake ***********/
	public void setEBrake(Boolean eb){
		eBrake = eb;
		train.setPower(0);
		train.toggleEmergencyBrake();
	}

	
	/******** tick action ***********/
	public void tick(double time){
		// tick enable
		if (tick==true){
			// update train attributes 
			currSpeed = train.getVelocity();
			speedLimit = train.getSpeedLimit();
			authority = train.getAuthority();
			
			temp = train.getTemperature();
			currBlock = train.getBlock();
			brake = train.getBrake();
			eBrake = train.getEmergencyBrake();
			routeInfo = train.getRouteInfo();
			
			// regulate set point speed
			regulateSpeed();
			

			// route info reset if no route
			if (routeInfo==null || panel.comboBox.getSelectedItem().equals("Train List")){
				nextStationName = "N/A";
			}

			// update destination
			if (currBlock!=null){
				getNextStation();
			}

			// check authority
			if (checkAuth()){
				// check station approach, and slow down when approaching a station
				if (currBlock!=null && nextStation!=null){
					stationApproachCheck();
				}
				// speed check make sure speed is legal
				if (speedIsSafe()){
					// initial power
					if (currSpeed == 0 && setPointSpeed > 0 && !brake && !eBrake){
						train.setPower(nextPower(setPointSpeed, currSpeed, time)*0.1);
					}
					// update power 
					else if (!brake && !eBrake){		
						train.setPower(nextPower(setPointSpeed, currSpeed, time));
					}
				}
			}
		}
		// tick disable 
		else{
			train.setPower(0);		// tick disable, stop output power
//			setBrake(false);
		}
		
		// update gui
		if(tickCounter==0){
			updateGui();

		}
//		tickCounter++;
//		tickCounter = tickCounter % 5;
		//		System.out.println("tick counter " + tickCounter);
	}

	/******** output power ***********/
	public double nextPower(double setpoint, double currentSpeed, double millis) {

		double integral; /* integral used for PID control */
		double error;    /* difference between setpoint and currentSpeed */
		double sampleTime; /* sample time */
		double powerCommand; /* power to provide to engine */
		double POWER_MAX = train.getPowerLimit();

		error = setpoint - currentSpeed;

		integral = integralLast + (millis/2f)*(error + errorLast);
		powerCommand = (INTEGRAL_GAIN * integral) + 
				(PROPORTIONAL_GAIN * error);

		if (powerCommand > POWER_MAX) {
			integral = integralLast;
			powerCommand = POWER_MAX;
		}
		else if (powerCommand <= 0.0f) {
			integral = integralLast;
			powerCommand = 0.0f;
		}
		integralLast = integral;
		errorLast = error; 

		return powerCommand;

	}

	/******** get next station ***********/
	public void getNextStation(){
		nextStation = routeInfo.get(routeInfo.size()-1);
		nextStationName = nextStation.getStationName();
	}

	/******** check if approach a station ***********/
	public void stationApproachCheck(){
		double slowDownDist =0;		
		getDistToNextStation();
		slowDownDist = currSpeed*currSpeed/2/1.2;

		if (distToNextStation <= slowDownDist){
			train.setPower(0);
			train.setBrake(true);
			brakeType=1;


			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Near Station Pull", 7, 1);

			if (currSpeed==0){
				brakeType=0;
				brake=false;
				train.setBrake(brake);
				panel.SetBrake.setSelected(false);
				panel.table.setValueAt("Release", 7, 1);
			}
		}


	}

	/******** get the distance to next station ***********/
	public void getDistToNextStation(){
		double distBetween=0;

		if (!currBlock.getStationName().equals(nextStation.getStationName())){
			for (int i=routeInfo.indexOf(currBlock); i<routeInfo.indexOf(nextStation)-1; i++){
				distBetween+=routeInfo.get(i+1).getLength();
			}
			distToNextStation = (currBlock.getLength() - train.getPosition()) + distBetween + nextStation.getLength()/2;
		}
		else if(currBlock.getStationName().equals(nextStation.getStationName()) && train.getPosition()< nextStation.getLength()/2){
			distToNextStation = currBlock.getLength()/2 - train.getPosition();
		}
		else{
			distToNextStation =0;
		}
	}

	/******** get authorized length ***********/
	public void getAuthLen(){
		authLen += currBlock.getLength() - train.getPosition();
		if (authority <= routeInfo.indexOf(nextStation)-routeInfo.indexOf(currBlock)){
			for (int i=0; i<authority; i++){
				authLen+=routeInfo.get(routeInfo.indexOf(currBlock)+1+i).getLength();
			}
		}
		else{
			for (int i=0; i<routeInfo.indexOf(nextStation)-routeInfo.indexOf(currBlock); i++){
				authLen+=routeInfo.get(routeInfo.indexOf(currBlock)+1+i).getLength();
			}
		}
	}

	/******** get distance required to make a emergency brake ***********/
	public double getEBrakeDist(){
		double dist;
		dist = currSpeed*currSpeed/2/2.73;
		return dist;
	}

	/******** check authority ***********/
	public Boolean checkAuth(){
		// update authorized length
		if (currBlock!=null){
			getAuthLen();
		}
		
		if (authority == 0 && !currBlock.getStationName().equals(nextStation.getStationName()) && eBrakeType!=1 && eBrakeType!=2){
			eBrakeType=1;
			train.setPower(0);
			train.toggleEmergencyBrake();
			panel.tglbtnSetEmergencyBrake.setSelected(true);
			return false;
		}
		else if (authority == 0 && !currBlock.getStationName().equals(nextStation.getStationName()) && eBrakeType==1){
			return false;
		}
		else if (authLen < eBrakeDist && eBrakeType!=4) {
			brakeType=2;
			train.setPower(0);
			train.setBrake(true);
			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Applied", 7, 1);
			return false;
		}

		else if (brakeType==2 && eBrakeType!=1){
			brakeType=0;
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Release", 7, 1);
			panel.table.setValueAt("Release", 6, 1);
			panel.tglbtnSetEmergencyBrake.setSelected(false);
		}
		else if (brakeType==2 && eBrakeType!=1){
			setSpeed(train.getSpeedLimit());
//			train.setSetpointSpeed(train.getSpeedLimit());
			panel.table.setValueAt("Release", 6, 1);
			panel.tglbtnSetEmergencyBrake.setSelected(false);
		}
		return true;
	}

	/******** set tick method ***********/
	public void setTick(Boolean t){
		tick = t;
		if (!tick){			
			System.out.println("Stop ticking");
			panel.table.setValueAt(String.format("%3.3f", train.getVelocity()) + " KPH", 0, 1);
			brakeType=0;
//			train.setPower(0);
			train.setBrake(brake);
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Release", 7, 1);
		}
	}

	/******** check if speed is safe to run ***********/
	public Boolean speedIsSafe(){
		if (currSpeed>(speedLimit/3.6)){
			brakeType=3;
			train.setPower(0);
			brake=true;
			train.setBrake(brake);
			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Overspeed Brake", 7, 1);
			return false;
		}
		else if (brakeType==3){
			brakeType=0;
			brake=false;
			train.setBrake(brake);
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Release", 7, 1);
			return true;
		}
		return true;
	}

	/******** update gui ***********/
	public void updateGui(){
		/* update table */
		if (!panel.comboBox.getSelectedItem().equals("Train List")){
			panel.table.setValueAt(String.format("%3.3f", currSpeed*3.6) + " KPH", 0, 1);
			panel.table.setValueAt(authority, 2, 1);
			panel.table.setValueAt(String.format("%3.3f", setPointSpeed*3.6) + " KPH", 1, 1);
			panel.table.setValueAt(String.format("%3.3f", train.getAcceleration()) + " m/s^2", 8, 1);
			panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
			panel.table.setValueAt(train.getSpeedLimit() + " KPH", 10, 1);
			panel.table.setValueAt(train.getPowerLimit() + " W", 11, 1);
			panel.nextStation.setText(nextStationName);

		}
	}
	
	/******** get brake type ***********/
	public int getBrakeType(){
		return brakeType;
	}
	
	/******** get emergency brake type ***********/
	public int getEBrakeType(){
		return eBrakeType;
	}
	
	/******** set brake type ***********/
	public void setBrakeType(int t){
		brakeType = t;
	}
	
	/******** set emergency brake type ***********/
	public void setEBrakeType(int t){
		eBrakeType = t;
	}
	
	/******** get speed limit ***********/
	public double getSpeedLimit(){
		return train.getSpeedLimit();
	}
	
	/******** regulate set point speed ***********/
	public void regulateSpeed(){
		if (train.getSetpointSpeed()<=speedLimit){
			setPointSpeed = train.getSetpointSpeed()/3.6;

		}
		else {
			setPointSpeed = train.getSpeedLimit()/3.6;
			train.setSetpointSpeed(speedLimit);
		}
	}
}
