/*
 * 	TrainModel.java
 * 	Train Model Class - Models Physics and Actions of Train
 * 	Author: Charles Musso
 * 	Date Created: 04/07/13
 * 	Date Last Updated: 4/25/13
 * 
 */

package TrainModel;

import java.util.ArrayList;
import TrackModel.Block;
import TrainController.TrainController;

public class TrainModel 
{
	//CONSTANTS
	private static final double PERSON_WEIGHT = 69;
	private static final double GRAVITY = 9.81;
	private static final double FRICTION_COEFF = 0.05;
	
	//MEMBER VARIABLES
	private static TrainModelModule	m_parent;
	
	private StringBuilder 		m_log;
	private TrainController		m_trainController;
	private ArrayList<Block>	m_routeInfo;
	private int					m_routeLength;
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
	private boolean				m_printFlag;
	private boolean 			m_writeLog = true;
	private boolean				m_atStation = true;
	private int 				m_passengerTotal = 0;
	private long 				m_time = 0;
	
	/**
	 * Create the Train Model
	 * @param ID the TrainID
	 * @param line the Line to place train on
	 * @param tc the TrainController associated
	 * @param tm the parent TrainModelModule
	 */
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
		m_printFlag = true;
		m_power = 0.0; 
		m_velocity = 0.0;
		m_position = 0.0;
		m_passengersMax = 222;
		m_eBrakeDecel = -2.73;
		m_sBrakeDecel = -1.2;
		m_accelMax = 1.0;
		m_accel = 0.0;
		m_grade = 0.0;
		m_log = new StringBuilder();
		m_printFlag = true;
	}
	
	/**
	 * set the trainController reference to this
	 */
	public void setTrainController()
	{
		m_trainController.setTrainModel(this);
	}
	
	/**
	 * main update method. drives simulation data forward
	 * @param timeLapse the amount of time that has passed in the simulation
	 */
	public void tick(double timeLapse)
	{
		double force = 0;
		
		m_time += 250;
		
		if(m_atStation)
		{
			if(m_printFlag)
			{
				
				
				if(m_routeInfo.get(m_blockIndex).getType() != -1) 
				{
					m_log.append("At "+m_routeInfo.get(m_blockIndex).getStationName()+"\n\n");
					toggleDoors();
					setCrew((int)(Math.random()*3+1));
					updatePassengers();
					toggleDoors();
				}
				else
				{
					m_log.append("At Yard\n\n");
				}
				
				m_log.append("Awaiting Route Data\n\n");
				m_parent.writeLog(3, "Train:"+m_trainID+" awaiting new route data\n");
				m_writeLog = true;
				m_printFlag = false;
			}
			
			return;
			
		}
			
		m_printFlag = true;
		
		//Check FailSafes
		if(m_engineFailure == true || m_signalFailure == true || m_brakeFailure == true)
		{
			m_power = 0.0;
			m_emergencyBrake = true;
		}
		
		if(m_velocity < 0.00001) 
			force = m_power / 0.1;
		else
			force = m_power / m_velocity;

		m_mass = m_passengers*PERSON_WEIGHT + m_massEmpty;
		
		m_accel = force / m_mass; 
		
		if(m_accel > m_accelMax) m_accel = m_accelMax;
		
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
    			m_velocity = 0.0;
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
    			m_velocity = 0.0;
    		}
    		else
    			m_accel = m_eBrakeDecel;
    	}
    	
    	// Update Velocity
		m_velocity = m_velocity + m_accel * timeLapse;
		
		// Limit Upper Bound
		if(m_velocity > m_velocityMax)
			m_velocity = m_velocityMax;
		
		m_position = m_position + m_velocity * timeLapse;
		
		//Shifting blocks
		if(m_position >= m_routeInfo.get(m_blockIndex).getLength() && m_routeInfo.size() > m_blockIndex+1)
		{
			m_position = m_position - m_routeInfo.get(m_blockIndex).getLength();
			
			m_routeInfo.get(m_blockIndex).setOccupied(false);
			m_blockIndex++;
			
			if(m_authority > 0) m_authority--;
			
			m_log.append("Entering Block" + m_routeInfo.get(m_blockIndex).getBlockNumber()+"\n\n");
			m_writeLog = true;
			m_routeInfo.get(m_blockIndex).setOccupied(true);
			m_routeInfo.get(m_blockIndex).trainOnBlock(m_trainID);
			m_speedLimit = m_routeInfo.get(m_blockIndex).getSpeedLimit();
		}
		
		//On last block
		if(m_routeInfo.size() == m_blockIndex+1)
		{
			//Ideal
			if(m_velocity == 0.0)
			{
				m_atStation = true;
				m_printFlag = true;
				m_writeLog = true;
				m_trainController.setTick(false);
			}
			//Ideally shouldn't hit this, Failing gracefully
			if(m_position >= m_routeInfo.get(m_blockIndex).getLength())
			{
				m_position = m_routeInfo.get(m_blockIndex).getLength();
				m_atStation = true;
				m_printFlag = true;
				m_writeLog = true;
				m_trainController.setTick(false);
				m_log.append("Missed the Station \n\n");
			}
			
		}
		
		m_trainController.tick(timeLapse);
	}
	
	/**
	 * Controls log writes to keep from continuously writing redundant data
	 * @param log the log associated with the module
	 */
	public void setWriteLog(boolean log)
	{
		m_writeLog = log;
	}
	
	/**
	 * get the status of writing to the log
	 * @return status of log writing
	 */
	public boolean getWriteLog()
	{
		return m_writeLog;
	}
	
	/**
	 * get passengers per hour on the train for metrics
	 * @return passengers per hour
	 */
	public double getPassengersPerHour()
	{
		if(m_time > 0)
			return ((double) m_passengerTotal) / (((double) m_time)/3600000.0);
		return 0;
	}
	
	/**
	 * set the route information
	 * @param routeInfo list of blocks for train to traverse
	 */
	public void setRouteInfo(ArrayList<Block> routeInfo)
	{
		System.out.println("Setting Route Info");
		m_routeInfo = routeInfo;
		m_blockIndex = 0;
		m_routeLength = m_routeInfo.size();
		m_authority = m_routeLength - 1;
		m_routeInfo.get(m_blockIndex).setOccupied(true);
		m_setpointVelocity = m_routeInfo.get(m_blockIndex).getSpeedLimit();
		m_speedLimit =  m_routeInfo.get(m_blockIndex).getSpeedLimit();
		
		
		//Routed somewhere besides yard
		if(m_routeInfo.size() > 1 )
		{
			System.out.println("Routed to legit");
			if(m_emergencyBrake) 
				toggleEmergencyBrake();
			m_log.append("Route Data Received. *Train Departing*\n\n");
			m_writeLog = true;
			m_atStation = false;
			m_trainController.setTick(true);
		}
		
		for(int i = 0; i < m_routeInfo.size(); i++) {
			System.out.println(m_routeInfo.get(i).getBlockNumber());
		}
	}
	
	/**
	 * get the block the train is currently occupying
	 * @return block number
	 */
	public int getCurrentBlock()
	{
		if(m_routeInfo == null)
			return 0;
		return m_routeInfo.get(m_blockIndex).getBlockNumber();
	}
	
	/**
	 * set the crew count of the train
	 * @param crew the count of crew members
	 */
	public void setCrew(int crew)
	{
		m_crew = crew;
		m_log.append("Crew Updated To "+m_crew+"\n\n");
		m_writeLog = true;
	}
	
	/**
	 * set the passenger count of the train
	 * @param passengers the count of passengers aboard
	 */
	public void setPassengers(int passengers)
	{
		m_passengers = passengers;
		m_log.append("Passengers Updated To "+passengers+"\n\n");
		m_writeLog = true;
	}
	
	/**
	 * toggles the emergency brake
	 */
	public void toggleEmergencyBrake()
	{
		m_emergencyBrake = !m_emergencyBrake;
		if(m_emergencyBrake)
		{
			m_log.append("Emergency Brake Engaged. *Train Stopping*\n\n");
		}
		else
		{
			m_log.append("Emergency Brake Disengaged. *Train Restarting*\n\n");
		}
		m_writeLog = true;
	}
	
	/**
	 * toggle the engine failure status
	 */
	public void toggleEngineFailure()
	{
		m_engineFailure = !m_engineFailure;
		if(m_engineFailure)
			m_log.append("Engine Failure. *Train Stopping*\n\n");
		else
			m_log.append("Engine Functioning. *Train Restarting*\n\n");
		m_writeLog = true;
	}
	
	/**
	 * toggles the signal failure status
	 */
	public void toggleSignalFailure()
	{
		m_signalFailure = !m_signalFailure;
		if(m_signalFailure)
			m_log.append("Signal Failure. *Train Stopping*\n\n");
		else
			m_log.append("Signals Functioning. *Train Restarting*\n\n");
		m_writeLog = true;
	}
	
	/**
	 * toggles the brake failure status
	 */
	public void toggleBrakeFailure()
	{
		m_brakeFailure = !m_brakeFailure;
		if(m_brakeFailure)
			m_log.append("Brake Failure. *Train Stopping*\n\n");
		else
			m_log.append("Brakes Funnctioning. *Train Restarting*\n\n");
		m_writeLog = true;
	}
	
	/**
	 * toggles the light status (on/off)
	 */
	public void toggleLights()
	{
		m_lights = !m_lights;
		if(m_lights)
			m_log.append("Lights Turned On \n\n");
		else
			m_log.append("Lights Turned Off \n\n");
		m_writeLog = true;
	}
	
	/**
	 * toggles the door status (open/closed)
	 */
	public void toggleDoors()
	{
		m_doors = !m_doors;
		if(m_doors)
			m_log.append("Doors Opened\n\n");
		else
			m_log.append("Doors Closed\n\n");
		m_writeLog = true;
	}
	
	/**
	 * get the route info of the train
	 * @return List of Blocks in the train's route information
	 */
	public ArrayList<Block> getRouteInfo()
	{
		return m_routeInfo;
	}
	
	/**
	 * returns the length of the route
	 * @return route length
	 */
	public int getRouteLength()
	{	
		return m_routeLength;
	}
	
	/**
	 * returns the current block length
	 * @return current block length
	 */
	public int getBlockLength()
	{
		
		return m_routeInfo.get(m_blockIndex).getLength();
	}
	
	/**
	 * returns the name of the line the train occupies 
	 * @return name of the occupied line
	 */
	public String getLine()
	{
		return m_line;
	}
	
	/**
	 * returns the current block index in the routeInfo
	 * @return current block index
	 */
	public int getBlockIndex()
	{
		return m_blockIndex;
	}
	
	/**
	 * returns the current block occupied
	 * @return current block occupied
	 */
	public Block getBlock()
	{
		return m_routeInfo.get(m_blockIndex);
	}
	
	/**
	 * returns the current velocity of the train
	 * @return velocity of train
	 */
	public double getVelocity()
	{
		return m_velocity;
	}
	
	/**
	 * set the speed limit of the train
	 * @param speedLimit 
	 */
	public void setSpeedLimit(int speedLimit)
	{
		m_speedLimit = speedLimit;
	}
	
	/**
	 * get the current speed limit of the train
	 * @return current Speed Limit
	 */
	public int getSpeedLimit()
	{
		return m_speedLimit;
	}
	
	/**
	 * get the trainID of this
	 * @return trainID
	 */
	public int getTrainID()
	{
		return m_trainID;
	}
	
	/**
	 * set the trainID of this
	 * @param trainID
	 */
	public void setTrainID(int trainID)
	{
		m_trainID = trainID;
	}
	
	/**
	 * returns the mass of the train
	 * @return mass of train - including cargo
	 */
	public double getMass()
	{
		return m_mass;
	}
	
	/**
	 * returns the count of cars the train consists of
	 * @return car count
	 */
	public int getCars()
	{
		return m_cars;
	}
	
	/**
	 * sets the car count of the train
	 * @param cars
	 */
	public void setCars(int cars)
	{
		m_cars = cars;
	}
	
	/**
	 * returns the current crew count
	 * @return crew
	 */
	public int getCrew()
	{
		return m_crew;
	}
	
	/**
	 * returns the current passenger count
	 * @return passenger
	 */
	public int getPassengers()
	{
		return m_passengers;
	}
	
	/**
	 * returns the current authority of the train
	 * @return authority
	 */
	public int getAuthority()
	{
		return m_authority;
	}
	
	/**
	 * sets the current authority of the train
	 * @param authority
	 */
	public void setAuthority(int authority)
	{
		m_authority = authority;
		m_log.append("Authority Updated To "+authority+"\n\n");
		m_writeLog = true;
	}
	
	/**
	 * get the current temperature of the train
	 * @return temperature
	 */
	public int getTemperature()
	{
		return m_temperature;
	}
	
	/**
	 * set the current temperature of the train
	 * @param temperature
	 */
	public void setTemperature(int temperature)
	{
		m_temperature = temperature;
	}
	
	/**
	 * get the service brake status (engaged/disengaged)
	 * @return service brake status
	 */
	public boolean getBrake()
	{
		return m_brake;
	}
	
	/**
	 * set the service brake status (engaged/disengaged)
	 * @param service brake status
	 */
	public void setBrake(boolean brake)
	{
		m_brake = brake;
	}
	
	/**
	 * get the emergency brake status (engaged/disengaged)
	 * @return emergency brake status
	 */
	public boolean getEmergencyBrake()
	{
		return m_emergencyBrake;
	}
	
	/** get the current status of the lights (on/off)
	 * @return light status
	 */
	public boolean getLights()
	{
		return m_lights;
	}
	
	/**
	 * get the current door status (open/closed)
	 * @return door status
	 */
	public boolean getDoors()
	{
		return m_doors;
	}
	
	/**
	 * set the current power of the train
	 * @param power
	 */
	public void setPower(double power)
	{
		m_power = power;
	}
	
	/**
	 * get the current power of the train
	 * @return power
	 */
	public double getPower()
	{
		return m_power;
	}
	
	/**
	 * get engine status of the train
	 * @return engine status
	 */
	public boolean getEngineStatus()
	{
		return m_engineFailure;
	}
	
	/**
	 * get signal status of the train
	 * @return signal status
	 */
	public boolean getSignalStatus()
	{
		return m_signalFailure;
	}

	/**
	 * get brake status of the train
	 * @return brake status
	 */
	public boolean getBrakeStatus()
	{
		return m_brakeFailure;
	}
	
	/**
	 * get current position of the train on the current block
	 * @return position on the current block
	 */
	public double getPosition()
	{
		return m_position;
	}
	
	/**
	 * get current acceleration of the train
	 * @return acceleration
	 */
	public double getAcceleration()
	{
		return m_accel;
	}
	
	/**
	 * get current setpoint speed of the train
	 * @return setpoint speed
	 */
	public double getSetpointSpeed()
	{
		return m_setpointVelocity;
	}
	
	/**
	 * set current setpoint speed of the train
	 * @param setpoint speed
	 */
	public void setSetpointSpeed(double speed)
	{
		m_setpointVelocity = speed;
	}
	
	/**
	 * get grade of the current block
	 * @return grade
	 */
	public double getGrade()
	{
		return m_grade;
	}
	
	/**
	 * get log associated with THIS train
	 * @return log
	 */
	public StringBuilder getLog()
	{
		return m_log;
	}
	
	/**
	 * get power limit of the train
	 * @return power limit
	 */
	public int getPowerLimit()
	{
		return m_powerLimit;
	}
	
	/* update passenger count and current mass*/
	private void updatePassengers()
	{
		setPassengers((int)(Math.random()*m_passengersMax));
		m_passengerTotal += m_passengers;
		m_mass = m_massEmpty + (m_passengerTotal + m_crew) * PERSON_WEIGHT;
	}
}


