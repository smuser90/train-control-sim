package System;

import java.text.DateFormat;
import java.util.Date;

import Log.Log;

public class Simulator implements Runnable{
	
	Log log = null;
	boolean paused = true;
	int realTime = 100;
	int timeStep = 1;
	long sysTimeNum;
	Date sysTime;
	DateFormat df;
	
	public void run() {
		try {
			Thread.sleep(realTime*timeStep);
			sysTimeNum += realTime*timeStep;
			sysTime.setTime(sysTimeNum);
			loadLogTime();
			log.append(2, "New Time\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
	
	public Simulator() {
		sysTimeNum = System.currentTimeMillis();
		sysTime = new Date(sysTimeNum);
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
		log = Log.Instance();
		loadLogTime();
	}
	
	public void loadLogTime() {
		log.setSysTime(df.format(sysTime));
	}
}
