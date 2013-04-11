package Simulator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import CTC.CTCModule;
import Log.Log;

public class Simulator implements Runnable{
	
	// Fields
	private Log log = null;
	private boolean paused = false;
	private int realTime = 1000;
	private int timeStep = 1;
	private long sysTimeNum;
	private Date sysTime;
	private DateFormat df;
	private CTCModule ctc;
	private SpeedDialog sd;
	
	public void run() {
		try {
			if(!paused) {
				Thread.sleep(realTime/timeStep);
				sysTimeNum += realTime;
				sysTime.setTime(sysTimeNum);
				loadLogTime();
			} else {
				Thread.sleep(1000);
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
	
	public Simulator(CTCModule c) {
		ctc = c;
		sd = new SpeedDialog(this);
		sysTimeNum = System.currentTimeMillis();
		sysTime = new Date(sysTimeNum);
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
		log = Log.Instance();
		loadLogTime();
	}
	
	public void loadLogTime() {
		log.setSysTime(df.format(sysTime));
	}
	
	public void setSimSpeed(int speed) {
		if(speed == -1) {
			log.append(3, "Speed must be a number 'x' s.t. 1 <= x <= 10\n");
		} else {
			timeStep = speed;
			log.append(1, "Speed set to " + Integer.toString(timeStep) + "*real time\n" );
		}
	}
	
	public void togglePause() {
		paused = !paused;
		if(paused) {
			log.append(1, "Simulation Paused\n");
		} else {
			log.append(1, "Simulation Unpaused\n");
		}
	}
	
	public SpeedDialog getSpeedDialog() {
		return this.sd;
	}
	
	public ArrayList<Integer> getTrainIDs() {
		return null;
	}
	
	public ArrayList<Integer >getBlockIDs() {
		return null;
	}
	
	public void routTrain(int TrainID, int StationID) {
		
	}
	
	public void scheduleTrain() {
		
	}
	
	public void closeBlock(int blockID) {
		
	}
	
	public void openBlock(int blockID) {
		
	}
	
	public void setAuthority(int trainID) {
		
	}
	
	public void setSpeedLimit(int blockID) {
		
	}
}
