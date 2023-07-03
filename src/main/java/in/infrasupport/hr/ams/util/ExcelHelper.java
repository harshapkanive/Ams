package in.infrasupport.hr.ams.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import in.infrasupport.hr.ams.model.Project;
import in.infrasupport.hr.ams.model.ProjectAssignment;
import in.infrasupport.hr.ams.model.User;

public class ExcelHelper {

	private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);
	
	public static List<Project> processProjectData(MultipartFile excelfile)
	{
		List<Project> projectList = new ArrayList<Project>();
		DataFormatter formatter = new DataFormatter();
		Project p = null;

		try {
			int i = 1;

			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);			

			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);
				if(row != null)
				{
					p = new Project(formatter.formatCellValue(row.getCell(0)),
							formatter.formatCellValue(row.getCell(1)),
							formatter.formatCellValue(row.getCell(2)),
							formatter.formatCellValue(row.getCell(3)),
							formatter.formatCellValue(row.getCell(4)));
					projectList.add(p);
					logger.debug(p.toString());
				}
			}			
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return projectList;
	}

	public static List<User> processEmpData(MultipartFile excelfile)
	{
		logger.debug("Processing Employee Data");
		List<User> userList = new ArrayList<>();
		DataFormatter formatter = new DataFormatter();
		User u = null;
		ProjectAssignment pa = null;
		
		try {
			int i = 1;

			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);			

			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);
				if(row != null)
				{
					u = new User(formatter.formatCellValue(row.getCell(0)),
							formatter.formatCellValue(row.getCell(1)),
							formatter.formatCellValue(row.getCell(2)),
							formatter.formatCellValue(row.getCell(3)));
					if(u.getEmpId().trim().length() == 0)
					{
						continue; // empty employee Id skip the row
					}
					pa = new ProjectAssignment(formatter.formatCellValue(row.getCell(0)),
							formatter.formatCellValue(row.getCell(6)),DateUtil.getCurrentDateAsString());
					if(pa.getpId().trim().length() == 0)
					{
						pa = null;
						logger.info("Emp Id-->" + u.getEmpId() + " Not assigned to any project");
					}
					
					u.setPa(pa);
					userList.add(u);
					logger.debug(u.toString());
				}
			}			
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}				
		
		return userList;
	}
}
