package in.infrasupport.hr.ams.exception;

public class AMSException extends Exception{

	private String exceptionInfo;

	public AMSException(String exceptionInfo) {
		super();
		this.exceptionInfo = exceptionInfo;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	
	
}
