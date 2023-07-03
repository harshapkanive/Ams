package in.infrasupport.hr.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.infrasupport.hr.ams.dao.ProjectAssignmentDAO;
import in.infrasupport.hr.ams.dao.ProjectDAO;
import in.infrasupport.hr.ams.dao.UserDAO;
import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Project;
import in.infrasupport.hr.ams.model.User;

@Service
public class UploadService {

	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	ProjectAssignmentDAO projectAssignmentDAO;
	
	public boolean uploadProjectData(List<Project> plist) throws AMSException
	{
		return projectDAO.create(plist);
	}
	
	public boolean uploadUserData(List<User> ulist) throws AMSException
	{
		boolean userUploadstatus = userDao.uploadUserData(ulist);
		boolean userProjectAssignStatus = projectAssignmentDAO.uploadProjectAssignment(ulist);
		return userUploadstatus && userProjectAssignStatus;
	}
	
}
