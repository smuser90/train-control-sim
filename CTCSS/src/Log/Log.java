package Log;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
	
	private static Log _instance = null;
	public LogPanel logPanel = null;
	private static StringBuilder log;
	private String st = null;
	private int limit = 100;
	private int size;
	
	private Log() {
		logPanel = new LogPanel();
		log = new StringBuilder();
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
		if(st != null) {
			log.append(st + " | ");
			System.out.print(st + " | ");
			logPanel.append(severity, st + " | ");
		}
		log.append("Severity: " + Integer.toString(severity) + " | " + text);
		System.out.print("Severity: " + Integer.toString(severity) + " | " + text);
		logPanel.append(severity, text);
	}
	
	public void writeToFile(String name) {
		try {
			FileWriter fw = new FileWriter(name);
			fw.write(this.getLog());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSysTime(String sysTime) {
		st = sysTime;
	}
	
	public String getLog() {
		return log.toString();
	}
	
}
