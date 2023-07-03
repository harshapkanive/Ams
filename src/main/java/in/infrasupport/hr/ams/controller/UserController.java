package in.infrasupport.hr.ams.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.infrasupport.hr.ams.exception.AMSException;
import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.UserService;

@Controller
@RequestMapping("/web")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;

	
	@RequestMapping(value = "/employeeIdlist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<String> getEmpIdList(@RequestParam("term") String query) {
		logger.info("Term " + query);
		List<String> empIdList = userService.getEmpIdList(query);
		logger.info(empIdList.toString());
		return empIdList;
	}		
	
	@RequestMapping(value = { "/searchuser"}, method = RequestMethod.GET)
    public String showSearchPage(ModelMap model) {
		logger.info("Search GET");
		model.addAttribute("user", new User());
        return "search";
    }
	
	@RequestMapping(value = { "/searchuser"}, method = RequestMethod.POST)
    public String search(@ModelAttribute User user, Model model) {
		logger.info("Searching for {} {}", user.getEmpId(), user.getFirstName());
		Set<User> userList = userService.searchUser(user.getEmpId(), user.getFirstName());
		model.addAttribute("USERLIST", userList);
        return "search";
    }
	
	@RequestMapping(value = { "/user"}, method = RequestMethod.GET)
	public String showUserForm(Model model)
	{
		model.addAttribute("user", new User());
		return "user";
	}
	
	
	@RequestMapping(value = { "/user"}, method = RequestMethod.POST)
	public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes)
	{
		logger.info("User Create -->" + user);
		
		try 
		{
			if(userService.createUser(user))
			{
				redirectAttributes.addFlashAttribute("message", "User Created Successfully");
				redirectAttributes.addFlashAttribute("newuser", user);
			}
			else
			{
				redirectAttributes.addFlashAttribute("error", "Unknown error while creating User!");
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/user";
	}	

	@RequestMapping(value = { "/userupdate"}, method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes)
	{
		logger.info("Update User -->" + user);
		
		try 
		{
			if(userService.updateUser(user))
			{
				redirectAttributes.addFlashAttribute("message", "User updated Successfully");
				redirectAttributes.addFlashAttribute("newuser", user);
			}
			else
			{
				redirectAttributes.addFlashAttribute("error", "Unknown error while updating User!");
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/user"; 
	}	
	
	
	@RequestMapping(value = { "/user-edit-{empId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String empId, ModelMap model) {
		User user = userService.getUser(empId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "user";  
	}	

	@RequestMapping(value = { "/viewattd-{empId}" }, method = RequestMethod.GET)
    public String viewAttend(@PathVariable String empId, ModelMap model) {
		logger.info("Getting attendance for {}", empId);
		model.addAttribute("ATT_ENTRIES", userService.getAttendance(empId));
		model.addAttribute("EMPID", empId);
        return "attendance";
    }
	
	@RequestMapping(value = { "/userinactive-{empId}" }, method = RequestMethod.GET)
    public String inactivateUser(@PathVariable String empId, Model model) {
		logger.info("Inactivating Employee: {}", empId);
		if(userService.inActivateUser(empId))
		{
			model.addAttribute("message", "User Inactivated Successfully");
			model.addAttribute("user", new User());
		}
		else
		{
			model.addAttribute("error", "Error while Inactivating User!");
		}
		return "search";
    }
	
	@RequestMapping(value = { "/resetpwd"}, method = RequestMethod.GET)
	public String showPwdResetForm(Model model)
	{
		model.addAttribute("user", new User());
		return "reset";
	}
	
	@RequestMapping(value = { "/resetpwd"}, method = RequestMethod.POST)
	public String getUserForReset(@ModelAttribute("user") User user,Model model)
	{
		Set<User> userList = userService.searchUser(user.getEmpId(), user.getFirstName());
		model.addAttribute("USERLIST", userList);
		return "reset";
	}
	
	@RequestMapping(value = { "/resetpwd-{empId}" }, method = RequestMethod.GET)
    public String resetPwdForEmp(@PathVariable String empId, RedirectAttributes redirectAttributes)
    {
		try {
			userService.resetUserPassword(empId);
			redirectAttributes.addFlashAttribute("message", "User Password reset Successfully");
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/resetpwd";
    }
	

}
