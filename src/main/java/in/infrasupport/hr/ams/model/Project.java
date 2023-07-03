package in.infrasupport.hr.ams.model;

import java.io.Serializable;

public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String pId;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private String isActive;
	private String location;
	
	public Project() {
		super();
	}

	public Project(String pId, String name, String description, String startDate, String location) {
		super();
		this.pId = pId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.location = location;
	}

	public Project(String pId, String name, String description, String startDate) {
		super();
		this.pId = pId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
	}

	public Project(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Project [pId=" + pId + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ "]";
	}	
}
