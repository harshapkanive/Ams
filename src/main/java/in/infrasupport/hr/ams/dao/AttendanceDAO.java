package in.infrasupport.hr.ams.dao;

import java.util.List;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.ReportCriteria;
import in.infrasupport.hr.ams.model.User;

public interface AttendanceDAO {

	public boolean create(User user) throws AMSException;
	
	public List<AttendanceEntry> getAttendance(String empId);
	
	//public List<AttendanceEntry> getAllAttendance();

	public byte[] getAttendanceImage(int attId);

	public List<AttendanceEntry> getAttendance(ReportCriteria reportCriteria);

	public List<AttendanceEntry> getAttendanceForMonth(String empId);

	public boolean create(List<AttendanceEntry> attendEntries, String firstName) throws AMSException;

	public List<AttendanceEntry> getDailyAttendance(String reportDate);

	public List<AttendanceEntry> getAttDump(String dumpDate);

	public List<User> getattDefaulters(ReportCriteria reportCriteria);
}
