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
import in.infrasupport.hr.ams.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDAO{
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	//Authentication always checks if user is active
	private static final String USER_AUTH_SQL = "select firstname,role,password from user where empid=? and isactive='Y'";
	
	private static final String GET_USER_SQL = "select email,firstname,lastname,role,password,isactive from user where empid=?";
	private static final String FIND_USER_BY_EMPID_SQL = "select empId,firstname,email from user where empid=? and isactive='Y'";
	private static final String PARTIAL_SEARCH_USER_BY_NAME_SQL = "select empId,firstname,email from user where firstname like ? and isactive='Y'"; 
	
	private static final String CREATE_USER_SQL = "insert into user(empid,firstname,lastname,email) values (?,?,?,?)";
	private static final String UPDATE_USER_SQL = "update user set firstname=?,lastname=?,email=? where empId=?";
	private static final String GET_EMPIDS_LIST_SQL = "select empid from user where empid like ? and isactive='Y'";
	
	private static final String CREATE_USER_SQL_DATAUPLOAD = "insert into user(empid,email,firstname,lastname) values (?,?,?,?)"; 
	
	@Autowired 
	DataSource dataSource;
	
	@Override
	public User getUser(String empId)
	{
		User user = null;
		try(Connection conn = dataSource.getConnection();) 
		{
			PreparedStatement ps = conn.prepareStatement(GET_USER_SQL);
			ps.setString(1,empId);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				user = new User(empId,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				logger.info(user.toString());
			}
			
		} catch (SQLException e) 
		{
			logger.error("Exception:", e);
		}		
		return user;
	}

	@Override
	public List<User> findByName(String name)
	{
		List<User> userList = new ArrayList<>();
		User user = null;
		try(Connection conn = dataSource.getConnection();) 
		{
			PreparedStatement ps = conn.prepareStatement(PARTIAL_SEARCH_USER_BY_NAME_SQL);
			ps.setString(1, "%"+name+"%");
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				user = new User();
				user.setEmpId(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setEmail(rs.getString(3));
				logger.info("EMP -->" + user);
				userList.add(user);
			}
			
		} catch (SQLException e) 
		{
			logger.error("Error while finding Empoyee by Name", e.getLocalizedMessage());
			logger.error("Exception:", e);
		}
		
		return userList;
	}
	
	@Override
	public User authenticate(String empId) {

		User user = null;
		try(Connection conn = dataSource.getConnection();) 
		{
			PreparedStatement ps = conn.prepareStatement(USER_AUTH_SQL);
			ps.setString(1, empId);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				user = new User();
				user.setFirstName(rs.getString(1));
				user.setRole(rs.getString(2));
				user.setPassword(rs.getString(3));
				logger.info("EMP ROLE-->" + user.getRole());
			}
			
		} catch (SQLException e) 
		{
			logger.error("Error while finding Empoyee by EMpID", e.getLocalizedMessage());
			logger.error("Exception:", e);
		}
		
		return user;
	}

	@Override
	public boolean create(User user) throws AMSException {
		int row = 0;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(CREATE_USER_SQL);
			ps.setString(1, user.getEmpId());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			//ps.setString(4, user.getPassword());
			ps.setString(4, user.getEmail());
			
			row = ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("DB Error while creating User:"+e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while creating User:"+e.getLocalizedMessage());
		}
		
		return row > 0;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(User user) throws AMSException {
		int row = 0;
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getEmpId());
			row = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error during update:"+e.getLocalizedMessage(), e);
			throw new AMSException("Error during update:"+e.getLocalizedMessage());
		}
		
		return row > 0;
	}

	@Override
	public List<String> getEmpIdList(String query) {
		List<String> empIdList = new ArrayList<>();
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(GET_EMPIDS_LIST_SQL);
			ps.setString(1, "%"+query+"%");
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				empIdList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while retrieving EmpIds"+e.getLocalizedMessage(), e);
		}
		
		return empIdList;
	}

	@Override
	public boolean uploadUserData(List<User> ulist) throws AMSException {
		
		int count = 0;
		boolean success = false;
		try(Connection conn = dataSource.getConnection();) 
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(CREATE_USER_SQL_DATAUPLOAD);
			
			for(User u : ulist)
			{
				ps.setString(1, u.getEmpId());
				ps.setString(2, u.getEmail());
				ps.setString(3, u.getFirstName());
				ps.setString(4, u.getLastName());
				try
				{
					count = count + ps.executeUpdate();
				}
				catch (SQLException e) {
					if(e.getLocalizedMessage().contains("Duplicate"))
					{
						//this is because one employee can be allocated to multiple projects
						//hence the excel can contain duplicate rows but different projects
						logger.info("Ignoring Duplicate entry for Employee Record:" + u.getEmpId());
					}
					else
					{
						conn.rollback();
						logger.info("USER -->" + u.toString());
						logger.error("DB Error during Employee Data upload " + e.getLocalizedMessage(), e);
						throw new AMSException("DB Error while Upload:"+ e.getLocalizedMessage());				
					}
				}
			}
			
			logger.info("Records processed -->" + count);
			conn.commit();
			success = true;
			
		} catch (SQLException e) {
			logger.error("DB Error during User Data upload " + e.getLocalizedMessage(), e);
			throw new AMSException("DB Error while Upload:"+e.getLocalizedMessage());
		}
		
		return success;
	}

	@Override
	public User findUserById(String empId) {
		
		User user = null;
		try(Connection conn = dataSource.getConnection();) 
		{
			PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_EMPID_SQL);
			ps.setString(1,empId);
			ResultSet rs = ps.executeQuery();
			
			if(rs != null && rs.next())
			{
				user = new User();
				user.setEmpId(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setEmail(rs.getString(3));
			}
			logger.info("EMP -->" + user);

			
		} catch (SQLException e) {
			logger.error("Error while finding Empoyee by ID:" + empId, e.getLocalizedMessage());
			logger.error("Exception:", e);
		}		
		
		return user;
	}

	@Override
	public List<User> findAllUsersForPasswordGen() {
		List<User> userList = new ArrayList<>();
		User u = null;
		String query = "select empid,email from user where password is null";
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs != null && rs.next())
			{
				u = new User();
				u.setEmpId(rs.getString(1));
				u.setEmail(rs.getString(2));
				userList.add(u);
			}
			
		} catch (SQLException e) {
			logger.error("DB Error while getting Employee Records for Emailing password", e);
		}
		
		return userList;
	}

	@Override
	public boolean updateUser(List<User> userList) {
		int[] counts = null;
		boolean success = false;
		String query = "update user set password=? where empid=?";
		try(Connection conn = dataSource.getConnection();) 
		{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(query);
			
			for(User u : userList)
			{
				ps.setString(1, u.getPassword());
				ps.setString(2, u.getEmpId());
				ps.addBatch();
			}
			try
			{
				counts = ps.executeBatch();
			}
			catch(BatchUpdateException be)
			{
				conn.rollback();
				logger.error("DB Error during Bulk Password Update " + be.getLocalizedMessage(), be);
			}			
			logger.info("Records processed -->" + counts);
			conn.commit();
			success = true;
			
		} catch (SQLException e) {
			logger.error("DB Error during Bulk Password Update " + e.getLocalizedMessage(), e);
		}
		
		return success;
	}

	@Override
	public String getEmailId(String empId) {
		String query = "select email from user where empid=?";
		String email = null;
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, empId);
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				email = rs.getString(1);
			}
			
		} catch (SQLException e) {
			logger.error("Error while getting email for " + empId  + ":" + e.getLocalizedMessage(), e);
		}
		
		return email;
	}

	@Override
	public boolean updatePassword(User user) throws AMSException{
		int row = 0;
		String sql = "update user set password=? where empid=?";
		
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getEmpId());
			row = ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("Error while updating password for " + user.getEmpId() + ":" + e.getLocalizedMessage(), e);
			throw new AMSException("Error while updating password for " + user.getEmpId() + ":" + e.getLocalizedMessage());
		}

		return row > 0;
	}

	@Override
	public boolean inActivate(String empId) {
		int rows = 0;
		String sql = "update user set isactive='N' where empid=?";
		try(Connection conn = dataSource.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empId);
			
			rows = ps.executeUpdate();
			logger.info("User Inactivated successfully");
			
		} catch (SQLException e) {
			logger.error("Error while In activating User "+ empId , e);
		}
		
		return rows > 0;
	}


}
