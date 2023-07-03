package in.infrasupport.hr.ams.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.infrasupport.hr.ams.dao.ProjectAssignmentDAO;
import in.infrasupport.hr.ams.dao.ProjectDAO;
import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Project;
import in.infrasupport.hr.ams.model.ProjectAssignment;

@Service("projectService")
public class ProjectService {

	final static Logger logger = LoggerFactory.getLogger(ProjectService.class);
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private ProjectAssignmentDAO projectAssignmentDAO;
	
	public boolean createProject(Project project) throws AMSException
	{
		return projectDAO.create(project);
	}
	
	public Project getProject(String pId)
	{
		return projectDAO.getProjectByPID(pId);
	}
	
	public Project getProjectByName(String pname) 
	{
		return projectDAO.getProjectByName(pname);
	}
	
	public boolean updateProject(Project p) throws AMSException
	{
		return projectDAO.updateProject(p);
	}

	public List<String> getProjectIdList(String query) {
		return projectDAO.getProjectIdList(query);
	}
	
	public List<String> getProjectNameList(String query) {
		return projectDAO.getProjectNameList(query);
	}
	
	public boolean createProjectAssignment(ProjectAssignment pa) throws AMSException
	{
		return projectAssignmentDAO.assign(pa);
	}

	public List<ProjectAssignment> getProjectAssmtList(String empId)
	{
		return projectAssignmentDAO.getAssignmentList(empId);
	}

	public ProjectAssignment getAssignmentById(String assignId) {
		return projectAssignmentDAO.getAssignmentById(assignId);
	}

	public boolean updateProjectAssignment(ProjectAssignment projectAssignment) throws AMSException {
		return projectAssignmentDAO.update(projectAssignment);
	}

	public boolean deleteProjectAssmt(String assignId) {
		return projectAssignmentDAO.delete(assignId);
	}

}
