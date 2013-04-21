package TrainController;

import java.util.ArrayList;

import TrainModel.*;
import TrackModel.Block;

public class TrainController 
{
	public TrainModel train;
	public int trainID;
	public double currSpeed;
	public double output;
	public double speedLimit;
	public double setPointSpeed=0;		// initial setPointSpeed
	public int authority;
	public Boolean lights = false;
	public Boolean doors = false;
	public int temp;
	public Boolean brake;
	public Boolean eBrake;
	private TNCPanel panel;
	private ArrayList<Block> routeInfo = null;
	private Block currBlock;
	private String nextStationName = "N/A";
	private Block nextStation = null;
	private double distToNextStation;
	private Boolean stationApproach=false;


	public static final double INTEGRAL_INITIAL = 0.0f;
	public static final double ERROR_INITIAL = 0.0f;
	public static final double PROPORTIONAL_GAIN = 10000f;
	public static final double INTEGRAL_GAIN = 2f;
	private double integralLast = INTEGRAL_INITIAL;
	private double errorLast = ERROR_INITIAL;
//	private double powerCommand; 


	public TrainController(TNCPanel gui){
		panel = gui;
		currSpeed = 0;
		speedLimit = 0;
		authority = 0;
		lights = false;
		doors = false;
		temp = 0;		
		brake = false;
		eBrake = false;
		currBlock = null;
	}

	public void setTrainModel(TrainModel tm)
	{
		train = tm;
		if (train!=null){
			trainID = tm.getTrainID();
			currSpeed = tm.getVelocity();
			speedLimit = tm.getSpeedLimit();
			authority = tm.getAuthority();
			lights = tm.getLights();
			doors =tm.getDoors();
			temp = tm.getTemperature();		
			brake = tm.getBrake();
			eBrake = tm.getEmergencyBrake();
			routeInfo = tm.getRouteInfo();
			
			
//			currBlock = tm.getBlock();
			panel.comboBox.addItem(tm.getTrainID());
			
			
		}

	}
	
	public void setSpeed(double s){
		if (setPointSpeed>train.getSpeedLimit()){
			setPointSpeed = train.getSpeedLimit();	 // check speed limit 
	
		}
		else {
			setPointSpeed = s;
		}
		train.setSetpointSpeed(s);
		
	}

	public double getSetPointSpeed(){
		return setPointSpeed;
	}

	public void setLights(Boolean l){
		lights = l;
		System.out.println("lights: "+ l);
		train.toggleLights();
	}

	public void setDoors(Boolean d){
		doors = d;
		train.toggleDoors();
	}

	public void setTemp(int t){
		temp = t;
		train.setTemperature(temp);
	}

	public void setBrake(Boolean b){
		brake = b;
		//		train.setBrake();
	}

	public void setEBrake(Boolean eb){
		eBrake = eb;
		//		train.setEBrake();
	}

	public void log(){
	}

	public void tick(double time){
		
		/* update train attributes */
		currSpeed = train.getVelocity();
		speedLimit = train.getSpeedLimit();
		authority = train.getAuthority();
		temp = train.getTemperature();
		currBlock = train.getBlock();
		brake = train.getBrake();
		eBrake = train.getEmergencyBrake();
		routeInfo = train.getRouteInfo();
		setPointSpeed = train.getSetpointSpeed();
//		System.out.println("RouteInfo: ");
/*		for (int i=0; i<routeInfo.size();i++){
			System.out.println("Station Name: " + routeInfo.get(i).getStationName());
		}*/
		
		// check route info 
		if (routeInfo==null || panel.comboBox.getSelectedItem().equals("Train List")){
			nextStationName = "N/A";
		}
		
		// check block
		if (currBlock!=null&& !panel.comboBox.getSelectedItem().equals("Train List")){
			getNextStation();
			
		}
		// update nextstation field
		panel.nextStation.setText(nextStationName);
		
		/* update table */
		if (!panel.comboBox.getSelectedItem().equals("Train List")){
		panel.table.setValueAt(String.format("%3.3f", currSpeed) + " m/s", 0, 1);
		panel.table.setValueAt(authority, 2, 1);
		panel.table.setValueAt(String.format("%3.3f", setPointSpeed) + " m/s", 1, 1);
		panel.table.setValueAt(String.format("%3.3f", train.getAcceleration()) + " m/s^2", 8, 1);
		panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		panel.table.setValueAt(train.getSpeedLimit() + " m/s", 10, 1);
		panel.table.setValueAt(train.getPowerLimit() + " W", 11, 1);
		
		}
		
		
		if (currBlock!=null && nextStation!=null){
			stationApproachCheck();
			panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		}
		
		/* set power command */
		if (currSpeed == 0 && setPointSpeed>0 && stationApproach==false ){
			train.setPower(nextPower(setPointSpeed, currSpeed, time)*0.1);
			panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		}
		else if (stationApproach == false){		
		train.setPower(nextPower(setPointSpeed, currSpeed, time));
		panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		}
	}

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
	
	public void getNextStation(){
		for (int i=routeInfo.indexOf(currBlock); i<routeInfo.size()-1;i++){
			if (!routeInfo.get(i+1).getStationName().equals(null)){
//				System.out.println("block station name: "+ routeInfo.get(i+1).getStationName());
				nextStation = routeInfo.get(i+1);
				nextStationName = routeInfo.get(i+1).getStationName();
				
			}
		}
	}
	
	public void checkAuth(){
		
	}
	
	public void stationApproachCheck(){
		double slowDownDist =0;
		
		
		getDistToNextStation();
		System.out.println("Distance to next station = "+distToNextStation);
		if (distToNextStation <= 375 && currSpeed >= 10){
			stationApproach = true;
			slowDownDist = currSpeed*currSpeed/2/1.2;
			System.out.println("slow down dist = "+ slowDownDist);
		}
		
		if (distToNextStation <= slowDownDist){
			train.setPower(0);
			train.setBrake(true);
			
			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Pull", 7, 1);
		}
	}
	
	public void getDistToNextStation(){
		double distBetween=0;
		for (int i=routeInfo.indexOf(currBlock); i<routeInfo.indexOf(nextStation)-1; i++){
			distBetween+=routeInfo.get(i+1).getLength();
		}
		distToNextStation = (currBlock.getLength() - train.getPosition()) + distBetween + nextStation.getLength()/2;
	}
	
	


}
