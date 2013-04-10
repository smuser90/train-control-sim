package Simulator;

import java.text.DateFormat;
import java.util.Date;

import CTC.CTCModule;
import Log.Log;

public class Simulator implements Runnable{
	
	// Fields
	Log log = null;
	boolean paused = true;
	int realTime = 1000;
	int timeStep = 1;
	long sysTimeNum;
	Date sysTime;
	DateFormat df;
	CTCModule ctc;
	
	public void run() {
		try {
			Thread.sleep(realTime*timeStep);
			sysTimeNum += realTime*timeStep;
			sysTime.setTime(sysTimeNum);
			loadLogTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
	
	public Simulator(CTCModule c) {
		ctc = c;
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
		timeStep = speed;
	}
}
