package in.infrasupport.hr.ams.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	/**
     * @param args
     */
    public static void main(String[] args) {
            String password = "admin123";
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            System.out.println(passwordEncoder.encode(password));
    }	
	
}
