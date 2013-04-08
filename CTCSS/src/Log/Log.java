package Log;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
	
	private static Log _instance = null;
	public LogPanel logPanel = null;
	private static StringBuilder log;
	
	private Log() {
		logPanel = new LogPanel();
		log = new StringBuilder();
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
	
	public String getLog() {
		return log.toString();
	}
}
