package in.infrasupport.hr.ams.dao;

import java.util.List;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Project;

public interface ProjectDAO {

	public boolean create(List<Project> plist) throws AMSException;
	public boolean create(Project project) throws AMSException;

	public Project get(String name);
	
	public Project getProjectByName(String pname);
	public Project getProjectByPID(String pId);
	boolean updateProject(Project p) throws AMSException;
	public List<String> getProjectNameList(String query);
	public List<String> getProjectIdList(String query);
	
}
