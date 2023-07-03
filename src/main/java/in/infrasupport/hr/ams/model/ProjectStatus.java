package in.infrasupport.hr.ams.model;

import java.util.Base64;

public class ProjectStatus {
	
    private int id;
    private String empId;
    private String firstName;
    private String pId;
    private String projectName;
    private String date;
    private String timeStamp;
    private byte[] imgData;
    private int isSync;
    private String statusDesc;

    private String strImgData;
    
    public ProjectStatus() { }

    public ProjectStatus(String empId, String firstName, String pId, String projectName) {
        this.empId = empId;
        this.firstName = firstName;
        this.pId = pId;
        this.projectName = projectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }


    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }
    
	@Override
    public String toString() {
        return "ProjectStatus{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pId='" + pId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", date='" + date + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", isSync=" + isSync +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}	
	


