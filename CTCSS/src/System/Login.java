package System;

import Log.Log;

public class Login {
	
	public static boolean login(String username, String password) {
		Log.Instance().append(1, "Logged in as " + username + "\n");
		return true;
	}
	
	public static void logout() {
		Log.Instance().append(3, "Logged out Optimus\n");
	}
}
