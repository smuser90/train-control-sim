package System;

import Log.Log;

public class Login {
	
	private static String uName;
	
	public static boolean login(String username, String password) {
		Log.Instance().append(1, "Logged in as " + username + "\n");
		uName = username;
		
		return true; //authenticate(password);
	}
	
	public static void logout() {
		Log.Instance().append(3, "Logged out " + uName + "\n");
	}
	
	private static boolean authenticate(String pw) {
		if(uName.equals("admin") && pw.equals("123456")) 
		{
			return true;
		}
		return false;
	}
}
