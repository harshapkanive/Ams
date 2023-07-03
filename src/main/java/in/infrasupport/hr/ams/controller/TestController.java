package in.infrasupport.hr.ams.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.infrasupport.hr.ams.dao.ProjectStatusDAO;
import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.service.MailerService;
import in.infrasupport.hr.ams.util.MailHelper;
import in.infrasupport.hr.ams.util.PDFUtil;

@Controller
@RequestMapping("/web")
public class TestController {
	
	@Autowired
	MailHelper mailHelper;
	
	@Autowired
	MailerService mailerService;
	
	@Autowired
	ProjectStatusDAO projectStatusDAO;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String showtestpage()
	{
		return "test";
	}

	@RequestMapping(value="/test/sendmail",method=RequestMethod.GET)
	public String sendEmail()
	{
		try {
			mailHelper.sendMail("sadanand.rud@gmail.com", "Attendance Mgmt System", "Dear User, Please use the generated password to log into the system " 
									+ RandomStringUtils.randomAlphanumeric(8));
			System.out.println("Mail Sent------------>");
		} catch (AMSException e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	@RequestMapping(value="/test/sendbulkmail",method=RequestMethod.GET)
	public String sendBulkEmail(Model model)
	{
		mailerService.sendBulkPwdMail();
		model.addAttribute("message", "Password Regeneration and E-mail Job has been successfully triggerred.");
		return "test";
	}	
	
	@RequestMapping(value="/test/genpdf",method=RequestMethod.GET)
	public void genPDF(HttpServletResponse response) throws IOException
	{
		 try {
	            
			 	ByteArrayOutputStream baos = PDFUtil.generatePSPDF(projectStatusDAO.getProjectStatusForPDF(null));
	            // setting some response headers
	            response.setHeader("Expires", "0");
	            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
	            response.setHeader("Content-disposition", "attachment; filename=" + "Testing.pdf");
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
	        }
	        catch(Exception e) {
	            throw new IOException(e.getMessage());
	        }		
		
	}		
	
}
