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
		m_powerLimit = 120000; 
		m_velocityMax = 70.0;
		m_engineFailure = false;
		m_signalFailure = false;
		m_brakeFailure = false;
		m_lights = false;
		m_doors = false;
		m_power = 0.0; //watts
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
	
	
	public void setTrainController()
	{
		m_trainController.setTrainModel(this);
	}
	
	public void tick(double timeLapse)
	{
		double force = 0;
		
		if(m_velocity < 0.00001) 
			force = m_power / 0.01;
		else
			force = m_power / m_velocity;

		m_accel = force / m_mass;
		
		// Slope of Rail
    	m_accel = m_accel - GRAVITY*Math.sin(m_grade);
    	
    	// Friction
    	m_accel = m_accel - (m_accel * FRICTION_COEFF);
		
    	// Braking - Service
    	if(m_brake) 
    	{
    		if(m_velocity < 0.00001)
    		{
    			m_accel = 0.0;
    			m_brake = false;
    		}
    		else
    			m_accel = m_sBrakeDecel;
    	}
    	
    	// Braking - Emergency
    	if(m_emergencyBrake)
    	{
    		if(m_velocity < 0.00001)
    		{
    			m_accel = 0.0;
    			m_emergencyBrake = false;
    		}
    		else
    			m_accel = m_eBrakeDecel;
    	}
    	
		m_velocity = m_velocity + m_accel * timeLapse;
		
		if(m_velocity > m_velocityMax) //Limit upper bound
			m_velocity = m_velocityMax;
		
		m_position = m_position + m_velocity * timeLapse;
		
		/*
		if(m_position >= m_routeInfo.get(m_blockIndex).getLength() )
		{
			m_position = m_position - m_routeInfo.get(m_blockIndex).getLength();
			
			m_routeInfo.get(m_blockIndex).setOccupied(false);
			m_blockIndex++;
			m_routeInfo.get(m_blockIndex).setOccupied(true);
			m_speedLimit = m_routeInfo.get(m_blockIndex).getSpeedLimit();
		}
		*/
		m_trainController.tick(timeLapse);
	}
	
	public void setRouteInfo(ArrayList<Block> routeInfo)
	{
		m_routeInfo = routeInfo;
		m_routeInfo.get(m_blockIndex).setOccupied(true);
	}
	
	public String getLine()
	{
		return m_line;
	}
	public int getBlockIndex()
	{
		return m_blockIndex;
	}
	
	public Block getBlock()
	{
		return m_routeInfo.get(m_blockIndex);
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
		if(m_emergencyBrake)
			m_log.append("Emergency Brake Engaged. Train Stopping!\n");
		else
			m_log.append("Engine Functioning. Train Restarting!\n");
	}
	
	public void toggleEngineFailure()
	{
		m_engineFailure = !m_engineFailure;
		if(m_engineFailure)
			m_log.append("Engine Failure. Train Stopping!\n");
		else
			m_log.append("Engine Functioning. Train Restarting!\n");
	}
	
	public void toggleSignalFailure()
	{
		m_signalFailure = !m_signalFailure;
		if(m_signalFailure)
			m_log.append("Signal Failure. Train Stopping!\n");
		else
			m_log.append("Signals Functioning. Train Restarting!\n");
	}
	
	public void toggleBrakeFailure()
	{
		m_brakeFailure = !m_brakeFailure;
	}
	
	public boolean getLights()
	{
		return m_lights;
	}
	
	public void toggleLights()
	{
		m_lights = !m_lights;
		if(m_lights)
			m_log.append("Lights Turned On \n");
		else
			m_log.append("Lights Turned Off \n");
	}
	
	public boolean getDoors()
	{
		return m_doors;
	}
	
	public void toggleDoors()
	{
		m_doors = !m_doors;
		if(m_doors)
			m_log.append("Doors Opened\n");
		else
			m_log.append("Doors Closed\n");
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
	
	public void setSetpointSpeed(double speed)
	{
		m_setpointVelocity = speed;
	}
	public double getGrade()
	{
		return m_grade;
	}
	
	public StringBuilder getLog()
	{
		return m_log;
	}
	
	public int getPowerLimit()
	{
		return m_powerLimit;
	}
}


