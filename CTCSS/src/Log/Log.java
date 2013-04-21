package Log;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
	
	private static Log _instance = null;
	public LogPanel logPanel = null;
	private static StringBuilder log1;
	private static StringBuilder log2;
	private static StringBuilder current;
	private static int l1 = 0;
	private static int lCurrent;
	private static int tCurrent;
	private String st = null;
	private int limit = 1000;
	private int size;
	private String dumpFileName;
	private String temp;
	
	private Log() {
		logPanel = new LogPanel();
		log1 = new StringBuilder();
		log2 = new StringBuilder();
		current = log1;
		lCurrent = l1;
		size = 0;
	}
	
	public static Log Instance() {
		if(_instance == null) {
			_instance = new Log();
		}
		return _instance;
	}
	
	public LogPanel getPanel() {
		if(logPanel == null) {
			Instance();
		}
		return logPanel;
	}
	
	public void append(int severity, String text) {
		if(size >= limit) {
			size = 0;
			if(dumpFileName == null)
				dumpFileName = "Log.txt";
			tCurrent = lCurrent;
			logPanel.clear();
			Thread t = new Thread(new fileWrite(1, dumpFileName));
			t.start();
			if(lCurrent == 0) {
				current = log2;
				lCurrent = 1;
			} else {
				current = log1;
				lCurrent = 0;
			}
			append(0, "Log written to file\n");
		}
		if(st != null) {
			current.append(st + " | ");
			size += st.length() + 3;
			System.out.print(st + " | ");
			logPanel.append(severity, st + " | ");
		}
		
		temp = "Severity: " + Integer.toString(severity) + " | " + text;
		current.append(temp);
		size += temp.length();
		System.out.print(temp);
		logPanel.append(severity, text);
	}
	
	private static class fileWrite implements Runnable {
		private int _mode;
		private String name;
		
		public fileWrite(int mode, String fName) {
			_mode = mode;
			name = fName;
		}
		
		public void run() {
			try {
				FileWriter fw = new FileWriter(name, true);
				if(_mode == 0)
					fw.write(getLog());
				else {
					if(tCurrent == 0) {
						fw.append(log1.toString());
						log1 = new StringBuilder();
					}
					else {
						fw.append(log2.toString());
						log2 = new StringBuilder();
					}
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeToFile(String name) {
		Thread t = new Thread(new fileWrite(0, name));
		t.start();
	}
	
	public void setSysTime(String sysTime) {
		if(st == null)
			dumpFileName = "Log" + sysTime.split(" ")[1].replaceAll(":", "") + ".txt";
		st = sysTime;
	}
	
	public static String getLog() {
		return current.toString();
	}
	
}
