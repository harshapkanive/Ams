package in.infrasupport.hr.ams.controller;

import java.util.List;

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
import in.infrasupport.hr.ams.model.Project;
import in.infrasupport.hr.ams.model.ProjectAssignment;
import in.infrasupport.hr.ams.service.ProjectService;

@Controller
@RequestMapping("/web")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = { "/project"}, method = RequestMethod.GET)
    public String showProjectForm(Model model) {
		logger.info("Show Project Form");
		model.addAttribute("project", new Project());
        return "project";
    }	
	
	@RequestMapping(value = { "/project"}, method = RequestMethod.POST)
	public String createProject(@ModelAttribute("project") Project project, RedirectAttributes redirectAttributes)
	{
		logger.info("Project Create -->" + project);
		
		try 
		{
			if(projectService.createProject(project))
			{
				redirectAttributes.addFlashAttribute("message", "Project Created Successfully");
				redirectAttributes.addFlashAttribute("newproject", project);
			}
			else
			{
				redirectAttributes.addFlashAttribute("error", "Unknown error while creating Project!");
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/project";
	}
	
	@RequestMapping(value = { "/projectupdate"}, method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("project") Project project, RedirectAttributes redirectAttributes)
	{
		logger.info("Update Project -->" + project);
		
		try 
		{
			if(projectService.updateProject(project))
			{
				redirectAttributes.addFlashAttribute("message", "Project updated Successfully");
				redirectAttributes.addFlashAttribute("newproject", project);;
			}
			else
			{
				redirectAttributes.addFlashAttribute("error", "Unknown error while updating Project!");
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/project"; 
	}	
	
	@RequestMapping(value = { "/project-edit-{pId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String pId, ModelMap model) {
		Project project = projectService.getProject(pId);
		model.addAttribute("project", project);
		model.addAttribute("edit", true);
		return "project";
	}	
	
	@RequestMapping(value="/assignproject", method=RequestMethod.GET)
	public String showassignpage(Model model)
	{
		model.addAttribute("projectAssignment", new ProjectAssignment());
		return "assign";
	}	
	
	@RequestMapping(value="/assignproject", method=RequestMethod.POST)
	public String assignProject(@ModelAttribute("projectAssignment") ProjectAssignment projectAssignment,
																		RedirectAttributes redirectAttributes)
	{
		logger.info("Assigning Project for " + projectAssignment.toString());
		
		try 
		{
			if(projectService.createProjectAssignment(projectAssignment))
			{
				redirectAttributes.addFlashAttribute("message", "Project Assigned Successfully");
				redirectAttributes.addFlashAttribute("projAssignList", 
											projectService.getProjectAssmtList(projectAssignment.getEmpId()));
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}

		return "redirect:/web/assignproject";
	}
	
	@RequestMapping(value = { "/projectassign-edit-{assignId}" }, method = RequestMethod.GET)
	public String editProjectAssignment(@PathVariable String assignId, ModelMap model) {
		logger.info("Getting PA info for Assign ID {}",assignId);
		ProjectAssignment projectAssignment = projectService.getAssignmentById(assignId);
		model.addAttribute("projectAssignment", projectAssignment);
		model.addAttribute("edit", true);
		return "assign";
	}
	
	@RequestMapping(value = { "/assignprojectupdate"}, method = RequestMethod.POST)
	public String updateProjectAssignment(@ModelAttribute("projectAssignment") ProjectAssignment projectAssignment, RedirectAttributes redirectAttributes)
	{
		logger.info("Update Project Assignment -->" + projectAssignment);
		
		try 
		{
			if(projectService.updateProjectAssignment(projectAssignment))
			{
				redirectAttributes.addFlashAttribute("message", "Project Assignment updated Successfully");
				redirectAttributes.addFlashAttribute("projAssignList", 
						projectService.getProjectAssmtList(projectAssignment.getEmpId()));
			}
			else
			{
				redirectAttributes.addFlashAttribute("error", "Unknown error while updating Project!");
			}
		} catch (AMSException e) {
			redirectAttributes.addFlashAttribute("error", e.getExceptionInfo());
		}
		
		return "redirect:/web/assignproject"; 
	}	
	
	@RequestMapping(value = "/projectassign-del-{assignId}-{empId}", method = RequestMethod.GET)
	public String deleteProjectAssignment(@PathVariable("assignId") String assignId, 
			@PathVariable("empId") String empId,RedirectAttributes redirectAttributes)
	{
		logger.info("Inactivating project assignment for emp {}, Assign Id {}", empId,assignId);
		
		if(projectService.deleteProjectAssmt(assignId))
		{
			redirectAttributes.addFlashAttribute("message", "Project Assignment updated Successfully");
			redirectAttributes.addFlashAttribute("projAssignList",projectService.getProjectAssmtList(empId));
		}
		else {
			redirectAttributes.addFlashAttribute("error", "Unknown Error while deleting the assignment");
		}
		return "redirect:/web/assignproject";
	}	
	
	@RequestMapping(value = "/get-assign-{empId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ProjectAssignment> getProjectAssignment(@PathVariable("empId") String empId)
	{
		logger.info("Getting project assignment for " + empId);
		
		return projectService.getProjectAssmtList(empId);
	}

	@RequestMapping(value = "/get-projectbyName-{pname}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Project getProjectDetailsByName(@PathVariable("pname") String pname)
	{
		logger.info("Getting project Details by Name " + pname);
		
		return projectService.getProjectByName(pname);
	}	
	
	
	@RequestMapping(value = "/get-project-{pid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Project getProjectDetails(@PathVariable("pid") String pid)
	{
		logger.info("Getting project Details for " + pid);
		
		return projectService.getProject(pid);
	}	

	@RequestMapping(value = "/projectIdlist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<String> getProjectIdList(@RequestParam("term") String query) {
		logger.info(query);
		List<String> projectNameList = projectService.getProjectIdList(query);
		return projectNameList;
	}	
	
	
	@RequestMapping(value = "/projectnamelist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<String> getProjectNameList(@RequestParam("term") String query) {
		logger.info(query);
		List<String> projectNameList = projectService.getProjectNameList(query);
		return projectNameList;
	}	
	
}
