/*
 * Login.java
 * Class for handling logging in and out
 * Author: Nikolas Parshook
 * Date Created: 04/10/2013
 * Date Last Updated: 04/21/2013
 */

package System;

import Log.Log;

/**
 * Class for handling logging in and out
 * @author Nikolas Parshook
 *
 */
public class Login 
{
	//Fields
	private static String uName;
	
	/**
	 * Login to the system
	 * @param username
	 * @param password
	 * @return true if successful, false otherwise
	 */
	public static boolean login(String username, String password) 
	{
		Log.Instance().append(1, "Logged in as " + username + "\n");
		uName = username;
		
		return true; //authenticate(password);
	}
	
	/**
	 * Logout of the system
	 */
	public static void logout() 
	{
		Log.Instance().append(3, "Logged out " + uName + "\n");
	}
	
	/* check password */
	private static boolean authenticate(String pw) 
	{
		if(uName.equals("admin") && pw.equals("123456")) 
		{
			return true;
		}
		return false;
	}
}
