package TrainModel;

import java.util.ArrayList;

import javax.swing.text.Document;

import TrackModel.Block;
import TrainController.TrainController;

public class TrainModel 
{
	//CONSTANTS
	private static final double PERSON_WEIGHT = 79.3786647;
	private static final double GRAVITY = 9.81;
	private static final double KMH_MS_CONV = 0.277777778;
	private static final double FRICTION_COEFF = 0.05;
	
	//MEMBER VARIABLES
	private static TrainModelModule	m_parent;
	
	private StringBuilder 		m_log;
	private TrainController		m_trainController;
	private ArrayList<Block>	m_routeInfo;
	private int					m_blockIndex;
	private int 				m_trainID;
	private String				m_line;
	private double 				m_length;
	private double 				m_height;
	private double 				m_width;
	private double 				m_mass;
	private double				m_massEmpty;	
	private double				m_massFull;
	private int 				m_cars;
	private int 				m_crew;
	private int 				m_passengers;
	private int					m_passengersMax;
	private int 				m_authority;
	private int 				m_authorityRemaining;
	private int 				m_temperature;
	private boolean 			m_brake;
	private int 				m_powerLimit;
	private int					m_speedLimit;
	private double				m_grade;
	private double 				m_eBrakeDecel;
	private double 				m_sBrakeDecel;
	private double				m_accelMax;
	private double				m_velocityMax;
	private double 				m_power;
	private double 				m_accel;
	private double 				m_velocity;
	private double				m_setpointVelocity;
	private double 				m_position;
	private boolean 			m_emergencyBrake;
	private boolean 			m_engineFailure;
	private boolean 			m_signalFailure;
	private boolean 			m_brakeFailure;
	private boolean				m_lights;
	private boolean				m_doors;
	
	
	
	public TrainModel(int ID, String line, TrainController tc, TrainModelModule tm)
	{
		m_parent = tm;
		m_trainController = tc;
		m_routeInfo = null;
		m_blockIndex = 0;
		m_line = line;
		m_trainID = ID;
		m_length = 32.2; 
		m_width = 2.65;
		m_height = 3.42;
		m_mass = 37103.9;
		m_massEmpty = m_mass;
		m_massFull = 51437.4;
		m_powerLimit = 120; 
		m_velocityMax = 70.0;
		m_engineFailure = false;
		m_signalFailure = false;
		m_brakeFailure = false;
		m_lights = false;
		m_doors = false;
		m_power = 100.0;
		m_velocity = 0.0;
		m_position = 0.0;
		m_passengersMax = 222;
		m_eBrakeDecel = -2.73;
		m_sBrakeDecel = -1.2;
		m_accelMax = 1.0;
		m_accel = 0.0;
		m_grade = 0.0;
		m_log = new StringBuilder();
	}
	
	public TrainModel(ArrayList<ArrayList> attributes, TrainController tc )
	{
		ArrayList<Double> doublesList = attributes.get(0);
		ArrayList<Integer> intsList = attributes.get(1);
		ArrayList<Boolean> boolsList = attributes.get(2);
		
	}
	
	public void setTrainController()
	{
		m_trainController.setTrainModel(this);
	}
	
	public void tick(double timeLapse)
	{
		double force = 0;
		if(m_velocity < 0.00001) 
			force = m_power*1000 / .01;
		else
			force = m_power*1000 / m_velocity;
		/*
		System.out.println("Force: "+force);
		System.out.println("timeStep: "+timeLapse);
		System.out.println("AccelBefore: "+m_accel);
		*/
		m_accel = (force / m_mass);
		
		//System.out.println("AccelAfter: "+m_accel);
		//System.out.println("VelocityBefore: "+m_velocity);
		
		m_velocity = m_velocity + m_accel * timeLapse;
		
		//System.out.println("VelocityAfter: "+m_velocity);
		//System.out.println("PositionBefore: "+m_position);
		
		m_position = m_position + m_velocity * timeLapse;
		
		//System.out.println("PositionAfter: "+m_position);
		//String time = "" + m_parent.getSimTime();
		//String position = ""+m_position;
		//String velocity = ""+m_velocity;
		/*
		m_log = m_log  +  time.substring(time.length()-6, time.length()) + ":\t" + "Position: "+position.substring(0,4) + "\n";
		time = "" + m_parent.getSimTime();
		m_log = m_log  +  time.substring(time.length()-6, time.length()) + ":\t" + "Velocity: "+velocity.substring(0,4) + "\n";
		*/
		m_trainController.tick();
	}
	
	public void setRouteInfo(ArrayList<Block> routeInfo)
	{
		m_routeInfo = routeInfo;
	}
	
	public double getVelocity()
	{
		return m_velocity;
	}
	
	public void setSpeedLimit(int speedLimit)
	{
		m_speedLimit = speedLimit;
	}
	
	public int getSpeedLimit()
	{
		return m_speedLimit;
	}
	
	public int getTrainID()
	{
		return m_trainID;
	}
	
	public void setTrainID(int trainID)
	{
		m_trainID = trainID;
	}
	
	
	public double getMass()
	{
		return m_mass;
	}
	
	public void setMass(double mass)
	{
		m_mass = mass;
	}
	
	public int getCars()
	{
		return m_cars;
	}
	
	public void setCars(int cars)
	{
		m_cars = cars;
	}
	
	public int getCrew()
	{
		return m_crew;
	}
	
	public void setCrew(int crew)
	{
		m_crew = crew;
	}
	
	public int getPassengers()
	{
		return m_passengers;
	}
	
	public void setPassengers(int passengers)
	{
		m_passengers = passengers;
		m_log.append("Passengers Updated To "+passengers+"\n");
	}
	
	public int getAuthority()
	{
		return m_authority;
	}
	
	public void setAuthority(int authority)
	{
		m_authority = authority;
		m_log.append("Authority Updated To "+authority+"\n");
	}
	
	public int getTemperature()
	{
		return m_temperature;
	}
	
	public void setTemperature(int temperature)
	{
		m_temperature = temperature;
	}
	
	public boolean getBrake()
	{
		return m_brake;
	}
	
	public void setBrake(boolean brake)
	{
		m_brake = brake;
	}
	
	public boolean getEmergencyBrake()
	{
		return m_emergencyBrake;
	}
	
	public void toggleEmergencyBrake()
	{
		m_emergencyBrake = !m_emergencyBrake;
	}
	
	public void toggleEngineFailure()
	{
		m_engineFailure = !m_engineFailure;
	}
	
	public void toggleSignalFailure()
	{
		m_signalFailure = !m_signalFailure;
	}
	
	public void toggleBrakeFailure()
	{
		m_brakeFailure = !m_brakeFailure;
	}
	
	public boolean getLights()
	{
		return m_lights;
	}
	
	public void setLights(boolean lights)
	{
		m_lights = lights;
		if(lights)
			m_log.append("Lights Turned On \n");
		else
			m_log.append("Lights Turned Off \n");
	}
	
	public boolean getDoors()
	{
		return m_doors;
	}
	
	public void setDoors(boolean doors)
	{
		m_doors = doors;
	}
	
	public void setPower(double power)
	{
		m_power = power;
	}
	
	public double getPower()
	{
		return m_power;
	}
	
	public boolean getEngineStatus()
	{
		return m_engineFailure;
	}
	
	public boolean getSignalStatus()
	{
		return m_signalFailure;
	}
	
	public boolean getBrakeStatus()
	{
		return m_brakeFailure;
	}
	
	public double getPosition()
	{
		return m_position;
	}
	
	public double getAcceleration()
	{
		return m_accel;
	}
	
	public double getSetpointSpeed()
	{
		return m_setpointVelocity;
	}
	
	public double getGrade()
	{
		return m_grade;
	}
	
	public StringBuilder getLog()
	{
		return m_log;
	}
}


