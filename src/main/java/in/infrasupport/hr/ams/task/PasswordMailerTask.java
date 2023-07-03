package in.infrasupport.hr.ams.task;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.UserService;
import in.infrasupport.hr.ams.util.AMSConstants;
import in.infrasupport.hr.ams.util.MailHelper;

@Component
@Scope("prototype")
public class PasswordMailerTask implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(PasswordMailerTask.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;	
	
	@Autowired
	MailHelper mailHelper;
	
	@Override
	public void run() {
		logger.info("Password Mailer Task Starting-->");
		List<User> userList = userService.findAllUsersForPasswordGen();
		
		for(User u : userList)
		{
			u.setPlainTextPwd(RandomStringUtils.randomAlphanumeric(8)); // will be sent by Email
			u.setPassword(passwordEncoder.encode(u.getPlainTextPwd())); //encoded will be saved to DB
		}
		
		try {
			mailHelper.sendBulkPasswordMail(userList, "Infrasupport Attendance System User Information", AMSConstants.MSG_BODY_PWD_SEND);
			userService.updateUser(userList);
		} catch (AMSException e) {
			logger.error("Error while sending Bulk Password Mail:" + e.getLocalizedMessage(), e);
		}
		
		logger.info("Password Mailer Task Completed-->");
		
	}
	
	
}
