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
	private final int Kp = 0;
	private final int Ki = 0;
	private final int t = 0;
	private double pre_error=0, error=0, Vk, pre_Vk=0 ;
	private TNCPanel panel;
//	private routeInfo ;
	
	public TrainController(TNCPanel gui, TrainModel tnm){
		panel = gui;
		train = tnm;
		trainID = 000;
		currSpeed = 100;
		speedLimit = 120;
		authority = 1;
		lights = false;
		doors = false;
		temp = 100;		
		brake = false;
		eBrake = false;
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
	
	public Boolean setLights(Boolean l){
		lights = l;
//		train.setLight(lights);
		return lights;
	}
	
	public Boolean setDoors(Boolean d){
		doors = d;
//		train.setDoor();
		return false;
	}
	
	public int setTemp(int t){
		temp = t;
		System.out.println("temp = " + temp);
//		train.setTemp(temp);
		return 0; 
	}
	
	public Boolean setBrake(Boolean b){
		brake = b;
//		train.setBrake();
		return brake;
	}
	
	public Boolean setEBrake(Boolean eb){
		eBrake = eb;
//		train.setEBrake();
		return eBrake;
	}
	
	public String log(){
		return null;
	}
	
	public void tick(){
//		currSpeed = train.getCurrSpeed();
//		speedLimit = train.SpeedLimit;
//		authority = train.getAuthority();
//		lights = train.getLight();
//		doors = train.getDoor();
//		temp = train.getTemp();
//		brake = train.getBrake();
//		eBrake = train.getEmergencyBrake();
//		routeInfo;
		
		panel.table.setValueAt(currSpeed, 0, 1);
		panel.table.setValueAt(authority, 2, 1);
		
//		if (!train.getAuthority()){
//			train.setEmergencyBrake();				// check authority
//		} 
/*		else if(setBrake()){
			train.setBrake();
		}
				
		else {
			regulateSpeed();
		}	*/	
	}
	
/*	public void regulateSpeed(){	
		double Pcmd;
		
		error = setPointSpeed - train.currSpeed;
		Pcmd = Kp*error + Ki*train.currSpeed;

		if (Pcmd>train.maxPower){
			Pcmd = train.maxPower;
		}
		train.setPower(Pcmd);
	}*/
	
	public void stationAnounce(){
		
	}
	
	
}
