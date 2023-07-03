package in.infrasupport.hr.ams.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String empId;
	private String email;
	private String firstName;
	private String lastName;
	private String role;
	private String password;
	private String isActive;
	private String plainTextPwd; // non persistent field
	private ProjectAssignment pa;
	private List<ProjectAssignment> paList;

	private AttendanceEntry ae;
	private List<AttendanceEntry> attendEntries;
	
	private List<ProjectStatus> psList;
	
	public User() {
		super();
	}

	public User(String empId, String password) {
		super();
		this.empId = empId;
		this.password = password;
	}

	public User(String empId, String email, String firstName, String lastName) {
		super();
		this.empId = empId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String empId, String email, String firstName, String lastName, String role, String password,
			String isActive) {
		super();
		this.empId = empId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.password = password;
		this.isActive = isActive;
	}

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<AttendanceEntry> getAttendEntries() {
		return attendEntries;
	}

	public void setAttendEntries(List<AttendanceEntry> attendEntries) {
		this.attendEntries = attendEntries;
	}

	public ProjectAssignment getPa() {
		return pa;
	}

	public void setPa(ProjectAssignment pa) {
		this.pa = pa;
	}
	
	public AttendanceEntry getAe() {
		return ae;
	}

	public void setAe(AttendanceEntry ae) {
		this.ae = ae;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		return result;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		return true;
	}

	public List<ProjectAssignment> getPaList() {
		return paList;
	}

	public void setPaList(List<ProjectAssignment> paList) {
		this.paList = paList;
	}

	@Override
	public String toString() {
		return "User [empId=" + empId + ", email=" + email + ", firstName=" + firstName + ", role=" + role + ", pa="
				+ pa + ", paList=" + paList + ", ae=" + ae + ", attendEntries=" + attendEntries + "]";
	}

	public String getPlainTextPwd() {
		return plainTextPwd;
	}

	public void setPlainTextPwd(String plainTextPwd) {
		this.plainTextPwd = plainTextPwd;
	}

	public List<ProjectStatus> getPsList() {
		return psList;
	}

	public void setPsList(List<ProjectStatus> psList) {
		this.psList = psList;
	}
	
}
