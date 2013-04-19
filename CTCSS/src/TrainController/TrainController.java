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
	private String nextStation = "Wait for route info";

	public static final double INTEGRAL_INITIAL = 0.0f;
	public static final double ERROR_INITIAL = 0.0f;
	public static final double PROPORTIONAL_GAIN = 6000f;
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
			//		brake = tm.getBrake();
			eBrake = tm.getEmergencyBrake();
//			routeInfo = tm.getRouteInfo();
			currBlock = tm.getBlock();
			panel.comboBox.addItem(tm.getTrainID());
/*			if (!routeInfo.isEmpty()){
				nextStation(routeInfo);
			}*/
		}

	}
	
	public void setSpeed(double s){
		setPointSpeed = s;
		train.setSetpointSpeed(s);
		if (setPointSpeed>train.getSpeedLimit()){
			setPointSpeed = train.getSpeedLimit();	 // check speed limit 
	
		}
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
		//		brake = train.getBrake();
		//		eBrake = train.getEmergencyBrake();
		//		routeInfo;

		/* update table */
		if (!panel.comboBox.getSelectedItem().equals("Train List")){
		panel.table.setValueAt(String.format("%3.3f", currSpeed) + " m/s", 0, 1);
		panel.table.setValueAt(authority, 2, 1);
		panel.table.setValueAt(String.format("%3.3f", train.getAcceleration()) + " m/s^2", 8, 1);
		panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		panel.table.setValueAt(train.getSpeedLimit() + " m/s", 10, 1);
		panel.table.setValueAt(train.getPowerLimit() + " W", 11, 1);
		}
		
		
		if (currSpeed == 0 && setPointSpeed>0 ){
			train.setPower(nextPower(setPointSpeed, currSpeed, time)*0.01);
		}
		else{		
		train.setPower(nextPower(setPointSpeed, currSpeed, time));
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

	public void nextStation(ArrayList<Block> route){
		if(currBlock.getStationName().equals(nextStation)){
			for (int i=route.indexOf(currBlock); i<route.size();i++){
				if (!route.get(i+1).getStationName().equals(null)){
					nextStation = route.get(i+1).getStationName();
					panel.nextStation.setText(nextStation);
				}
			}
		}
	}


}
