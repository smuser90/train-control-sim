/*
 * Log.java
 * Log class to provide logging facilities to the modules
 * Author: Nikolas Parshook
 * Date Created: 04/07/2013
 * Date Last Updated: 04/21/2013
 */

package Log;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Log class provides system logging
 * @author Nikolas Parshook
 *
 */
public class Log 
{
	
	// Fields
	private static Log _instance = null;
	public LogPanel logPanel = null;
	private static StringBuilder log1;
	private static StringBuilder log2;
	private static StringBuilder current;
	private static int l1 = 0;
	private static int lCurrent;
	private static int tCurrent;
	private String st = null;
	private int limit = 10000;
	private int size;
	private String dumpFileName;
	private String temp;
	
	/* Private constructor to prevent multiple copies of the log from being created */
	private Log() {
		logPanel = new LogPanel();
		log1 = new StringBuilder();
		log2 = new StringBuilder();
		current = log1;
		lCurrent = l1;
		size = 0;
	}
	
	/**
	 * Call to get the instance of the log
	 * @return returns an instance of Log
	 */
	public static Log Instance() 
	{
		if(_instance == null) 
		{
			_instance = new Log();
		}
		return _instance;
	}
	
	/**
	 * Call to get the logs JPanel
	 * @return returns the logs display panel
	 */
	public LogPanel getPanel()
	{
		if(logPanel == null) 
		{
			Instance();
		}
		return logPanel;
	}
	
	
	/**
	 * Append to the log
	 * @param severity an int to describe how severe the event that is writing to log is
	 * @param text message to the log
	 */
	public void append(int severity, String text) 
	{
		if(size >= limit) 
		{
			size = 0;
			if(dumpFileName == null) {
				dumpFileName = "Log.txt";
			}
			tCurrent = lCurrent;
			logPanel.clear();
			Thread t = new Thread(new fileWrite(1, dumpFileName));
			t.start();
			if(lCurrent == 0) 
			{
				current = log2;
				lCurrent = 1;
			} 
			else 
			{
				current = log1;
				lCurrent = 0;
			}
			append(0, "Log written to file\n");
		}
		if(st != null) 
		{
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
	
	private static class fileWrite implements Runnable 
	{
		private int _mode;
		private String name;
		
		public fileWrite(int mode, String fName) 
		{
			_mode = mode;
			name = fName;
		}
		
		public void run() 
		{
			try 
			{
				FileWriter fw = new FileWriter(name, true);
				if(_mode == 0) {
					fw.write(getLog());
				}
				else 
				{
					if(tCurrent == 0)
					{
						fw.append(log1.toString());
						log1 = new StringBuilder();
					}
					else 
					{
						fw.append(log2.toString());
						log2 = new StringBuilder();
					}
				}
				fw.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes to the specified file
	 * @param name file to be written to.
	 */
	public void writeToFile(String name) 
	{
		Thread t = new Thread(new fileWrite(0, name));
		t.start();
	}
	
	/**
	 * Sets the time for the log to use in messages
	 * @param sysTime
	 */
	public void setSysTime(String sysTime) 
	{
		if(st == null)
			dumpFileName = "Log" + sysTime.split(" ")[1].replaceAll(":", "") + ".txt";
		st = sysTime;
	}
	
	/**
	 * Get the string representation of the log
	 * @return String
	 */
	public static String getLog() 
	{
		return current.toString();
	}
	
	/**
	 * Causes the log to write to its dumpfile
	 */
	public void writeToDumpFile() 
	{
		Thread t = new Thread(new fileWrite(1, dumpFileName));
		t.start();
	}
	
}
