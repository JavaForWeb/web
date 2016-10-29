package bo.gob.asfi.servlets;

import bo.gob.asfi.entity.Account;
import bo.gob.asfi.utils.DBSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fernando on 10/27/16.
 */

@WebServlet(
	name = "AccountServlet",
	description = "A simple Accounts servlet",
	urlPatterns = {"/accounts"}
)
public class AccountServlet extends HttpServlet
{
	static Logger log = Logger.getLogger(TransferServlet.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		log.info("doGet ");
		PrintWriter writer = response.getWriter();



		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("AccountsHQLwithLAZY").
			setFirstResult(1).
			setMaxResults(20);

		List<Account> accounts = query.list();

		String strContentType = request.getContentType();

		if ( "application/json".equals(strContentType)) {
			response.setContentType("application/json");
			String json = new ObjectMapper().writeValueAsString(accounts);
			writer.println(json);
		} else {
			writer.println("\nNamed HQL query for Account" );
			writer.printf("|%6s|%-30s|%-30s|%-30s|%-10s|\n", "id", "name", "description", "date", "number");
			for (Account account: accounts) {
				writer.printf("|%6d|%-30s|%-30s|%-30s|%-10s|\n", account.getId(), account.getName(), account.getLocation(),
					account.getDate(), account.getBalance());
			}
		}

		session.getTransaction().commit();
		writer.flush();
	}

}
