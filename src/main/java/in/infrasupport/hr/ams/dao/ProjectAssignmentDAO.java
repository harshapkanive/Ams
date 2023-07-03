package in.infrasupport.hr.ams.dao;

import java.util.List;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.ProjectAssignment;
import in.infrasupport.hr.ams.model.User;

public interface ProjectAssignmentDAO {

	//TO DO Depricate
	public ProjectAssignment get(String empId);
	public List<ProjectAssignment> getAssignmentList(String empId);
	
	public boolean assign(ProjectAssignment pa) throws AMSException;
	
	public boolean uploadProjectAssignment(List<User> ulist) throws AMSException;
	public ProjectAssignment getAssignmentById(String assignId);
	public boolean update(ProjectAssignment projectAssignment) throws AMSException;
	public boolean delete(String assignId);
	
}
