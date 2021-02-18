package com.bluetail.aero.automation;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @description This class contains method to get the current Time Stamp
 * @createdAt 02/17/2020
 * @modifiedAt
 * @Modifiedby
 * @author Ashok.
 */

public class CommonFunctions {
	
	
	public static String getCurrentDateTimeStamp() {

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		String timeStamp = dateFormat.format(cal.getTime());
		return timeStamp;

	}
	
	public static String DynamicValidEmail() {
		String email = "";
		email = "ashok" + getCurrentDateTimeStamp() + "@gmail.com";
		return email;
		
	}
	
	public static String DynamicInValidEmail() {
		String email = "";
		email = "ashok" + getCurrentDateTimeStamp() + "...&&&&";
		return email;
		
	}

}
