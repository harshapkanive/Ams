package in.infrasupport.hr.ams.model;

public class ReportCriteria {

	private String empId;
	private String pId;
	private String startDate;
	private String endDate;
	
	public ReportCriteria() {
		super();
	}
	
	public ReportCriteria(String empId, String startDate, String endDate) {
		super();
		this.empId = empId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ReportCriteria(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
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

	@Override
	public String toString() {
		return "ReportCriteria [empId=" + empId + ", pId=" + pId + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}
	
}
