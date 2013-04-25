/*
 *		TrackModelModule.java
 *		This is the TrackModelModule, mainly used for setting up parts of the TrackModel. 
 *		Author: Kai Manuel
 *		Date Created: 04/07/2013
 *		Date Last Updated: 04/25/2013
 */

package TrackModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Log.Log;
import TrainModel.TrainModel;

public class TrackModelModule {
	private TrackModelPanel gui = null;
	private Track tr;
	private boolean hasTrack = false;
	
	/**
	 * Makes a new TMM
	 */
	public TrackModelModule() 
	{
		gui = new TrackModelPanel(this);
		tr = new Track(gui);
	}
	
	/**
	 * Returns a Panel
	 * @return
	 */
	public TrackModelPanel getPanel() {
		return gui;
	}
	
	/**
	 * Loads the file selected into the program
	 * @param f
	 */
	public void getLineFile(File f)
	{
		hasTrack = tr.getLineFile(f);
		if(hasTrack)
			addLine(tr.getNewLine());
	}
	
	/**
	 * Returns a Track file
	 * @return
	 */
	public Track getTrack()
	{
		return tr;
	}
	
	/**
	 * Returns true if the Module has a track object
	 * @return
	 */
	public boolean hasTrack() {
		return hasTrack;
	}
	
	/**
	 * Returns a Line and signals that a Line has been passed
	 * @return
	 */
	public Line gotTrack() {
		hasTrack = false;
		return tr.getNewLine();
	}
	
	/**
	 * Adds a Line to the gui
	 * @param l
	 */
	private void addLine(Line l) {
		gui.addLine(l);
	}
	
	/**
	 * Other methods call this to close a block
	 * @param bNum
	 * @param lineName
	 */
	public void closeBlock(int bNum, String lineName)
	{
		tr.getLine(lineName).getBlock(bNum).breakBlock();
	}
	
	/**
	 * Opens a block on a line
	 * @param bNum
	 * @param lineName
	 */
	public void openBlock(int bNum, String lineName)
	{
		tr.getLine(lineName).getBlock(bNum).fixBlock();
	}
	 
	/**
	 * Sets the speed Limit for a block
	 * @param bNum
	 * @param lineName
	 * @param newSpLim
	 */
	public void setSpeedLimit(int bNum, String lineName, int newSpLim)
	{
		tr.getLine(lineName).getBlock(bNum).setSpeedLimit(newSpLim);
	}
	
	/**
	 * Calls the private routing function
	 * @param train
	 * @param start
	 * @param end
	 * @param l
	 */
	public void route(TrainModel train, int start, int end, Line l) {
		tr.route(train, start, end, l);
	}
	
	/**
	 * Custom printing function
	 */
	public void printOpen() {
		for(int i = 0; i < tr.getLines().size(); i++) {
			System.out.println(tr.getLines().get(i).getName());
			for(int j = 0; j < tr.getLines().get(i).getBlocks().size(); j++) {
				System.out.println(tr.getLines().get(i).getBlocks().get(j).getBlockNumber() + ":" + tr.getLines().get(i).getBlocks().get(j).getSpeedLimit());
			}
		}
	}
	
	/**
	 * Adds a block to the Line specified
	 * @param lName
	 * @param prevBlock
	 */
	public void addBlock(String lName, int prevBlock) {
		Log.Instance().append(3, "Adding Blocks not implemented yet!\n");
	}
	
	/**
	 * Removes a block from the Line specified
	 * @param lName
	 * @param blockNum
	 */
	public void removeBlock(String lName, int blockNum) {
		Log.Instance().append(3, "Removing Blocks not implemented yet!\n");
	}
}
