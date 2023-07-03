package in.infrasupport.hr.ams.util;

import org.apache.commons.lang.RandomStringUtils;

public class PasswordUtil {
	
	public static void main(String[] args) {
		
		String text = RandomStringUtils.randomAlphanumeric(8);		
		
		System.out.println(text);
	}

}
