package in.infrasupport.ams.test;

import in.infrasupport.hr.ams.util.DateUtil;

public class TestDateUtil {

	public static void main(String[] args) {
		String strDt = "12-11-2016 06:14:14 p.m";
		//DateUtil.strToSQLDateTime(strDt);
		
		String mod = strDt.replace(".", "");
		
		System.out.println(mod);
		
		DateUtil.strToSQLDateTime(mod);

	}

}
