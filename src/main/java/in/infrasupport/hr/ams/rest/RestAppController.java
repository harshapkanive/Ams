package in.infrasupport.hr.ams.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Result;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.UserService;

@RestController
public class RestAppController {

	private static final Logger logger = LoggerFactory.getLogger(RestAppController.class);

	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/rest/login")
	public ResponseEntity authenticate(@RequestBody User puser) {
		
		logger.info("Rest Login ** Employee Id {} ",puser.getEmpId());
		logger.info("Rest Login ** Password {} ",puser.getPassword());
		
		User user = userService.authenticate(puser.getEmpId(), puser.getPassword()); 
		
		if(user != null)
		{
			user.setPassword("");
			return new ResponseEntity(user, HttpStatus.OK);
		}
		{
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}	
	
/*	@PostMapping(value = "/rest/markatt")
	public ResponseEntity markAttendance(@RequestBody AttendEntry attendEntry)
	{
		logger.info("Mark Attendance for Date {} ",attendEntry.getDate());
		logger.info("Image Data size {} ",attendEntry.getImgData().length);
		return new ResponseEntity(new Result("Success"), HttpStatus.OK);
		//return "result:success";
	}*/
	

	@PostMapping(value = "/rest/attsync")
	public ResponseEntity syncAttendance(@RequestBody User user)
	{
		logger.info("Mark Attendance for User ", user.getEmpId());
		
		try 
		{
			if(userService.syncAttendance(user))
			{
				logger.info("Attendance sync success for " + user.getEmpId());
				return new ResponseEntity(new Result("SUCCESS"), HttpStatus.OK);
			}
			else
			{
				logger.info("Attendance sync FAILURE for " + user.getEmpId());
				return new ResponseEntity(new Result("Failure to sync attendance"), HttpStatus.OK);
			}
		} catch (AMSException e) {
			logger.error("Error during attendance sync for User " + user.getEmpId() + ":" + e.getExceptionInfo(), e);
			return new ResponseEntity(new Result(e.getExceptionInfo()), HttpStatus.OK);
		}
		
	}

	@PostMapping(value = "/rest/pssync")
	public ResponseEntity syncProjectStatus(@RequestBody User user)
	{
		logger.info("Project Status Sync for User ", user.getEmpId());
		
		try 
		{
			if(userService.syncProjectStatus(user))
			{
				logger.info("Project Status sync success for " + user.getEmpId());
				return new ResponseEntity(new Result("SUCCESS"), HttpStatus.OK);
			}
			else
			{
				logger.info("Project Status sync FAILURE for " + user.getEmpId());
				return new ResponseEntity(new Result("Failure to sync attendance"), HttpStatus.OK);
			}
		} catch (AMSException e) {
			logger.error("Error during Project Status sync for User " + user.getEmpId() + ":" + e.getExceptionInfo(), e);
			return new ResponseEntity(new Result(e.getExceptionInfo()), HttpStatus.OK);
		}
		
	}	
	
		
}
