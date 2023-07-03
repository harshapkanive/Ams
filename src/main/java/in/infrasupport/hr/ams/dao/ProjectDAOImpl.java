package in.infrasupport.hr.ams.dao;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Project;

@Repository("projectDAO")
public class ProjectDAOImpl implements ProjectDAO{

	private static final Logger logger = LoggerFactory.getLogger(ProjectDAOImpl.class);

	private static final String INSERT_PROJECT_SQL="insert into project(pid,projectname, description,location,startdate) values(?,?,?,?,?)";
	private static final String GET_PROJECT_SQL= "select name,description from project where name like ? and isactive='Y'";
	private static final String GET_PROJ_BY_NAME_SQL = "select pid from project where projectname=? and isactive='Y'";
	private static final String GET_PROJECT_BY_PID_SQL = "select projectname, description,location,startDate from project where pid=? and isactive='Y'";
	private static final String UPDATE_PROJECT_SQL = "update project set projectname=?,description=?,location=?,startdate=? where pid=?";
	private static final String GET_PROJECT_NAME_LIST_SQL = "select projectname from project where projectname like ? and isactive='Y'";
	private static final String GET_PROJECT_ID_LIST_SQL = "select pid from project where pid like ? and isactive='Y'";
	
	
	@Autowired
	DataSource dataSource;
	
	
	@Override
	public Project get(String name) {
		
		Project p = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJECT_SQL);
			ps.setString(1, "%"+name+"%");
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				p = new Project(rs.getString(1), rs.getString(2));
			}
			logger.debug(p.toString());
			
		} catch (SQLException e) {
			logger.error("DB Error while getting project name:" + e.getLocalizedMessage(), e);
		}
		
		return p;
	}
	
	@Override
	public boolean create(List<Project> plist) throws AMSException {
		
		int[] counts = null;
		boolean success = false;
		try(Connection conn = dataSource.getConnection();) 
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(INSERT_PROJECT_SQL);
			
			for(Project p : plist)
			{
				ps.setString(1, p.getpId());
				ps.setString(2, p.getName());
				ps.setString(3, p.getDescription());
				ps.setString(4, p.getLocation());
				ps.setString(5, p.getStartDate());
				ps.addBatch();
			}
			try
			{
				counts = ps.executeBatch();
			}
			catch(BatchUpdateException be)
			{
				conn.rollback();
				logger.error("DB Error during Project Data upload " + be.getLocalizedMessage(), be);
				throw new AMSException("DB Error while Upload:"+ be.getLocalizedMessage());				
			}
			
			logger.info("Records processed -->" + counts.length + ":");
			
			conn.commit();
			success = true;
		} catch (SQLException e) {
			logger.error("DB Error during Project Data upload " + e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while Upload:"+e.getLocalizedMessage());
		}
		
		return success;
	}

	@Override
	public boolean create(Project p) throws AMSException {
		int row = 0;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(INSERT_PROJECT_SQL);
			ps.setString(1, p.getpId());
			ps.setString(2, p.getName());
			ps.setString(3, p.getDescription());
			ps.setString(4, p.getLocation());
			ps.setString(5, p.getStartDate());
			row = ps.executeUpdate();
			logger.info("Project created successfully:" + p);
			
		} catch (SQLException e) {
			logger.error("Error during Project Create:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error during Project Create:"+e.getLocalizedMessage());
		}
		return row > 0;
	}
	
	@Override
	public boolean updateProject(Project p) throws AMSException
	{
		int row = 0;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(UPDATE_PROJECT_SQL);
			ps.setString(1, p.getName());
			ps.setString(2, p.getDescription());
			ps.setString(3, p.getLocation());
			ps.setString(4, p.getStartDate());
			ps.setString(5, p.getpId());
			
			row = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error while updating Project:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error while updating Project:"+e.getLocalizedMessage());
		}
		
		return row > 0;
	}

	@Override
	public Project getProjectByPID(String pId) {
		Project p = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJECT_BY_PID_SQL);
			ps.setString(1, pId);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				p = new Project(pId, rs.getString(1), rs.getString(2),rs.getString(4));
				p.setLocation(rs.getString(3));
			}
			
		} catch (SQLException e) {
			logger.error("Error while retrieving project information:"+ e.getLocalizedMessage(), e);
		}
		
		return p;
	}

	@Override
	public List<String> getProjectNameList(String query) {
		List<String> projectNameList = new ArrayList<>();
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJECT_NAME_LIST_SQL);
			ps.setString(1, "%"+query+"%");
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				projectNameList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while retrieving Project Names"+e.getLocalizedMessage(), e);
		}
		
		return projectNameList;		
	}

	@Override
	public List<String> getProjectIdList(String query) {

		List<String> projectNameList = new ArrayList<>();
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJECT_ID_LIST_SQL);
			ps.setString(1, "%"+query+"%");
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				projectNameList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while retrieving Project IDs.."+e.getLocalizedMessage(), e);
		}
		
		return projectNameList;		
	
	}

	@Override
	public Project getProjectByName(String pname) {
		Project p = null;
		try(Connection conn = dataSource.getConnection())
		{
			logger.info("DAO"+ pname);
			PreparedStatement ps = conn.prepareStatement(GET_PROJ_BY_NAME_SQL);
			ps.setString(1, pname);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				p = new Project();
				p.setpId(rs.getString(1));
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while retrieving Project IDs.."+e.getLocalizedMessage(), e);
		}
		
		return p;
	}



}
