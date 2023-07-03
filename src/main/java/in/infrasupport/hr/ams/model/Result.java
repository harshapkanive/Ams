package in.infrasupport.hr.ams.model;

import java.io.Serializable;

public class Result implements Serializable{
	
	private String message;
	
	public Result() {
		super();
	}

	public Result(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
