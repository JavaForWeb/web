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
import java.util.Random;

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
	static Logger log = Logger.getLogger(AccountServlet.class.getName());
	static Integer count = 0;
	static Random random = new Random();

	static Integer countAccounts = null;

	public AccountServlet()
	{
		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		countAccounts = ((Long)session.createQuery("select count(*) from Account").uniqueResult()).intValue();

		log.info( "there are " + countAccounts + " accounts");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			count++;
			PrintWriter writer = response.getWriter();

			//Todo: check limits
			int pageSize = 20;
			int pagesTotal = countAccounts / pageSize;
			int pageNumber = random.nextInt(pagesTotal) * pageSize;

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("AccountsHQLwithLAZY").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<Account> accounts = query.list();

			String strContentType = request.getContentType();
			log.info("get accouns, count= " + count + " " + accounts.get(1).getName());

			if("application/js	on".equals(strContentType)) {
				response.setContentType("application/json");
				String json = new ObjectMapper().writeValueAsString(accounts);
				writer.println(json);
			} else {
				writer.println("\nNamed HQL query for Account (count=" + count + ")");
				writer.printf("|%6s|%-30s|%-30s|%-30s|%-10s|\n", "id", "name", "description", "date", "number");
				for(Account account : accounts) {
					writer.printf("|%6d|%-30s|%-30s|%-30s|%-10s|\n", account.getId(), account.getName(), account.getLocation(), account.getDate(), account.getBalance());
				}
			}

			session.getTransaction().commit();
			writer.flush();
		} catch( Exception e) {
			log.error(e);
		}
	}

}
