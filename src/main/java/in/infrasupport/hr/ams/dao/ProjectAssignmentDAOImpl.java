package in.infrasupport.hr.ams.dao;

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
import in.infrasupport.hr.ams.model.ProjectAssignment;
import in.infrasupport.hr.ams.model.User;

@Repository("projectAssignmentDAO")
public class ProjectAssignmentDAOImpl implements ProjectAssignmentDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProjectAssignmentDAOImpl.class);
	private static final String GET_PROJ_ASSIGN_SQL="select p.projectname, p.pid from project p, project_assignment pa, user u where pa.active='Y' and u.empid = pa.empid and pa.pid = p.pid and u.empid=?";
	private static final String CREATE_ASSIGNMENT_SQL = "insert into project_assignment(empid,pid,startdate) values(?,?,?)";
	
	private static final String GET_PROJ_ASSIGN_LISt_SQL = "select pa.id,pa.empid,p.projectname, p.pid, pa.startdate from project p, project_assignment pa, user u where pa.active='Y' and u.empid = pa.empid and pa.pid = p.pid and u.empid=?";
	private static final String GET_PROJ_ASSIGN_BY_ASSID = "select pa.empId, pa.pid, p.projectname,pa.startdate from project_assignment pa, project p where pa.pid = p.pid and pa.id = ?";
	
	private static final String UPDATE_PROJ_ASSIGN = "update project_assignment set pid=?, startdate=? where id=?";
	private static final String INACTIVATE_PROJ_ASSIGN_SQL = "update project_assignment set active=? where id=?";
	
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public ProjectAssignment get(String empId) {
		ProjectAssignment pa = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJ_ASSIGN_SQL);
			ps.setString(1, empId);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				pa = new ProjectAssignment(empId, rs.getString(1));
				pa.setpId(rs.getString(2));
			}
			
			logger.debug("Getting Project Assignment-->" + pa);
			
		} catch (SQLException e) {
			logger.error("DB error while getting Project Assignment:"+ e.getLocalizedMessage(), e);
		}
		return pa;
	}

	@Override
	public boolean assign(ProjectAssignment pa) throws AMSException {
		int row = 0; // auto generated key
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(CREATE_ASSIGNMENT_SQL);
			ps.setString(1, pa.getEmpId());
			ps.setString(2, pa.getpId());
			ps.setString(3, pa.getStartDate());
			
			row = ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("Error while assigning project:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error while assigning project:"+e.getLocalizedMessage());
		}
		return row > 0;
	}

	@Override
	public List<ProjectAssignment> getAssignmentList(String empId) {
		List<ProjectAssignment> palist = new ArrayList<>();
		ProjectAssignment pa = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJ_ASSIGN_LISt_SQL);
			ps.setString(1, empId);

			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				pa = new ProjectAssignment();
				pa.setId(rs.getInt(1));
				pa.setEmpId(rs.getString(2));
				pa.setProjectName(rs.getString(3));
				pa.setpId(rs.getString(4));
				pa.setStartDate(rs.getString(5));
				palist.add(pa);
			}
			
			logger.info("Getting Project Assignment LIST-->" + palist);
			
		} catch (SQLException e) {
			logger.error("DB error while getting Project Assignment:"+ e.getLocalizedMessage(), e);
		}
		return palist;
	}

	@Override
	public boolean uploadProjectAssignment(List<User> ulist) throws AMSException {
		int count = 0;
		boolean success = false;
		try(Connection conn = dataSource.getConnection();) 
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(CREATE_ASSIGNMENT_SQL);
			
			for(User u : ulist)
			{
				if(u.getPa() != null)
				{
					ps.setString(1,u.getPa().getEmpId());
					ps.setString(2,u.getPa().getpId());
					ps.setString(3,u.getPa().getStartDate());
					try
					{
						count = count + ps.executeUpdate();
					}
					catch(SQLException e)
					{
						conn.rollback();
						logger.error("DB Error during Project Assignment Data upload " + e.getLocalizedMessage(), e);
						logger.info(u.toString());
						logger.info(u.getPa().toString());
						throw new AMSException("DB Error while Uploading Project Assignment Date:"+ e.getLocalizedMessage());				
					}			

				}
			}
			
			logger.info("Project Assignment Records processed -->" + count + ":");

			conn.commit();
			success = true;
			
		} catch (SQLException e) {
			logger.error("DB Error while inserting Project Assignment Date"+e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while inserting Project Assignment Date"+e.getLocalizedMessage());
		}
		return success;
	}

	@Override
	public ProjectAssignment getAssignmentById(String assignId) {
		ProjectAssignment pa = null;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_PROJ_ASSIGN_BY_ASSID);
			int id = Integer.parseInt(assignId);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				pa = new ProjectAssignment();
				pa.setEmpId(rs.getString(1));
				pa.setpId(rs.getString(2));
				pa.setProjectName(rs.getString(3));
				pa.setStartDate(rs.getString(4));
				pa.setId(id);
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Project Assignment information for assignment Id" + assignId
					+e.getLocalizedMessage(), e);
		}
		
		return pa;
	}

	@Override
	public boolean update(ProjectAssignment projectAssignment) throws AMSException{
		int row = 0;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(UPDATE_PROJ_ASSIGN);
			ps.setString(1, projectAssignment.getpId());
			ps.setString(2, projectAssignment.getStartDate());
			ps.setInt(3,projectAssignment.getId());
			
			row = ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("DB Error while updating Project Assignment information for assignment Id" 
					+ projectAssignment.getId() +e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while updating Project Assignment information for assignment Id" 
					+ e.getLocalizedMessage());
		}
		return row > 0;
	}

	@Override
	public boolean delete(String assignId) {
		// no record will be deleted - record is made inactive
		int row = 0;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(INACTIVATE_PROJ_ASSIGN_SQL);
			ps.setString(1, "N");
			ps.setInt(2, Integer.parseInt(assignId));
			row = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB Error while deleting Project Assignment information for assignment Id" 
					+ assignId + e.getLocalizedMessage(), e);
		}
		return row > 0;
	}

}
