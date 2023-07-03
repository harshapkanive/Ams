package in.infrasupport.hr.ams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	
	private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    public static String getCurrentDateAsString()
    {
        Calendar localCalendar = Calendar.getInstance();
        return dateFormatter.format(localCalendar.getTime());
    }

    public static String getCurrentTimeStampAsString()
    {
        Calendar localCalendar = Calendar.getInstance();
        return dateTimeFormatter.format(localCalendar.getTime());
    }	
	
    
    public static String sqlDateToString(java.sql.Date sqlDt)
    {
    	java.util.Date dt = new java.util.Date(sqlDt.getTime());
    	return dateFormatter.format(dt.getTime());
    }

    public static String sqlTSPToString(java.sql.Timestamp sqlTsp)
    {
    	java.util.Date dt = new java.util.Date(sqlTsp.getTime());
    	return dateTimeFormatter.format(dt.getTime());
    }    

    public static java.sql.Date strToSQLDate(String strDate)
    {
    	Date date = null;
    	try {
			date = dateFormatter.parse(strDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return new java.sql.Date(date.getTime());
    }
    
    public static java.sql.Timestamp strToSQLDateTime(String strDate)
    {
    	
    	Date date = null;
    	try {
			date = dateTimeFormatter.parse(strDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
			String mod = strDate.replace(".", "");
			try {
				date = dateTimeFormatter.parse(mod);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    	return new java.sql.Timestamp(date.getTime());
    }
        
    
    public static void main(String[] args) {
		strToSQLDateTime("02-01-2017 06:14:55 p.m.");
	}
    
}
