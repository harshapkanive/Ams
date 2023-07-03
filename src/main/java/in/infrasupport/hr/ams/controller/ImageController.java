package in.infrasupport.hr.ams.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.infrasupport.hr.ams.dao.AttendanceDAO;
import in.infrasupport.hr.ams.dao.ProjectStatusDAO;

@Controller
public class ImageController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	AttendanceDAO attendanceDAO; // directly calling DAO TO DO - call from service

	@Autowired
	ProjectStatusDAO projectStatusDAO;
	
	@RequestMapping(value="/web/viewimage-{id}",method=RequestMethod.GET)
	public void showImage(@PathVariable("id") Integer attId, HttpServletResponse response)
	{
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

		byte[] rawdata = attendanceDAO.getAttendanceImage(attId);
		if(rawdata != null)
			try {
				response.getOutputStream().write(rawdata);
			} catch (IOException e) {
				logger.error("IO Error while getting Image data for Attendance", e);
			}
	}

	@RequestMapping(value="/web/psimg-{id}",method=RequestMethod.GET)
	public void showProjStatusImage(@PathVariable("id") Integer id, HttpServletResponse response)
	{
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

		byte[] rawdata = projectStatusDAO.getProjectStatImg(id);
		if(rawdata != null)
			try {
				response.getOutputStream().write(rawdata);
			} catch (IOException e) {
				logger.error("IO Error while getting Proj Status Image " + e.getLocalizedMessage(), e);
			}
	}	
	
}
