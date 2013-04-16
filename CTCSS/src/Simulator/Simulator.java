package Simulator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import CTC.CTCModule;
import Log.Log;
import TrackController.TrackControllerModule;
import TrackModel.Block;
import TrackModel.Line;
import TrackModel.TrackModelModule;
import TrainModel.TrainModelModule;

public class Simulator implements Runnable{
	
	// Fields
	//private static final int trainsMax = 1;
	//private static int trains = 0;
	private Log log = null;
	private boolean paused = false;
	private int realTime = 250;
	private int timeStep = 1;
	private long sysTimeNum;
	private Date sysTime;
	private DateFormat df;
	private CTCModule ctc;
	private TrackControllerModule tcm;
	private TrainModelModule tm;
	private TrackModelModule trm;
	private SpeedDialog sd;
	private Line newLine;
	
	public void run() {
		try {
			if(!paused) {
				// Put simulator to sleep for current time step
				Thread.sleep(realTime/timeStep);
				
				// Calculate and load new system time into log
				sysTimeNum += realTime;
				sysTime.setTime(sysTimeNum);
				loadLogTime();
				
				// For the current tick tell the Trains and Track Controllers to update
				tm.tick(realTime/1000.0);
				tcm.receiveTick();
				
				// Check for new tracks and load them
				if(trm.hasTrack()) {
					newLine = trm.gotTrack();
					this.loadGTrack();
				}
				/*if(trains < trainsMax)
				{
					tm.addTrain("Dicks");
					trains++;
				}*/
			} else {
				Thread.sleep(1000);
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
	
	public Simulator(CTCModule c, TrackControllerModule TcM, TrainModelModule TM, TrackModelModule Tmm) {
		ctc = c;
		tcm = TcM;
		tm = TM;
		tm.linkSimulator(this);
		trm = Tmm;
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
			tm.toggleLock();
			log.append(1, "Simulation Paused\n");
		} else {
			tm.toggleLock();
			log.append(1, "Simulation Unpaused\n");
		}
	}
	
	public SpeedDialog getSpeedDialog() {
		return this.sd;
	}
	
	public long getSimTime()
	{
		return sysTimeNum;
	}
	
	public void loadGTrack() {
		tcm.getTrack(newLine);
		//ctc.setGLine(myBlocks);
		ctc.addLine(newLine);
		log.append(1, "Track Loaded\n");
	}
	
	/*public ArrayList<Integer> getTrainIDs() {
		return null;
	}
	
	public ArrayList<Integer >getBlockIDs() {
		return null;
	}*/
	
	/* Train Actions ***********************************************************/
	
	public void scheduleTrain(String line) {
		tm.addTrain(line);
		Set<Integer> trains = tm.getTrainIDS();
		Integer [] trs = new Integer[trains.size()];
		trains.toArray(trs);		
		ArrayList<Integer> tids = new ArrayList<Integer>();
		for(int i = 0; i < trains.size(); i++) {
			tids.add(trs[i]);
		}
		ctc.setGLTrains(tids);
		tcm.receiveTrains(tm.getTrainList());
	}
	
	public void routTrain(int TrainID, int StationID) {
		
	}
	
	public void setAuthority(int trainID) {
		
	}
	
	/* BLock Actions ***********************************************************/
	
	public void setSpeedLimit(int bNum, String lName, int lim) {
		
	}
	
	public void openBLock(int bNum, String lName) {
		
	}
	
	public void closeBLock(int bNum, String lName) {
		trm.closeBlock(bNum, lName);
	}
	
	
}
