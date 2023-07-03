package in.infrasupport.hr.ams.dao;

import java.util.List;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.ProjectStatus;
import in.infrasupport.hr.ams.model.ReportCriteria;

public interface ProjectStatusDAO {

	public boolean create(List<ProjectStatus> psList) throws AMSException;

	public List<ProjectStatus> getProjectStatus(ReportCriteria reportCriteria);

	byte[] getProjectStatImg(int id);
	
	public List<ProjectStatus> getProjectStatusForPDF(ReportCriteria reportCriteria);

	public List<ProjectStatus> getProjectStatusForMonth(String getpId);

	public List<ProjectStatus> getProjectStatusForMonthPDF(String getpId);

	public List<ProjectStatus> getProjectStatusPDF(ReportCriteria reportCriteria);
	
}
