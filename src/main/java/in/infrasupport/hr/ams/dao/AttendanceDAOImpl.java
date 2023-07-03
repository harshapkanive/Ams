package in.infrasupport.hr.ams.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.ProjectAssignment;
import in.infrasupport.hr.ams.model.ReportCriteria;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.util.DateUtil;

@Repository("attendanceDAO")
public class AttendanceDAOImpl implements AttendanceDAO{

	private static final Logger logger = LoggerFactory.getLogger(AttendanceDAOImpl.class);
	private static final String INSERT_ATT_SQL = "insert into ATTENDANCE_ENTRY(empId,firstName,pid,projectname,location_address,LAT,LON,ATT_DATE,ATT_TSP) values (?,?,?,?,?,?,?,?,?)";
	
	private static final String GET_ATT_FOR_EMP_SQL = "select id,empid,firstname,pid,projectname,location_address,lat,lon,att_date,att_tsp from ATTENDANCE_ENTRY where empid=?";
	//private static final String GET_ALL_ATT_SQL = "select id,empid,firstname,pid,projectname,location_address,lat,lon,att_date,att_tsp from ATTENDANCE_ENTRY";
	private static final String GET_IMG_DATA_SQL = "select img from ATTENDANCE_ENTRY where id=?";
	
	private static final String GET_ATT_FOR_MONTH = "select A.empid,A.firstName,A.pid,A.projectname,A.att_date,ifnull(A.location_address, concat(A.lat,',',A.lon)) as location, TIMESTAMPDIFF(MINUTE,A.ATT_TSP,B.ATT_TSP)/60 as totaltime from attendance_entry A,attendance_entry B where A.pid = B.pid AND A.att_date = B.att_date AND A.empid = B.empid AND A.empid = ? AND MONTH(A.att_date) = MONTH(CURDATE()) having totaltime > 0";

	private static final String GET_DAILY_ATTEND = "select A.empid,A.firstName,A.pid,A.projectname,A.att_date,ifnull(A.location_address, concat(A.lat,',',A.lon)) as location, TIMESTAMPDIFF(MINUTE,A.ATT_TSP,B.ATT_TSP)/60 as totaltime from attendance_entry A,attendance_entry B where A.pid = B.pid AND A.att_date = B.att_date AND A.empid = B.empid AND A.att_date = ? having totaltime > 0";
	
	private static final String GET_ATT_DUMP = "select empid,firstname,pid,projectname, ifnull(location_address, concat(lat,',',lon)) as location,ATT_DATE, ATT_TSP from attendance_entry where att_date = ?";
	
	private static final String GET_ATT_DEFAULTERS = "select u.empid,u.firstname,p.pid,p.projectname from user u, project p, project_assignment pa where u.empid = pa.empid and pa.pid = p.pid and u.empid not in ( select distinct empid from attendance_entry where att_date >= ? and att_date <= ? ) ";

	
	@Autowired
	DataSource dataSource;
	
	@Override
	public boolean create(User user) throws AMSException {
		
		int row=0;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(INSERT_ATT_SQL);
			ps.setString(1, user.getEmpId());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getAe().getProjectName());
			ps.setString(4, user.getAe().getLat());
			ps.setString(5, user.getAe().getLon());
			ps.setDate(6, DateUtil.strToSQLDate(user.getAe().getDate()));
			ps.setTimestamp(7, DateUtil.strToSQLDateTime(user.getAe().getTimestamp()));
			
			row = ps.executeUpdate();
			logger.debug("Attendance marked success:" + user);
		} catch (SQLException e) {
			logger.error("DB Error while inserting attendance:"+e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while marking attendance:" + e.getLocalizedMessage());
		}
		
		return row > 0;
	}

	@Override
	public List<AttendanceEntry> getAttendance(String empId) {
		List<AttendanceEntry> attEntries = new ArrayList<>();
		AttendanceEntry ae = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_ATT_FOR_EMP_SQL);
			ps.setString(1, empId);
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setId(rs.getInt(1));
				ae.setEmpId(rs.getString(2));
				ae.setFirstName(rs.getString(3));
				ae.setpId(rs.getString(4));
				ae.setProjectName(rs.getString(5));
				ae.setAddressLocation(rs.getString(6));
				ae.setLat(rs.getString(7));
				ae.setLon(rs.getString(8));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(9)));
				ae.setTimestamp(DateUtil.sqlTSPToString(rs.getTimestamp(10)));
				
				attEntries.add(ae);
			}
		
			logger.info("DB : Retrieving Att Entry for " + empId );
		} catch (SQLException e) {
			logger.error("DB Error while getting attendance for " + empId + ":" + e.getLocalizedMessage(), e);
		}
		return attEntries;
	}

	@Override
	public byte[] getAttendanceImage(int attId) {
		
		byte[] rawdata = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_IMG_DATA_SQL);
			ps.setInt(1, attId);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				InputStream is = rs.getBinaryStream(1);
				rawdata = IOUtils.toByteArray(is);
			}
			
		} catch (SQLException e) {
			logger.error("Error while getting image for Id:" + attId + " -->" + e.getLocalizedMessage() , e);
		} catch (IOException e) {
			logger.error("Error while converting to byte stream:"+e.getLocalizedMessage(), e);
		}
		
		return rawdata;
	}

/*	@Override
	public List<AttendanceEntry> getAllAttendance() {
		List<AttendanceEntry> attEntries = new ArrayList<>();
		AttendanceEntry ae = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_ALL_ATT_SQL);
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setId(rs.getInt(1));
				ae.setEmpId(rs.getString(2));
				ae.setFirstName(rs.getString(3));
				ae.setpId(rs.getString(4));
				ae.setProjectName(rs.getString(5));
				ae.setAddressLocation(rs.getString(6));
				ae.setLat(rs.getString(7));
				ae.setLon(rs.getString(8));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(9)));
				ae.setTimestamp(DateUtil.sqlTSPToString(rs.getTimestamp(10)));

				attEntries.add(ae);
			}
		} catch (SQLException e) {
			logger.error("Error while getting Attendance Entries" + e.getLocalizedMessage() , e);
		}
		logger.info("Att Entry List size " + attEntries.size());
		return attEntries;		
	}*/

	@Override
	public List<AttendanceEntry> getAttendance(ReportCriteria reportCriteria) {
		List<AttendanceEntry> attList = new ArrayList<>();
		AttendanceEntry ae = null;
		//String query = "select A.empid,A.firstName,A.pid,A.projectname,A.att_date,ifnull(A.location_address, concat(A.lat,',',A.lon)) as location, TIMESTAMPDIFF(MINUTE,A.ATT_TSP,B.ATT_TSP)/60 as totaltime from attendance_entry A,attendance_entry B where A.pid = B.pid AND A.att_date = B.att_date AND A.empid = B.empid AND A.empid = ? ";
		String query = "select A.empid,A.firstName,A.pid,A.projectname,A.att_date,ifnull(A.location_address, concat(A.lat,',',A.lon)) as location, TIMESTAMPDIFF(MINUTE,A.ATT_TSP,B.ATT_TSP)/60 as totaltime from attendance_entry A,attendance_entry B where A.pid = B.pid AND A.att_date = B.att_date AND A.empid = B.empid ";		

		if(reportCriteria.getEmpId().length() != 0)
		{
			query = query + " AND A.empid = ?";
		}
		
		if(!("0".equals(reportCriteria.getStartDate())))
		{
			//query = query + " and str_to_date(att_date,'%d-%m-%Y') >= str_to_date('" + reportCriteria.getStartDate() + "','%d-%m-%Y') ";
			query = query + " and A.att_date >= " + DateUtil.strToSQLDate(reportCriteria.getStartDate());
		}
		if(!("0".equals(reportCriteria.getEndDate())))
		{
			query = query + " OR A.att_date <= " + DateUtil.strToSQLDate(reportCriteria.getEndDate());
		}
		
		query = query + " having totaltime > 0";
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(query);
			if(reportCriteria.getEmpId().length() != 0){
				ps.setString(1, reportCriteria.getEmpId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setEmpId(rs.getString(1));
				ae.setFirstName(rs.getString(2));
				ae.setpId(rs.getString(3));
				ae.setProjectName(rs.getString(4));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(5)));
				ae.setAddressLocation(rs.getString(6));
				ae.setTotalTime(rs.getFloat(7));

				attList.add(ae);
			}
			
		} catch (SQLException e) {
			logger.error("Error while getting Attendance Entries for " + reportCriteria.toString(), e);
		}
		
		return attList;
	}

	@Override
	public List<AttendanceEntry> getAttendanceForMonth(String empId) {
		List<AttendanceEntry> attList = new ArrayList<>();
		AttendanceEntry ae = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_ATT_FOR_MONTH);
			ps.setString(1, empId);
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setEmpId(rs.getString(1));
				ae.setFirstName(rs.getString(2));
				ae.setpId(rs.getString(3));
				ae.setProjectName(rs.getString(4));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(5)));
				ae.setAddressLocation(rs.getString(6));
				ae.setTotalTime(rs.getFloat(7));

				attList.add(ae);
			}
			
		} catch (SQLException e) {
			logger.error("Error while getting Monthly Attendance Entries for " + empId, e);
		}
		return attList;
	}

	@Override
	public boolean create(List<AttendanceEntry> attendEntries, String firstName) throws AMSException {
		int[] counts = null;
		boolean success = false;
		try(Connection conn = dataSource.getConnection();) 
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(INSERT_ATT_SQL);
			
			for(AttendanceEntry ae : attendEntries)
			{
				ps.setString(1, ae.getEmpId());
				//ps.setString(2, ae.getFirstName());
				ps.setString(2, firstName); // hack for first name empty bug
				ps.setString(3, ae.getpId());
				ps.setString(4, ae.getProjectName());
				ps.setString(5, ae.getAddressLocation());
				ps.setString(6, ae.getLat());
				ps.setString(7, ae.getLon());
				//ps.setString(8, ae.getDate());
				//ps.setString(9, ae.getTimestamp());
				ps.setDate(8, DateUtil.strToSQLDate(ae.getDate()));
				ps.setTimestamp(9, DateUtil.strToSQLDateTime(ae.getTimestamp()));
				ps.addBatch();
			}
			
			try
			{
				counts = ps.executeBatch();
			}
			catch(BatchUpdateException be)
			{
				conn.rollback();
				logger.error("DB Error during Attendance Data Sync " + be.getLocalizedMessage(), be);
				throw new AMSException("DB Error Attendance Sync:"+ be.getLocalizedMessage());				
			}			
			
			logger.info("Attendance Records processed -->" + counts.length + ":");
			conn.commit();
			success = true;
			
		} catch (SQLException e) {
			logger.error("DB Error during Attendance Data Sync  " + e.getLocalizedMessage(), e);
			throw new AMSException("DB Error Attendance Data Sync :"+e.getLocalizedMessage());
		}
		
		return success;
		
	}

	@Override
	public List<AttendanceEntry> getDailyAttendance(String reportDate) {
		List<AttendanceEntry> aeList = new ArrayList<>();
		AttendanceEntry ae = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_DAILY_ATTEND);
			ps.setDate(1, DateUtil.strToSQLDate(reportDate));
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setEmpId(rs.getString(1));
				ae.setFirstName(rs.getString(2));
				ae.setpId(rs.getString(3));
				ae.setProjectName(rs.getString(4));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(5)));
				ae.setAddressLocation(rs.getString(6));
				ae.setTotalTime(rs.getFloat(7));
				aeList.add(ae);
			}
				
		} catch (SQLException e) {
			logger.error("DB Error while getting Daily Attendance:"+e.getLocalizedMessage(), e);
		}
		
		return aeList;
	}

	@Override
	public List<AttendanceEntry> getAttDump(String dumpDate) {
		List<AttendanceEntry> aeList = new ArrayList<>();
		AttendanceEntry ae = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_ATT_DUMP);
			ps.setDate(1, DateUtil.strToSQLDate(dumpDate));
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				ae = new AttendanceEntry();
				ae.setEmpId(rs.getString(1));
				ae.setFirstName(rs.getString(2));
				ae.setpId(rs.getString(3));
				ae.setProjectName(rs.getString(4));
				ae.setAddressLocation(rs.getString(5));
				ae.setDate(DateUtil.sqlDateToString(rs.getDate(6)));
				ae.setTimestamp(DateUtil.sqlTSPToString(rs.getTimestamp(7)));
				aeList.add(ae);
			}			
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Attendance Dump:"+e.getLocalizedMessage(), e);
		}
		return aeList;
	}

	@Override
	public List<User> getattDefaulters(ReportCriteria reportCriteria) {
		
		List<User> defaulterList = new ArrayList<>();
		User u = null;
		ProjectAssignment pa = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_ATT_DEFAULTERS);
			ps.setDate(1, DateUtil.strToSQLDate(reportCriteria.getStartDate()));
			ps.setDate(2, DateUtil.strToSQLDate(reportCriteria.getEndDate()));
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				u = new User();
				u.setEmpId(rs.getString(1));
				u.setFirstName(rs.getString(2));
				pa = new ProjectAssignment();
				pa.setpId(rs.getString(3));
				pa.setProjectName(rs.getString(4));
				u.setPa(pa);
				defaulterList.add(u);
			}			
		}
		catch (SQLException e) {
			logger.error("DB Error while getting Attendance Defaulters:"+e.getLocalizedMessage(), e);
		}
		
		return defaulterList;
	}

}
