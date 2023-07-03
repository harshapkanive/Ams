package in.infrasupport.hr.ams.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.infrasupport.hr.ams.dao.AttendanceDAO;
import in.infrasupport.hr.ams.dao.ProjectStatusDAO;
import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.ProjectStatus;
import in.infrasupport.hr.ams.model.ReportCriteria;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.util.PDFUtil;

@Service("reportService")
public class ReportService {

	final static Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	AttendanceDAO attendanceDAO;
	
	@Autowired
	ProjectStatusDAO projectStatusDAO;
	
/*	public List<AttendanceEntry> getAllAttendance()
	{
		return attendanceDAO.getAllAttendance();
	}*/

	public List<AttendanceEntry> getAttendance(ReportCriteria reportCriteria) {
		if(reportCriteria.getStartDate().equals("0") && reportCriteria.getEndDate().equals("0"))
		{
			return attendanceDAO.getAttendanceForMonth(reportCriteria.getEmpId());
		}
		return attendanceDAO.getAttendance(reportCriteria);
	}

	public List<ProjectStatus> getProjectStatus(ReportCriteria reportCriteria) {
		if(reportCriteria.getStartDate().equals("0") && reportCriteria.getEndDate().equals("0"))
		{
			return projectStatusDAO.getProjectStatusForMonth(reportCriteria.getpId());
		}
		return projectStatusDAO.getProjectStatus(reportCriteria);
	}

	public List<AttendanceEntry> getDailyAttendance(String reportDate) {
		return attendanceDAO.getDailyAttendance(reportDate);
	}

	public ByteArrayOutputStream getProjectStatusForPDF(ReportCriteria reportCriteria) {
		
		List<ProjectStatus> psList = null;
		
		if(reportCriteria.getStartDate().equals("0") && reportCriteria.getEndDate().equals("0"))
		{
			psList = projectStatusDAO.getProjectStatusForMonthPDF(reportCriteria.getpId());
		}
		else
		{
			psList = projectStatusDAO.getProjectStatusPDF(reportCriteria);	
		}
		
		return PDFUtil.generatePSPDF(psList);
		
	}

	public List<AttendanceEntry> getAttDump(String dumpDate) {
		return attendanceDAO.getAttDump(dumpDate);
	}

	public List<User> getAttDefaulters(ReportCriteria reportCriteria) {
		return attendanceDAO.getattDefaulters(reportCriteria);
	}
	
}
