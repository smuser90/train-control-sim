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
	
	
	public TrainController(TNCPanel gui){
		panel = gui;
//		trainID = 0;
		currSpeed = 100;
		speedLimit = 120;
		authority = 0;
		lights = false;
		doors = false;
		temp = 0;		
		brake = false;
		eBrake = false;
		panel.comboBox.addItem(trainID);
	}
	
	// testing without train model
/*	public TrainController(TNCPanel gui, int tID){
		panel = gui;
		//train = tnm;
		trainID = tID;
		currSpeed = 100;
		speedLimit = 120;
		authority = 0;
		lights = false;
		doors = false;
		temp = 0;		
		brake = false;
		eBrake = false;
		panel.comboBox.addItem(trainID);
	}*/
	
	public void setTrainModel(TrainModel tm)
	{
		train = tm;
		if (train!=null){
		trainID = tm.getTrainID();
		currSpeed = tm.getVelocity();
//		speedLimit = tm.getSpeedLimit;
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
//		if (setPointSpeed>train.speedLimit){
//			setPointSpeed = train.speedLimit;	 // check speed limit 
//		}
//		regulateSpeed();
	}
	
	public double getSetPointSpeed(){
		return setPointSpeed;
	}
	
	public void setLights(Boolean l){
		lights = l;
//		train.setLight(lights);
	}
	
	public void setDoors(Boolean d){
		doors = d;
//		train.setDoor();
	}
	
	public void setTemp(int t){
		temp = t;
//		train.setTemp(temp);
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
	
	public void tick(){
		currSpeed = train.getVelocity();
//		speedLimit = train.getSpeedLimit();
		authority = train.getAuthority();
//		lights = train.getLight();
//		doors = train.getDoor();
		temp = train.getTemperature();
//		brake = train.getBrake();
//		eBrake = train.getEmergencyBrake();
//		routeInfo;
		
		panel.table.setValueAt(currSpeed, 0, 1);
		panel.table.setValueAt(authority, 2, 1);
		
//		if (!train.getAuthority()){
//			train.setEmergencyBrake();				// check authority
//		} 
/*		else if(brake==true){
			train.setBrake(true);
		}
				
		else {
			regulateSpeed();
		}	*/	
	}
	
/*	public void regulateSpeed(){	
		double Pcmd;
		
		error = setPointSpeed - currSpeed;
		Pcmd = Kp*error + Ki*currSpeed;

		if (Pcmd>train.maxPower){
			Pcmd = train.maxPower;
		}
		train.setPower(Pcmd);
	}*/
	
	public void stationAnounce(){
		
	}
	
	
}
