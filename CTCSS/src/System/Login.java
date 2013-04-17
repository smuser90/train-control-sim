package System;

import Log.Log;

public class Login {
	
	private static String uName;
	
	public static boolean login(String username, String password) {
		Log.Instance().append(1, "Logged in as " + username + "\n");
		uName = username;
		return true;
	}
	
	public static void logout() {
		Log.Instance().append(3, "Logged out " + uName + "\n");
	}
}
