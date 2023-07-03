package in.infrasupport.hr.ams.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import in.infrasupport.hr.ams.model.AttendanceEntry;
import in.infrasupport.hr.ams.model.ProjectStatus;
import in.infrasupport.hr.ams.model.ReportCriteria;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.ReportService;

@Controller
@RequestMapping("/web")
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	ReportService reportService; 
	
	@RequestMapping(value = { "/reports"}, method = RequestMethod.GET)
    public String reportPage() {
        return "report";
    }
	
	@RequestMapping(value = { "/psreports"}, method = RequestMethod.GET)
    public String statusReportPage() {
        return "psreport";
    }

	
/*	@RequestMapping(value = "/reports/attend-{empId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<AttendanceEntry> getAttendance(@PathVariable("empId") String empId) {
		logger.info("Getting Attendance for " + empId);
		List<AttendanceEntry> attendEntryList = reportService.getAllAttendance();
		return attendEntryList;
	}*/		  
	
	@RequestMapping(value = "/reports/attend-{empId}-{startDate}-{endDate}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<AttendanceEntry> getAttendance(@PathVariable("empId") String empId,
							@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) {
		startDate = startDate.replace("~", "-");
		endDate = endDate.replace("~", "-");
		ReportCriteria reportCriteria = new ReportCriteria(empId,startDate,endDate);
		logger.info("Getting Attendance for " + empId + ":" + startDate + ":" + endDate);
		List<AttendanceEntry> attendEntryList = reportService.getAttendance(reportCriteria);
		return attendEntryList;
	}
	
	

	@RequestMapping(value = "/reports/attenddefaulter-{startDate}-{endDate}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<User> getAttendanceDefaulters(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) {
		startDate = startDate.replace("~", "-");
		endDate = endDate.replace("~", "-");
		ReportCriteria reportCriteria = new ReportCriteria(startDate,endDate);
		logger.info("Getting Attendance Defaulters for " + ":" + startDate + ":" + endDate);
		List<User> userList = reportService.getAttDefaulters(reportCriteria);
		return userList;
	}
	
	
	@RequestMapping(value = "/reports/attenddaily-{reportDate}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<AttendanceEntry> getDailyAttendance(@PathVariable("reportDate") String reportDate) {
		reportDate = reportDate.replace("~", "-");
		logger.info("Getting Daily Attendance for all Employees for Date:"+reportDate);
		List<AttendanceEntry> attendEntryList = reportService.getDailyAttendance(reportDate);
		return attendEntryList;
	}
	
	@RequestMapping(value = "/reports/attenddump-{dumpDate}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<AttendanceEntry> getAttDump(@PathVariable("dumpDate") String dumpDate) {
		dumpDate = dumpDate.replace("~", "-");
		logger.info("Getting Attendance Dump for all Employees for Date:"+dumpDate);
		List<AttendanceEntry> attendEntryList = reportService.getAttDump(dumpDate);
		return attendEntryList;
	}

	
	
	
	
	@RequestMapping(value = "/reports/psr-{pId}-{startDate}-{endDate}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ProjectStatus> getStatusReport(@PathVariable("pId") String pId,
							@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) {
		startDate = startDate.replace("~", "-");
		endDate = endDate.replace("~", "-");
		ReportCriteria reportCriteria = new ReportCriteria(startDate,endDate);
		reportCriteria.setpId(pId);
		
		logger.info("Getting Status Report for Project " + pId + ":" + startDate + ":" + endDate);
		List<ProjectStatus> psList = reportService.getProjectStatus(reportCriteria);
		return psList;
	}

	@RequestMapping(value = "/reports/psrgenpdf-{pId}-{startDate}-{endDate}", method = RequestMethod.GET)
	public void getStatusReportPDF(@PathVariable("pId") String pId,	@PathVariable("startDate") String startDate,
										@PathVariable("endDate") String endDate, HttpServletResponse response) {
		startDate = startDate.replace("~", "-");
		endDate = endDate.replace("~", "-");
		ReportCriteria reportCriteria = new ReportCriteria(startDate,endDate);
		reportCriteria.setpId(pId);
		
		logger.info("Generating Status Report PDF for Project " + pId + ":" + startDate + ":" + endDate);
		ByteArrayOutputStream baos = reportService.getProjectStatusForPDF(reportCriteria);
		
		if(baos != null)
		{
			try
			{
	            // setting some response headers
	            response.setHeader("Expires", "0");
	            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
	            response.setHeader("Content-disposition", "attachment; filename=" + "Status-Report-PID-" + pId + "-Date-"+startDate+"-"+endDate +".pdf" );
	            response.setHeader("Pragma", "public");
	            // setting the content type
	            response.setContentType("application/pdf");
	            // the contentlength
	            response.setContentLength(baos.size());
	            // write ByteArrayOutputStream to the ServletOutputStream
	            OutputStream os = response.getOutputStream();
	            baos.writeTo(os);
	            os.flush();
	            os.close();
			}catch(IOException e)
			{
				logger.error("IO Error while Generating PDF"+e.getLocalizedMessage(), e);
			}
			
		}
		
		
	}
	
	
	
	
}
