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
	private static final int trainsMax = 1;
	private static int trains = 0;
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
	private ArrayList<Block> myBlocks;
	private Line newLine;
	
	public void run() {
		try {
			if(!paused) {
				Thread.sleep(realTime/timeStep);
				sysTimeNum += realTime;
				sysTime.setTime(sysTimeNum);
				loadLogTime();
				
				
				if(trains < trainsMax)
				{
					tm.addTrain("Dicks");
					trains++;
				}
				
				
				tm.tick(realTime/1000.0);
				
				if(trm.hasTrack()) {
					newLine = trm.gotTrack();
					this.loadGTrack();
				}
			} else {
				Thread.sleep(1000);
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.run();
	}
	
	public Simulator(CTCModule c, TrackControllerModule TcM, TrainModelModule TM, TrackModelModule Tmm) {
		/*myBlocks = new ArrayList<Block>();
		for (int blockCount = 0; blockCount < 5; blockCount++) {
			Block blk = new Block(blockCount);
			//blk.setBlockNumber(blockCount);
			if(blockCount == 1)
				blk.setType(1);
			if(blockCount == 2)
				blk.setType(2);
			if(blockCount == 4)
				blk.setType(3);
			myBlocks.add(blk);
		}*/
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
	
	public ArrayList<Integer> getTrainIDs() {
		return null;
	}
	
	public ArrayList<Integer >getBlockIDs() {
		return null;
	}
	
	public void routTrain(int TrainID, int StationID) {
		
	}
	
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
	}
	
	public void closeBlock(int blockID) {
		
	}
	
	public void openBlock(int blockID) {
		
	}
	
	public void setAuthority(int trainID) {
		
	}
	
	public void setSpeedLimit(int blockID) {
		
	}
	
	public void loadGTrack() {
		//tcm.getTrack(myBlocks);
		//ctc.setGLine(myBlocks);
		ctc.addLine(newLine);
		log.append(1, "Track Loaded\n");
	}
}
