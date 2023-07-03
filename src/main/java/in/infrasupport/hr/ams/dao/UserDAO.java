package in.infrasupport.hr.ams.dao;

import java.util.List;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.User;

public interface UserDAO {

	public User authenticate(String empId);
	public List<User> findByName(String name);
	
	public boolean create(User user) throws AMSException;
	
	List<User> findAllUsers();
	
	User getUser(String empId); // used for editing
	
	User findUserById(String empId);
	public boolean updateUser(User user) throws AMSException;
	public List<String> getEmpIdList(String query);
	
	public boolean uploadUserData(List<User> ulist) throws AMSException;
	public List<User> findAllUsersForPasswordGen();
	public boolean updateUser(List<User> userList);
	
	public String getEmailId(String empId);
	public boolean updatePassword(User user) throws AMSException;
	public boolean inActivate(String empId);
	
}
