package com.wackycode.datagen.apps.website;

import java.util.Calendar;
import java.util.Date;

import com.wackycode.datagen.client.User;

public class WebAction {
	public Date Time;
	public String SessionId;
	public User User;
	public WebPage Page;
	
	
	public String getRecordString() {
		String delim = "\t";
		String record = "";
		
		// Format the Time
		Calendar cal = Calendar.getInstance();
		cal.setTime(Time);
		
		String year = padInt(cal.get(Calendar.YEAR)); 
		String month = padInt(cal.get(Calendar.MONTH));
		String day = padInt(cal.get(Calendar.DAY_OF_MONTH));
		String hour = padInt(cal.get(Calendar.HOUR_OF_DAY));
		String minute = padInt(cal.get(Calendar.MINUTE));
		String second = padInt(cal.get(Calendar.SECOND));
		
		String tsStr = 
			year + "-" +
			month + "-" +
			day + " " +
			hour  + ":" +
			minute  + ":" +
			second;
			
		record += 
			tsStr + delim
			+ SessionId + delim
			+ User.Id + delim
			+ Page.getUrl();
		
		return record;
	} // getRecordString
	
	private String padInt(int num) {
		String n = Integer.toString(num);
		if (num < 10) {
			n = "0" + n;
		}
		return n;
	}
}
