package TrainController;

import TrainModel.TrainModel;

public class TrainController 
{
	public TrainModel train;
	public int trainID;
//	private double currSpeed;
	private double output;
//	private double speedLimit;
	public double setPointSpeed=0;		// initial setPointSpeed
	private Boolean authority;
	private Boolean lights;
	private Boolean doors;
	private int temp;
	private Boolean brake;
	private Boolean eBrake;
	private final int Kp = 0;
	private final int Ki = 0;
	private final int t = 0;
	private double pre_error=0, error=0, Vk, pre_Vk=0 ;
//	private routeInfo ;
	
	public TrainController(TrainModel t){
		train = t;
//		trainID = t.trainID;
	}
	
	public void setSpeed(int s){
		setPointSpeed = s;
//		if (setPointSpeed>train.speedLimit){
//			setPointSpeed = train.speedLimit;	 // check speed limit 
//		}
//		regulateSpeed();
	}
	
	public Boolean setLights(){
//		train.setLight();
		return false;
	}
	
	public Boolean setDoors(){
//		train.setDoor();
		return false;
	}
	
	public int setTemp(int temp){
//		train.setTemp(temp);
		return 0; 
	}
	
	public Boolean setBrake(){
//		train.setBrake();
		return false;
	}
	
	public Boolean setEBrake(){
//		train.setEBrake();
		return false;
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
