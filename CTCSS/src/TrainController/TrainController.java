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
	public double setPointSpeed;		
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
	private double authLen=0;
	private double eBrakeDist;
	private Boolean tick=true;
	private int brakeType = 0;	// 0-free 1-near station 2-auth check 3-speed check 4-manual
	private int eBrakeType = 0;;	// 0-free 1-auth 2-manual
	private int tickCounter=0;


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
		eBrakeDist = getEBrakeDist();
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
			setPointSpeed = train.getSpeedLimit()/3.6;	 // check speed limit 

		}
		else {
			setPointSpeed = s/3.6;
		}
		train.setSetpointSpeed(setPointSpeed);

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
		//	train.setBrake();
	}

	public void setEBrake(Boolean eb){
		eBrake = eb;
		//		train.setEBrake();
	}

	public void log(){
	}

	public void tick(double time){
		
		if (tick==true){
			/* update train attributes */
			currSpeed = train.getVelocity();
			speedLimit = train.getSpeedLimit();
			authority = train.getAuthority();
			temp = train.getTemperature();
			currBlock = train.getBlock();
			brake = train.getBrake();
			eBrake = train.getEmergencyBrake();
			routeInfo = train.getRouteInfo();
			if (train.getSetpointSpeed()<=speedLimit){
				setPointSpeed = train.getSetpointSpeed()/3.6;

			}
			else {
				setPointSpeed = train.getSpeedLimit()/3.6;
				train.setSetpointSpeed(speedLimit);
			}

			// check route info 
			if (routeInfo==null || panel.comboBox.getSelectedItem().equals("Train List")){
				nextStationName = "N/A";
			}

			// check block
			if (currBlock!=null){
				getNextStation();

			}
			// update nextstation field
			

			//			System.out.println("currBlock: " + currBlock.getStationName());
			//			System.out.println("nextStation: " + nextStation.getStationName());
			if (checkAuth()){
				if (currBlock!=null && nextStation!=null){
					stationApproachCheck();
					panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
				}

				/* set power command */
				//			System.out.println("currSpeed: "+currSpeed);
				//			System.out.println("setPointSpeed: "+setPointSpeed);
				//			System.out.println("brake: "+brake);
				if (speedIsSafe()){
					if (currSpeed < 3 && setPointSpeed > 0 && !brake ){
						train.setPower(nextPower(setPointSpeed, currSpeed, time)*0.1);
			//			panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
					}
					else if (!brake){		
						//				System.out.println("Set power ");
						train.setPower(nextPower(setPointSpeed, currSpeed, time));
			//			panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
					}
				}
			}
		}
		else{
			//			System.out.println("tick1: " + tick);
			train.setPower(0);
			setBrake(false);
		}
		if(tickCounter==0){
			updateGui();
			
		}
		tickCounter++;
		tickCounter = tickCounter % 5;
//		System.out.println("tick counter " + tickCounter);
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
				nextStation = routeInfo.get(i+1);
				nextStationName = routeInfo.get(i+1).getStationName();

			}
		}
	}



	public void stationApproachCheck(){
		double slowDownDist =0;		
		getDistToNextStation();
		//			System.out.println("Distance to next station = "+distToNextStation);
		slowDownDist = currSpeed*currSpeed/2/1.2;
		//			System.out.println("slow down dist = "+ slowDownDist);


		if (distToNextStation <= slowDownDist){
			train.setPower(0);
			train.setBrake(true);
			//			stationApproach=true;
			brakeType=1;


			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Applied", 7, 1);

			if (currSpeed==0){
				brakeType=0;
				//				stationApproach=false;
				//		System.out.println("Shit happen");
				brake=false;
				train.setBrake(brake);
				panel.SetBrake.setSelected(false);
				panel.table.setValueAt("Unapplied", 7, 1);
				//			tick=false;
			}
		}


	}

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

	public double getAuthLen(){
		authLen += currBlock.getLength() - train.getPosition();
		for (int i=0; i<authority; i++){
			authLen+=routeInfo.get(routeInfo.indexOf(currBlock)+1+i).getLength();
		}
		return authLen;
	}

	public double getEBrakeDist(){
		double dist;
		dist = currSpeed*currSpeed/2/2.73;
		return dist;
	}

	public Boolean checkAuth(){
		if (authority == 0 && !currBlock.getStationName().equals(nextStation.getStationName())){
			eBrakeType=1;
			train.setPower(0);
			train.toggleEmergencyBrake();
			panel.tglbtnSetEmergencyBrake.setSelected(true);
			return false;
		}
		else if (authLen < eBrakeDist) {
			System.out.println("Authority brake");
			brakeType=2;
			train.setPower(0);
			train.setBrake(true);
			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Applied", 7, 1);
			return false;
		}

		else if (brakeType==2){
			brakeType=0;
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("unapplied", 7, 1);
			panel.table.setValueAt("unapplied", 6, 1);
			panel.tglbtnSetEmergencyBrake.setSelected(false);
		}
		return true;
	}

	public void setTick(Boolean t){
		tick = t;
		if (tick){
			brakeType=0;
			train.setPower(0);
			brake=false;
			train.setBrake(brake);
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Unapplied", 7, 1);
		}
		else{
			brakeType=0;
			train.setPower(0);
			stationApproach=false;
			train.setBrake(brake);
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Unapplied", 7, 1);
		}

	}

	public Boolean speedIsSafe(){
		if (currSpeed>(speedLimit/3.6)){
			brakeType=3;
			train.setPower(0);
			brake=true;
			train.setBrake(brake);
			panel.SetBrake.setSelected(true);
			panel.table.setValueAt("Applied", 7, 1);
			return false;
		}
		else if (brakeType==3){
			brake=false;
			train.setBrake(brake);
			panel.SetBrake.setSelected(false);
			panel.table.setValueAt("Unapplied", 7, 1);
			return true;
		}
		return true;
	}
	
	public void updateGui(){
//		System.out.println("update gui");
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
}
