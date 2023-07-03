package in.infrasupport.ams.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.Result;
import in.infrasupport.hr.ams.model.User;

public class RestClient {
	
	private static final String REST_SERVICE_URL="http://localhost:8080/ams";

	public static void main(String[] args) {
		testLogin();
		//testAttendanceUpload();  
	}
	
/*	private static void testLogin()
	{
		System.out.println("Test login");
		RestTemplate restTemplate = new RestTemplate();
		User user = new User("INFEMPADMIN","sada123a");
		User result = restTemplate.getForObject(REST_SERVICE_URL+"/rest/login.json?empId=" + user.getEmpId() + "&password=" + user.getPassword(),User.class);
		System.out.println(result.getEmpId() + ">>" + result.getPassword());
	}*/	
	
	private static void testAttendanceUpload()
	{
        RestTemplate restTemplate = new RestTemplate();
        byte[] imgdata = null;
        try(InputStream in = new ClassPathResource("maize.jpg").getInputStream();) 
        {
        	imgdata = IOUtils.toByteArray(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        User user = new User("INFSUPPADMIN", "");
        user.setFirstName("Ams Admin");
        //AttendanceEntry ae = new AttendanceEntry("National Highway Authority","11.000000", "77.333455", "10-14-2016","10-10-2016 10:10:10",imgdata);
        AttendanceEntry ae = new AttendanceEntry();
        user.setAe(ae);
        HttpEntity<User> request = new HttpEntity<User>(user);
        Result result = null;
        try
        {
            result = restTemplate.postForObject(REST_SERVICE_URL+"/rest/markatt",request,Result.class);
            System.out.println("Attendance Posted -->" + result.getMessage());
        }
        catch(RestClientException rce)
        {
            rce.printStackTrace();
        }
        
	}
	
	
	private static void testLogin()
	{
		System.out.println("Test login");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<User> request = new HttpEntity<User>(new User("admin","admin123")); 

		User result = restTemplate.postForObject(REST_SERVICE_URL+"/rest/login",request,User.class);
		System.out.println(result.toString());
	}
	
}
