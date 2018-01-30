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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Controller
public class TestController {
	
	@Autowired
    ServletContext context;
	
	@Autowired
	NotificationService service;
	
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public TestController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }
    
    @RequestMapping(value="/fb")
    public String helloFacebook(Model model, @RequestParam String token) {
        if (token == null) {
            return "redirect:/connect/facebook";
        }
        facebook = new FacebookTemplate(token);
        String[] fields = { "id", "about", "age_range",
        		"birthday", "context", "cover", "currency", "devices",
        		"education", "email", "favorite_athletes", "favorite_teams",
        		"first_name", "gender", "hometown", "inspirational_people", "installed",
        		"install_type", "is_verified", "languages", "last_name", "link", "locale",
        		"location", "meeting_for", "middle_name", "name", "name_format", "political",
        		"quotes", "payment_pricepoints", "relationship_status", "religion",
        		"security_settings", "significant_other", "sports", "test_group", "timezone",
        		"third_party_id", "updated_time", "verified", "video_upload_limits",
        		"viewer_can_send_gift", "website", "work"};
        System.out.println(facebook.fetchObject("me", String.class, fields));
        return "welcome";
    }
	
	@RequestMapping(value="/home")
	public String getView() {
		
		Page<NotificationEntity> pages = service.findNotificationEntity();

		System.out.println(pages.getTotalElements());
		System.out.println(pages.getSize());
		System.out.println(pages.getNumber());
		System.out.println("####################");
		for(NotificationEntity n : pages.getContent()) {
			System.out.println(n.getName());
		}
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
