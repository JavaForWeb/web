package bo.asfi.gob.servlets;

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
	name = "AnnotatedServlet",
	description = "A sample and simple annotated servlet",
	urlPatterns = {"/sample", "/simple"}
)
public class SimpleServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

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

		PrintWriter writer = response.getWriter();
		writer.println("Area of the rectangle is: " + area + "");
		writer.flush();

	}
}
