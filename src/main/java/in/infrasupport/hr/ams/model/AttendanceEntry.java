package in.infrasupport.hr.ams.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AttendanceEntry implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String empId;
	private String firstName;
	private String pId;
    private String projectName;
    private String addressLocation;
    private String lat;
    private String lon;
    private String date;
    private String timestamp;
	private int isSync;
	private float totalTime;
    //private byte[] imgData;

    public AttendanceEntry() {
        //default const
    }

    public AttendanceEntry(String lat, String lon, String date) {
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

     public AttendanceEntry(int id,String projectName, String lat, String lon, String date, String timestamp) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.lat = lat;
		this.lon = lon;
		this.date = date;
		this.timestamp = timestamp;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getAddressLocation() {
		return addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }	
	
    @Override
    public String toString() {
        return "AttendanceEntry{" +
                "empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pId='" + pId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", addressLocation='" + addressLocation + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", date='" + date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

	public float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(float totalTime) {
		//this.totalTime = totalTime;
		this.totalTime = BigDecimal.valueOf(totalTime).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	}	
    
}
