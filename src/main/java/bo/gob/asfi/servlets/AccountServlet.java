package bo.gob.asfi.servlets;

import bo.gob.asfi.entity.Account;
import bo.gob.asfi.utils.DBSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
		writer.println("" + getServletContext().getServletContextName());


		writer.println("\nNamed HQL query for Account" );

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("AccountsHQLwithLAZY").
			setFirstResult(1).
			setMaxResults(20);

		List<Account> accounts = query.list();

		writer.printf("|%6s|%-30s|%-30s|%-25s|%-10s|\n", "id", "name", "location", "street", "number");
		for (Account account: accounts) {

			//for (FAddress address : customer.getAddresses() ) {
				writer.printf("|%6d|%-30s|%-30s|%-25s|%-10s|\n", account.getId(), account.getName(), account.getLocation(),
					account.getDate(), account.getBalance());
			//}
		}

		session.getTransaction().commit();
		writer.flush();
	}

}
