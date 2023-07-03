package in.infrasupport.hr.ams.dao;

import java.io.ByteArrayInputStream;
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
import in.infrasupport.hr.ams.model.ProjectStatus;
import in.infrasupport.hr.ams.model.ReportCriteria;
import in.infrasupport.hr.ams.util.DateUtil;

@Repository("projectStatusDAO")
public class ProjectStatusDAOImpl implements ProjectStatusDAO{

	private static final Logger logger = LoggerFactory.getLogger(ProjectStatusDAOImpl.class);
	
	private static final String INSERT_SQL = "insert into PROJECT_STATUS(empId,firstName,pid,projectname,status_desc,ps_date,ps_tsp,IMG) values(?,?,?,?,?,?,?,?)";
	
	private static final String GET_IMG_DATA_SQL = "select img from PROJECT_STATUS where id=?";
	
	private static final String GET_PS_DATA_FOR_PDF = "select empid,firstname,pid,projectname,status_desc,ps_date,ps_tsp,img from project_status"; 
	
	private static final String GET_PS_DATA_FOR_MONTH = "select id,empid,firstname,pid,projectname,status_desc,ps_date,ps_tsp from project_status where pid=? AND MONTH(ps_date) = MONTH(CURDATE())";

	private static final String GET_PS_DATA_FOR_MONTH_PDF = "select empid,firstname,pid,projectname,status_desc,ps_date,ps_tsp,img from project_status where pid=? AND MONTH(ps_date) = MONTH(CURDATE())";	
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public boolean create(List<ProjectStatus> psList) throws AMSException {

		boolean success = false;
		int[] counts = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
			
			for(ProjectStatus pst : psList)
			{
				ps.setString(1, pst.getEmpId());
				ps.setString(2, pst.getFirstName());
				ps.setString(3, pst.getpId());
				ps.setString(4, pst.getProjectName());
				ps.setString(5, pst.getStatusDesc());
				//ps.setString(6, pst.getDate());
				//ps.setString(7, pst.getTimeStamp());
				ps.setDate(6, DateUtil.strToSQLDate(pst.getDate()));
				ps.setTimestamp(7, DateUtil.strToSQLDateTime(pst.getTimeStamp()));
				ps.setBinaryStream(8, new ByteArrayInputStream(pst.getImgData()));
				ps.addBatch();
			}
			
			try
			{
				counts = ps.executeBatch();
			}
			catch(BatchUpdateException be)
			{
				conn.rollback();
				logger.error("DB Error during Project Status Report create " + be.getLocalizedMessage(), be);
				throw new AMSException("DB Error while Upload:"+ be.getLocalizedMessage());				
			}
			
			logger.info("Records processed -->" + counts.length + ":");
			
			conn.commit();
			success = true;			
			
		} catch (SQLException e) {
			logger.error("DB Error while inserting Project Status"+e.getLocalizedMessage(), e);
		}
		
		return success;
	}

	@Override
	public byte[] getProjectStatImg(int id)
	{
		byte[] rawdata = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_IMG_DATA_SQL);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				InputStream is = rs.getBinaryStream(1);
				rawdata = IOUtils.toByteArray(is);
			}
			
		} catch (SQLException e) {
			logger.error("Error while getting image for Id:" + id + " -->" + e.getLocalizedMessage() , e);
		} catch (IOException e) {
			logger.error("Error while converting to byte stream:"+e.getLocalizedMessage(), e);
		}
		
		return rawdata;
	}
	
	
	@Override
	public List<ProjectStatus> getProjectStatus(ReportCriteria reportCriteria) {
		List<ProjectStatus> psList = new ArrayList<>();
		ProjectStatus pst = null;
		
		String query = "select id,empid,firstname,pid,projectname,status_desc,ps_date,ps_tsp from project_status where pid=? ";
		
		if(!("0".equals(reportCriteria.getStartDate())))
		{
			//query = query + " and str_to_date(ps_date,'%d-%m-%Y') >= str_to_date('" + reportCriteria.getStartDate() + "','%d-%m-%Y') ";
			query = query + " and ps_date >= " + DateUtil.strToSQLDate(reportCriteria.getStartDate());
		}
		if(!("0".equals(reportCriteria.getEndDate())))
		{
			//query = query + " and str_to_date(ps_date,'%d-%m-%Y') <= str_to_date('" + reportCriteria.getEndDate() + "','%d-%m-%Y') ";
			query = query + " OR ps_date <= " + DateUtil.strToSQLDate(reportCriteria.getEndDate());
		}
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, reportCriteria.getpId());
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				pst = new ProjectStatus();
				pst.setId(rs.getInt(1));
				pst.setEmpId(rs.getString(2));
				pst.setFirstName(rs.getString(3));
				pst.setpId(rs.getString(4));
				pst.setProjectName(rs.getString(5));
				pst.setStatusDesc(rs.getString(6));
				pst.setDate(DateUtil.sqlDateToString(rs.getDate(7)));
				pst.setTimeStamp(DateUtil.sqlTSPToString(rs.getTimestamp(8)));
				//pst.setImgData(IOUtils.toByteArray(rs.getBinaryStream(9)));
				psList.add(pst);
				
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting image for Project Status: -->" + e.getLocalizedMessage() , e);
		} 
		
		return psList;
	}

	@Override
	public List<ProjectStatus> getProjectStatusForPDF(ReportCriteria reportCriteria) {
		List<ProjectStatus> psList = new ArrayList<>();
		ProjectStatus pst = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PS_DATA_FOR_PDF);
			ResultSet rs = ps.executeQuery();
			
			while(rs != null && rs.next())
			{
				pst = new ProjectStatus();
				pst.setEmpId(rs.getString(1));
				pst.setFirstName(rs.getString(2));
				pst.setpId(rs.getString(3));
				pst.setProjectName(rs.getString(4));
				pst.setStatusDesc(rs.getString(5));
				//pst.setDate(rs.getString(6));
				//pst.setTimeStamp(rs.getString(7));
				pst.setDate(DateUtil.sqlDateToString(rs.getDate(6)));
				pst.setTimeStamp(DateUtil.sqlTSPToString(rs.getTimestamp(7)));
				pst.setImgData(IOUtils.toByteArray(rs.getBinaryStream(8)));
				psList.add(pst);
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Proj Stat Data:"+e.getLocalizedMessage(), e);
		} catch (IOException e) {
			logger.error("IO Error while getting Proj Stat Image:"+e.getLocalizedMessage(), e);
		}
		
		return psList;
	}

	@Override
	public List<ProjectStatus> getProjectStatusForMonth(String pId) {
		// Image data is not populated for Web reports, record id is used to fetch image
		// Only for PDF Report Image Data is populated
		List<ProjectStatus> psList = new ArrayList<>();
		ProjectStatus pst = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PS_DATA_FOR_MONTH);
			ps.setString(1, pId);
			ResultSet rs = ps.executeQuery();
			
			while(rs != null && rs.next())
			{
				pst = new ProjectStatus();
				pst.setId(rs.getInt(1));
				pst.setEmpId(rs.getString(2));
				pst.setFirstName(rs.getString(3));
				pst.setpId(rs.getString(4));
				pst.setProjectName(rs.getString(5));
				pst.setStatusDesc(rs.getString(6));
				pst.setDate(DateUtil.sqlDateToString(rs.getDate(7)));
				pst.setTimeStamp(DateUtil.sqlTSPToString(rs.getTimestamp(8)));
				psList.add(pst);
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Proj Stat Data:"+e.getLocalizedMessage(), e);
		} 
		
		return psList;
	}

	@Override
	public List<ProjectStatus> getProjectStatusForMonthPDF(String pId) {
		// For PDF Generation Image data is populated
		
		List<ProjectStatus> psList = new ArrayList<>();
		ProjectStatus pst = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PS_DATA_FOR_MONTH_PDF);
			ps.setString(1, pId);
			ResultSet rs = ps.executeQuery();
			
			while(rs != null && rs.next())
			{
				pst = new ProjectStatus();
				pst.setEmpId(rs.getString(1));
				pst.setFirstName(rs.getString(2));
				pst.setpId(rs.getString(3));
				pst.setProjectName(rs.getString(4));
				pst.setStatusDesc(rs.getString(5));
				pst.setDate(DateUtil.sqlDateToString(rs.getDate(6)));
				pst.setTimeStamp(DateUtil.sqlTSPToString(rs.getTimestamp(7)));
				pst.setImgData(IOUtils.toByteArray(rs.getBinaryStream(8)));
				psList.add(pst);
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Proj Stat Data:"+e.getLocalizedMessage(), e);
		} catch (IOException e) {
			logger.error("IO Error while getting Proj Stat Image:"+e.getLocalizedMessage(), e);
		} 
		
		return psList;	
	}

	@Override
	public List<ProjectStatus> getProjectStatusPDF(ReportCriteria reportCriteria) {
		
		List<ProjectStatus> psList = new ArrayList<>();
		ProjectStatus pst = null;
		
		String query = "select empid,firstname,pid,projectname,status_desc,ps_date,ps_tsp,img from project_status where pid=? ";
		
		if(!("0".equals(reportCriteria.getStartDate())))
		{
			query = query + " and ps_date >= " + DateUtil.strToSQLDate(reportCriteria.getStartDate());
		}
		if(!("0".equals(reportCriteria.getEndDate())))
		{
			query = query + " OR ps_date <= " + DateUtil.strToSQLDate(reportCriteria.getEndDate());
		}
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, reportCriteria.getpId());
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				pst = new ProjectStatus();
				pst.setEmpId(rs.getString(1));
				pst.setFirstName(rs.getString(2));
				pst.setpId(rs.getString(3));
				pst.setProjectName(rs.getString(4));
				pst.setStatusDesc(rs.getString(5));
				pst.setDate(DateUtil.sqlDateToString(rs.getDate(6)));
				pst.setTimeStamp(DateUtil.sqlTSPToString(rs.getTimestamp(7)));
				pst.setImgData(IOUtils.toByteArray(rs.getBinaryStream(8)));
				psList.add(pst);
				
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting image for Project Status: -->" + e.getLocalizedMessage() , e);
		} catch (IOException e) {
			logger.error("IO Error while getting Proj Stat Image:"+e.getLocalizedMessage(), e);
		} 
		
		return psList;		
		
	}

}
