package in.infrasupport.hr.ams.model;

import java.io.Serializable;

public class ProjectAssignment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id; // assignment id
	private String empId;
	private String pId;
	private String projectName;
	private String startDate;
	private String endDate;
	
	public ProjectAssignment() {
		super();
	}

	public ProjectAssignment(String empId, String pid, String startDate) {
		super();
		this.empId = empId;
		this.pId = pid;
		this.startDate = startDate;
	}

	public ProjectAssignment(String empId, String projectName) {
		super();
		this.empId = empId;
		this.projectName = projectName;
	}

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	@Override
	public String toString() {
		return "ProjectAssignment [id=" + id + ", empId=" + empId + ", pid=" + pId + ", projectName=" + projectName
				+ ", startDate=" + startDate + "]";
	}

}
