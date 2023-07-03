package in.infrasupport.hr.ams.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.task.PasswordMailerTask;
import in.infrasupport.hr.ams.util.AMSConstants;
import in.infrasupport.hr.ams.util.MailHelper;

@Service
public class MailerService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailerService.class);
	
	@Autowired
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Autowired
	PasswordMailerTask passwordMailerTask;
	
	@Autowired
	MailHelper mailHelper;	
	
	public void sendBulkPwdMail()
	{
		logger.info("Mailer Service Bulk Pwd Mail Invoking Task Executor");
		threadPoolTaskExecutor.execute(passwordMailerTask);
		logger.info("Mailer Service Returning after Aysnc Task Execution<----");
		
	}

	public void sendEmail(User user) throws AMSException {
		
		logger.info("Mailer Service Send Email");
		mailHelper.sendMail(user.getEmail(), AMSConstants.PWD_RESET_EMAIL_SUBJ, 
				AMSConstants.PASSWORD_RESET_EMAIL_BODY + " " + user.getPlainTextPwd());
		
	}

}
