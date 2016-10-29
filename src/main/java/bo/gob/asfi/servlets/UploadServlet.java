package bo.gob.asfi.servlets;

import bo.gob.asfi.entity.UploadFile;
import bo.gob.asfi.utils.DBSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fernando on 10/29/16.
 */
@WebServlet(
	name = "UploadServlet",
	description = "A simple Uploadfile servlet",
	urlPatterns = {"/upload"}
)

public class UploadServlet extends HttpServlet
{
	static Logger log = Logger.getLogger(UploadServlet.class.getName());

	private static final long serialVersionUID = 1L;

	public UploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String paramId = request.getParameter("id");
		int id = Integer.parseInt(paramId);

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();
		UploadFile uploadFile = session.get(UploadFile.class, id);


		byte[] bUploaded = uploadFile.getContent();

		response.setContentType(uploadFile.getMimeType());
		response.setContentLength(bUploaded.length);

		try{
			OutputStream responseOutputStream = response.getOutputStream();
			for (int i = 0; i < bUploaded.length; i++) {
				responseOutputStream.write(bUploaded[i]);
			}
			responseOutputStream.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url;
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart)
		{
			log.error("this submit is not multipart");
		}
		else
		{
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = null;
			try
			{
				items=upload.parseRequest(request);
			}
			catch (FileUploadException e)
			{
				log.error(e.getMessage());
				log.error(e.getCause());
			}
			Iterator itr = items.iterator();
			while (itr.hasNext())
			{
				FileItem item = (FileItem) itr.next();
				if (item.isFormField())	{
					log.error("this submit does not have any field");
				}
				else
				{
					try
					{
						String itemName = item.getName();
						File savedFile = new File(request.getRealPath("/")+"/fileFolder/"+itemName);
						item.write(savedFile);
						url="/fileFolder/"+itemName;
						log.info("alternative url: " + url);
						try
						{
							Date uploadingDate=new Date();
							String date=uploadingDate.toString();


							UploadFile uploadFile = new UploadFile();
							uploadFile.setName(itemName);
							uploadFile.setMimeType(item.getContentType());

							byte[] bFile = new byte[(int) savedFile.length()];

							try {
								FileInputStream fileInputStream = new FileInputStream(savedFile);
								//convert file into array of bytes
								fileInputStream.read(bFile);
								fileInputStream.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							uploadFile.setContent(bFile);

							Session session = DBSession.getInstance().getSession();
							session.beginTransaction();

							session.save(uploadFile);

							session.getTransaction().commit();

							response.sendRedirect("upload?id=" + uploadFile.getId());

						} catch(Exception ex) {
							log.error(ex.getMessage());
						}
					} catch (Exception e)
					{
						log.error(e.getMessage());
						log.error(e.getCause());
					}

				}
			}
		}

	}
}
