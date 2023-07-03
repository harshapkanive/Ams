package in.infrasupport.hr.ams.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.infrasupport.hr.ams.dao.AttendanceDAO;
import in.infrasupport.hr.ams.dao.ProjectAssignmentDAO;
import in.infrasupport.hr.ams.dao.ProjectStatusDAO;
import in.infrasupport.hr.ams.dao.UserDAO;
import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.User;

@Service("userService")
public class UserService  {

	final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDao;

	@Autowired
	private AttendanceDAO attendanceDAO; 
	
	@Autowired
	private ProjectAssignmentDAO projectAssignmentDAO;
	
	@Autowired
	private ProjectStatusDAO projectStatusDAO; 
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	MailerService mailerService; 	


	//get user for authentication - all fields are not selected
	public User findUserforAuth(String empId) {
		User user = userDao.authenticate(empId);
		return user;
	}

	// Get user for editing
	public User getUser(String empId)
	{
		return userDao.getUser(empId);
	}

	public boolean createUser(User user) throws AMSException {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.create(user);
	}
	
	
	public boolean updateUser(User user) throws AMSException {
		return userDao.updateUser(user);
	}

	public boolean updateUser(List<User> userList) throws AMSException {
		return userDao.updateUser(userList);
	}

	public List<User> findAllUsersForPasswordGen() {
		return userDao.findAllUsersForPasswordGen();
	}


	public User authenticate(String empId, String pwd) {
		User user = userDao.authenticate(empId);
		
		if(user != null && passwordEncoder.matches(pwd, user.getPassword()))
		{
			logger.info("******AUTHENTICATED****");
			user.setPassword("");//blank out the password
			user.setEmpId(empId);
			user.setPaList(projectAssignmentDAO.getAssignmentList(empId));
			logger.debug(user.toString());
			return user;
		}
		else
		{
			logger.error("<<<<AUTH FAILED>>>> for User Id :" + empId);
			return null;
		}
	}

	public Set<User> searchUser(String empId, String name)
	{
		Set<User> userSet = new HashSet<>();
		if(name != null && (name.trim().length() > 0 ))
		{
			userSet.addAll(userDao.findByName(name));
		}
		if(empId != null && (empId.trim().length() > 0))
		{
			User user = userDao.findUserById(empId); //check null since set allows null
			if (user != null) userSet.add(user);
		}
		logger.info("User Service Search-->" + userSet.size());
		return userSet;
	}
	
	public List<AttendanceEntry> getAttendance(String empId)
	{
		return attendanceDAO.getAttendance(empId);
	}

	public boolean syncAttendance(User puser) throws AMSException {
		
		User dbUser = findUserforAuth(puser.getEmpId());
		if(dbUser != null && passwordEncoder.matches(puser.getPassword(), dbUser.getPassword()))
		{
			logger.info("MARK ATT User Authenticated Success, calling DAO Save");
			// For Bug fix with empty firstname coming from mobile APP
			// Temporary hack for empty firstname bug
			return attendanceDAO.create(puser.getAttendEntries(),dbUser.getFirstName());
		}
		else
		{
			throw new AMSException("Invalid User Credentials");
		}
		
	}

	public List<String> getEmpIdList(String query) {
		return userDao.getEmpIdList(query);
	}
	
	public boolean resetUserPassword(String empId) throws AMSException
	{
		User user = new User();
		user.setEmpId(empId);
		user.setPlainTextPwd(RandomStringUtils.randomAlphanumeric(8)); // will be sent by Email
		user.setPassword(passwordEncoder.encode(user.getPlainTextPwd())); //encoded will be saved to DB
		user.setEmail(userDao.getEmailId(empId));
		
		if(userDao.updatePassword(user))
		{
			mailerService.sendEmail(user);
		}
		
		return true;
		
	}
	
	public boolean syncProjectStatus(User puser) throws AMSException
	{
		User dbUser = findUserforAuth(puser.getEmpId());
		if(dbUser != null && passwordEncoder.matches(puser.getPassword(), dbUser.getPassword()))
		{
			logger.info("MARK ATT User Authenticated Success, calling DAO Save");
			return projectStatusDAO.create(puser.getPsList());
		}
		else
		{
			throw new AMSException("Invalid User Credentials");
		}
	}

	public boolean inActivateUser(String empId) {
		return userDao.inActivate(empId);
	}
	
}
