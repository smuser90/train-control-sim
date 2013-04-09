package System;

import java.util.Date;
import java.text.DateFormat;

public class SystemTime {
	private Date date;
	private DateFormat df;
	public SystemTime() {
		date = new Date();
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
	}
	
	public String getTime() {
		return df.format(date);
	}

}
