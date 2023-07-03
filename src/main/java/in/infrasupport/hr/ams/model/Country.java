package in.infrasupport.hr.ams.model;

import java.io.Serializable;

public class Country implements Serializable{

	private String cid;
	private String cname;
	
	public Country() {
		// TODO Auto-generated constructor stub
	}
	public Country(String cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
