package TrainController;

import TrainModel.*;

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
	private final int Kp = 1;
	private final int Ki = 1;
	private final int t = 0;
	private double pre_error=0, error=0, Vk, pre_Vk=0 ;
	private TNCPanel panel;
	//	private routeInfo ;

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
//		System.out.println("Train controller created!");
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
			panel.comboBox.addItem(tm.getTrainID());
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
		currSpeed = train.getVelocity();
		speedLimit = train.getSpeedLimit();
		authority = train.getAuthority();
		//      lights = train.getLight();
		//		doors = train.getDoor();
		temp = train.getTemperature();
		
		//		brake = train.getBrake();
		//		eBrake = train.getEmergencyBrake();
		//		routeInfo;

		panel.table.setValueAt(String.format("%3.3f", currSpeed) + " m/s", 0, 1);
		panel.table.setValueAt(authority, 2, 1);
		if (train.getAcceleration()>0.5){
			System.out.println("Over Acc " + train.getAcceleration() + " Power "+ train.getPower());
		}
		panel.table.setValueAt(String.format("%3.3f", train.getAcceleration()) + " m/s^2", 8, 1);
		panel.table.setValueAt(String.format("%3.3f", train.getPower()) + " W", 9, 1);
		panel.table.setValueAt(train.getSpeedLimit() + " m/s", 10, 1);
		panel.table.setValueAt(train.getPowerLimit() + " W", 11, 1);
		
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

	public void stationAnounce(){

	}


}
