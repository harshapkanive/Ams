package in.infrasupport.hr.ams.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import in.infrasupport.hr.ams.model.ProjectStatus;

public class PDFUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PDFUtil.class);
	
	public static ByteArrayOutputStream  generatePSPDF(List<ProjectStatus> psList)
	{
		ByteArrayOutputStream baos = null;
		
        try {
            // step 1
            Document document = new Document();
            // step 2
            baos = new ByteArrayOutputStream();

        	PdfWriter.getInstance(document, baos);
            // step 3
            document.open();
            // step 4
            
            Font fontbold = new Font(Font.FontFamily.HELVETICA  , 20, Font.BOLD);
            Font fontUL = new Font(Font.FontFamily.HELVETICA  , 15, Font.BOLD | Font.UNDERLINE );
            
            Phrase header = new Phrase("Infra Support - Project Status Report", fontbold);
            document.add(header);
            document.add(Chunk.NEWLINE);
            Phrase project = null;
            if(psList.size() > 0)
            {
            	project = new Phrase(psList.get(0).getProjectName(), fontUL);
            }
            else
            {
            	project = new Phrase("No records found for the Project", fontUL);
            }
            document.add(project);
            
            for(ProjectStatus ps : psList)
            {
            	document.add(Chunk.NEWLINE);
                PdfPTable table = new PdfPTable(4); // 4 columns.

                PdfPCell cell1hdr = new PdfPCell(new Paragraph("EMP ID"));
                PdfPCell cell2hdr = new PdfPCell(new Paragraph("Name"));
                PdfPCell cell3hdr = new PdfPCell(new Paragraph("Date"));
                PdfPCell cell4hdr = new PdfPCell(new Paragraph("Status"));
                
                
                PdfPCell cell1 = new PdfPCell(new Paragraph(ps.getEmpId()));
                PdfPCell cell2 = new PdfPCell(new Paragraph(ps.getFirstName()));
                PdfPCell cell3 = new PdfPCell(new Paragraph(ps.getTimeStamp()));
                PdfPCell cell4 = new PdfPCell(new Paragraph(ps.getStatusDesc()));

                table.addCell(cell1hdr);
                table.addCell(cell2hdr);
                table.addCell(cell3hdr);
                table.addCell(cell4hdr);
                
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                
                document.add(table);
                
                document.add(Chunk.NEWLINE);
                
                Image img = Image.getInstance(ps.getImgData());
                img.setAlignment(Image.ALIGN_CENTER);
                document.add(img);
                document.add(Chunk.NEWLINE);
            }
            
            // step 5
            document.close();
        
        } catch (DocumentException | IOException e) {
        	logger.error("Error while creating PDF:" + e.getLocalizedMessage(), e);
		}
		
		return baos;
	}

}
