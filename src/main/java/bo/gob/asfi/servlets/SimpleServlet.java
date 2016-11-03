package bo.gob.asfi.servlets;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by fernando on 10/27/16.
 */

@WebServlet(
	name = "SimpleServlet",
	description = "A sample and simple annotated servlet",
	urlPatterns = {"/sample", "/simple"}
)
public class SimpleServlet extends HttpServlet
{
	static Logger log = Logger.getLogger(SimpleServlet.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		log.info("doGet  ");

		PrintWriter writer = response.getWriter();
		writer.println("<html>Hello, I am a simple Java servlet!</html>");
		writer.flush();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		String paramWidth = request.getParameter("width");
		int width = Integer.parseInt(paramWidth);

		String paramHeight = request.getParameter("height");
		int height = Integer.parseInt(paramHeight);

		long area = width * height;

		log.info("doPost " + width + " x " + height);

		PrintWriter writer = response.getWriter();
		writer.println("Area of the rectangle is: " + area + "");
		writer.flush();

	}
}
