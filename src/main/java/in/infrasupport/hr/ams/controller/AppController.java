package in.infrasupport.hr.ams.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.infrasupport.hr.ams.model.User;
import in.infrasupport.hr.ams.service.UserService;

@Controller
@RequestMapping("/web")
@SessionAttributes("USER")
public class AppController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        return "login";
    }
	
	@RequestMapping(value = { "/login"}, method = RequestMethod.POST)
    public String authenticateUser(@RequestParam String empId, @RequestParam String password,
    												RedirectAttributes redirectAttributes, ModelMap model) {
		logger.info("Employee Id {} ",empId);
		logger.info("Password {} ",password);
		
		User user = userService.authenticate(empId, password);   

		if(user != null)
		{
			model.addAttribute("USER", user);
			return "home";
		}
		else
		{
			redirectAttributes.addAttribute("error", "Invalid username and password!");
			return "redirect:/web/login";
		}
        
    }
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET)
	public String showHome()
	{
		return "home";
	}
	
	@RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    public String logout(HttpSession session,Model model) {
		logger.info("Loggin out-->");
		session.invalidate();
		model.asMap().clear();
        return "redirect:/web/login";
    }	


}
