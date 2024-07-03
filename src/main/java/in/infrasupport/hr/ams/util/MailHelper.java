package in.infrasupport.hr.ams.util;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.User;

@Component
public class MailHelper {

	private static final Logger logger = LoggerFactory.getLogger(MailHelper.class);

	@Autowired
	Environment environment;
	
	private Properties props;
	final String username = "infrasupportindia@gmail.com";
	final String password = "Infra$1234";	
	
	@PostConstruct
	public void init()
	{
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", environment.getRequiredProperty("mail.smtp.host"));
		props.put("mail.smtp.port", environment.getRequiredProperty("mail.smtp.port"));
		
	}
	
	public boolean sendBulkPasswordMail(List<User> userList, String subject, String messageBody) throws AMSException 
	{

		int userListSize = userList.size();
		int count = 0;
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		Transport t = null;
		try {
				t = session.getTransport();
				t.connect();
				for(User user : userList)
				{
					try
					{
						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress("do-not-reply@infrasupport.in"));
						message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(user.getEmail()));
						message.setSubject(subject);
						message.setText(messageBody + " " + user.getPlainTextPwd() + AMSConstants.MAIL_FOOTER);
						message.saveChanges();
						t.sendMessage(message, message.getAllRecipients());
						logger.info("Mail Sent Successfully to " + user.getEmpId());
						count++;
					}catch (Exception e) {
						logger.error("Error while sending Email to *** " + user.getEmail());
						logger.error(e.getLocalizedMessage());
					}
				}
			
		} catch (MessagingException e) {
			logger.error("Error while sending email:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error while sending email:"+e.getLocalizedMessage());
		}
		finally
		{
			if(t != null){
				try {
					t.close();
				} catch (MessagingException e) {
					logger.error("Error while closing Mail Transport:"+e.getLocalizedMessage(), e);
				}
			}
		}
		logger.info("Total Records to Send Email to -->" + userListSize);
		logger.info("Total Emails successfully sent-->" + count);
		return count == userListSize;
	}
	
	
	public boolean sendMail(String toAddresses, String subject, String messageBody) throws AMSException 
	{

		boolean sent = false;
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@infrasupport.in"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toAddresses));
			message.setSubject(subject);
			message.setText(messageBody);

			//Transport.send(message);
			Transport t = session.getTransport();
			t.connect();
			message.saveChanges();
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			
			logger.info("Mail Sent Successfully");
			sent = true;
		} catch (MessagingException e) {
			logger.error("Error while sending email:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error while sending email:"+e.getLocalizedMessage());
		}
		
		return sent;
	}	
	
	
/*	
public static void main(String[] args) {
		//MailHelper.sendMail("navadiganta.tumkur@gmail.com","new42day","smtp.gmail.com","465","true","true",true,"javax.net.ssl.SSLSocketFactory","false","sadanand.rud@gmail.com","Welcome to Infra","Welcome to Infra Support Engg");
		MailHelper.sendMail("navadiganta.tumkur@gmail.com","new42day","smtp.gmail.com","587","true","true",true,"javax.net.ssl.SSLSocketFactory","false",new String[]{"sadanand.rud@gmail.com"},"Welcome to Infra","Welcome to Infra Support Engg");
	}
	
	public synchronized static boolean sendMail(String userName,
			String passWord,String host,String port,String starttls,
			String auth,boolean debug,String socketFactoryClass,
			String fallback,String[] to,String subject,String text)
	{
		logger.info("************************************************************************");
		logger.info("To:" + to[0]);
		logger.info("Subject:" + subject);
		logger.info("Body" + text);
		logger.info("************************************************************************");

		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);
		if(!"".equals(port))
			props.put("mail.smtp.port", port);
		if(!"".equals(starttls))
			props.put("mail.smtp.starttls.enable",starttls);
		props.put("mail.smtp.auth", auth);
		if(debug){
			props.put("mail.smtp.debug", "true");
		}
		else{
			props.put("mail.smtp.debug", "false"); 
		}
		if(!"".equals(port))
				props.put("mail.smtp.socketFactory.port", port);
				if(!"".equals(socketFactoryClass))
				props.put("mail.smtp.socketFactory.class",socketFactoryClass);
				if(!"".equals(fallback))
				props.put("mail.smtp.socketFactory.fallback", fallback);
		try{
			Session session = Session.getDefaultInstance(props);

			session.setDebug(debug);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(text);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress("navadiganta.tumkur@gmail.com"));
			for(int i=0;i<to.length;i++){
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			}
						 for(int i=0;i<cc.length;i++){
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
					}
					for(int i=0;i<bcc.length;i++){
					msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
					}
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}	*/

}
