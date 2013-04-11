package TrainModel;

import java.util.ArrayList;

import TrackModel.Block;
import TrainController.TrainController;

public class TrainModel 
{
	private TrainController		m_trainController;
	private ArrayList<Block>	m_routeInfo;
	private int					m_blockIndex;
	private int 				m_trainID;
	private int					m_line;
	private double 				m_length;
	private double 				m_height;
	private double 				m_width;
	private double 				m_mass;
	private double				m_massEmpty;	
	private double				m_massFull;
	private int 				m_cars;
	private int 				m_crew;
	private int 				m_passengers;
	private int 				m_authority;
	private int 				m_authorityRemaining;
	private int 				m_temperature;
	private int 				m_brake;
	private int 				m_powerLimit;
	private int					m_speedLimit;
	private int[] 				m_accelRange;
	private int[] 				m_velocityRange;
	private double 				m_power;
	private double 				m_accel;
	private double 				m_velocity;
	private double 				m_position;
	private boolean 			m_emergencyBrake;
	private boolean 			m_engineFailure;
	private boolean 			m_signalFailure;
	private boolean 			m_brakeFailure;
	
	
	
	public TrainModel(int ID, int line, TrainController tc)
	{
		m_trainController = tc;
		m_routeInfo = null;
		m_line = line;
		m_trainID = ID;
		m_length = 32.2; 
		m_width = 2.65;
		m_height = 3.42;
		m_mass = 37103.9;
		m_massEmpty = m_mass;
		m_massFull = 51437.4;
		m_powerLimit = 300; 
	}
	
	public void setTrainController(TrainModel tm)
	{
		m_trainController.setTrainModel(tm);
	}
	
	public void tick(double timeLapse)
	{
		if(m_velocity == 0.0) 
			m_velocity = 0.00001;
		
		m_accel = ((m_power / m_velocity) / m_mass);
		m_velocity = m_velocity + m_accel*timeLapse;
		m_position = m_position + m_velocity*timeLapse;
		
		m_trainController.tick();
	}
	
	public double getVelocity()
	{
		return m_velocity;
	}
	
	public void setSpeedLimit(int speedLimit)
	{
		m_speedLimit = speedLimit;
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
	}
	
	public int getAuthority()
	{
		return m_authority;
	}
	
	public void setAuthority(int authority)
	{
		m_authority = authority;
	}
	
	public int getTemperature()
	{
		return m_temperature;
	}
	
	public void setTemperature(int temperature)
	{
		m_temperature = temperature;
	}
	
	public int getBrake()
	{
		return m_brake;
	}
	
	public void setBrake(int brake)
	{
		m_brake = brake;
	}
	
	public boolean getEmergencyBrake()
	{
		return m_emergencyBrake;
	}
	
	public void setEmergencyBrake(boolean emergencyBrake)
	{
		m_emergencyBrake = emergencyBrake;
	}
	
	public void setEngineFailure(boolean engineFailure)
	{
		m_engineFailure = engineFailure;
	}
	
	public void setSignalFailure(boolean signalFailure)
	{
		m_signalFailure = signalFailure;
	}
	
	public void setBrakeFailure(boolean brakeFailure)
	{
		m_brakeFailure = brakeFailure;
	}
	
}

