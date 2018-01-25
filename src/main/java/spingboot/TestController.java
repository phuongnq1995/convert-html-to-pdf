package spingboot;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Controller
public class TestController {
	
	@Autowired
    ServletContext context;
	
	@RequestMapping(value="/home")
	public String getView() {
		return "welcome";
	}
	
	@RequestMapping(value="/export")
	public void export(Model model, HttpServletResponse res) throws DocumentException, IOException {
		Document document = new Document();
		File temp = File.createTempFile("pdf", ".pdf", new File(context.getRealPath("/")+"WEB-INF\\file"));
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(temp));
        document.open();
        XMLWorkerHelper.getInstance(). parseXHtml(writer, document,
                new FileInputStream(context.getRealPath("/")+"WEB-INF\\jsp\\welcome.jsp"));	
        document.close();
        
        System.out.println( "PDF Created!" );
        // Write file output stream
        temp.deleteOnExit();
        FileInputStream fileInuptStream1 = new FileInputStream(temp);
        BufferedInputStream bufferedInputStream1 = new BufferedInputStream(fileInuptStream1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int start = 0;
        int length = 1024;
        int offset = -1;
        byte[] buffer = new byte[length];

        // Write BufferedInputStream
        while ((offset = bufferedInputStream1.read(buffer, start, length)) != -1) {
            byteArrayOutputStream.write(buffer, start, offset);
        }

        // Close inputStream & outputstream
        bufferedInputStream1.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();

        // Set content type return client
        res.setHeader("Content-Disposition", "attachment;filename=pdf.pdf");
        res.setContentType("application/pdf ; charset=utf-8");
        OutputStream out = res.getOutputStream();
        out.write(byteArrayOutputStream.toByteArray());
        out.flush();
        out.close();
        temp.delete();
		return;
	}
}
