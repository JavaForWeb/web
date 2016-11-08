package bo.gob.asfi.servlets;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.io.PrintWriter;

/*
@WebServlet(
	name = "JMS Servlet",
	description = "A sample JMS Sender",
	urlPatterns = {"jms"}
)
*/

public class JmsServlet extends HttpServlet {
/*
	@Resource(lookup = "java:/ConnectionFactory") ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/sampleQueue")
	Destination destination;


	static Logger log = Logger.getLogger(SimpleServlet.class.getName());


	public void doGet(
		HttpServletRequest req,
		HttpServletResponse resp
	) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		log.info("do get jms");
		try {
			//Authentication info can be omitted if we are using in-vm
			QueueConnection connection = (QueueConnection)
				connectionFactory.createConnection(
					"myJmsUser",
					"myJmsPassword"
				);

			try {
				QueueSession session =
					connection.createQueueSession(
						false,
						Session.AUTO_ACKNOWLEDGE
					);

				try {
					MessageProducer producer =
						session.createProducer(destination);

					try {
						TextMessage message =
							session.createTextMessage(
								"Hello, world! ^__^"
							);

						producer.send(message);

						writer.println(
							"Message sent! ^__^"
						);
					} finally {
						producer.close();
					}
				} finally {
					session.close();
				}

			} finally {
				connection.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace(writer);
		}
	}
	 */

}
