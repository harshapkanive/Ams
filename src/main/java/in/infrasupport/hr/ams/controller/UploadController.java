package in.infrasupport.hr.ams.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.Project;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.UploadService;
import in.infrasupport.hr.ams.util.ExcelHelper;

@Controller
@RequestMapping("/web")
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);	
	
	@Autowired
	UploadService uploadService;
	
	
	   @RequestMapping(value = { "/masterdataupload"}, method = RequestMethod.GET)
	    public String uploadData() {
	    	logger.info("Inside upload GET Method****");
	        return "upload";     
	    }	 
	    
		@RequestMapping(value = "/processProjData", method = RequestMethod.POST)
		public String processProjectData(Model model, @RequestParam("projectxlsx") MultipartFile excelfile,
															RedirectAttributes redirectAttributes) {
			List<Project> plist = ExcelHelper.processProjectData(excelfile);
			try
			{
				if(uploadService.uploadProjectData(plist))
				{
					redirectAttributes.addFlashAttribute("message", "Project Data upload Successful!");
				}
				else
				{
					redirectAttributes.addFlashAttribute("error", "Unknown error during project data upload!");
				}
				
			}catch (AMSException amse) {
				redirectAttributes.addFlashAttribute("error", amse.getExceptionInfo());
			}
			
			return "redirect:/web/masterdataupload";	
		}    
		
		@RequestMapping(value = "/processEmpData", method = RequestMethod.POST)
		public String processEmpData(Model model, @RequestParam("employeexlsx") MultipartFile excelfile,
															RedirectAttributes redirectAttributes) {		
	 
			List<User> ulist = ExcelHelper.processEmpData(excelfile);
			
			try {
				if(uploadService.uploadUserData(ulist))
				{
					redirectAttributes.addFlashAttribute("message", "User Data upload Successful!");
				}
				else
				{
					redirectAttributes.addFlashAttribute("error", "Unknown error during User data upload!");
				}			
			} catch (AMSException e) {
				redirectAttributes.addFlashAttribute("error", e.getExceptionInfo()); 
			}
			
			return "redirect:/web/masterdataupload";	
		} 	

}
