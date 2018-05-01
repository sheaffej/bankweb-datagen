package com.wackycode.datagen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utilities {

	public static Random rand = new Random();
	private static int[] distArray = {
		1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, // 15
		2,2,2,2,2,2,2,2,	// 8
		3,3,3,3, // 4
		4,4,	
		5, 
		6,
		7,
		8,
		9,
		10
	};
	
	public static Date nextDate(Date start, int maxNextSecs) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
//		double r = (double) Utilities.rand.nextInt(maxNextSecs);
//		double r2 = Math.log(r);
//		int r3 = (int)r2;
//		cal.add(Calendar.SECOND, r3);
//		
//		System.out.println(r+" "+r2+" "+r3);

		int r = Utilities.rand.nextInt(Utilities.distArray.length);
		int level = Utilities.distArray[r];
		int adjMaxSecs = (maxNextSecs * level) / 10;
		int secs = Utilities.rand.nextInt(adjMaxSecs);
		int msecs = secs*10;
		cal.add(Calendar.MILLISECOND, msecs);

//		System.out.println(secs + "  "+ r + " " + level + "  " + adjMaxSecs);
		return cal.getTime();
	} // static nextDate
}
