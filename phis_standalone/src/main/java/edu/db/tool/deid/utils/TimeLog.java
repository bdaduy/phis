package edu.db.tool.deid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Duy Bui
 */
public class TimeLog {



	private static boolean DEBUG = false;
        
        public static String getCurrentTime(){
            return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US).format(new Date());
        }

	public static long getCurrent(){
		return System.currentTimeMillis();
	}

	public static long getSpan(long baseline) {

		long span = System.currentTimeMillis() - baseline;

		return span;

	}
	
	public static double getSpanMin(long baseline) {

		

		return getSpan(baseline)/(60.0*1000);

	}
	public static int getSpanSec(long baseline) {

		

		return (int) (getSpan(baseline)/1000);

	}

	
	private static double toMinute(long mseconds){
		return mseconds/(60.0*1000);
	}
	


}
