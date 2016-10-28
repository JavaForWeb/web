package bo.gob.asfi.servlets;

import bo.gob.asfi.entity.Account;
import bo.gob.asfi.entity.Transfer;
import bo.gob.asfi.utils.DBSession;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by fernando on 10/27/16.
 */

@WebServlet(
	name = "TransferServlet",
	description = "A simple Transfer servlet",
	urlPatterns = {"/transfer"}
)
public class TransferServlet extends HttpServlet
{
	static Logger log = Logger.getLogger(TransferServlet.class.getName());

	private void executeHQLwithLazy(HttpServletResponse response) throws IOException
	{
		log.info("executeHQLwithLazy");
		PrintWriter writer = response.getWriter();
		writer.println("" + getServletContext().getServletContextName());


		writer.println("\nNamed HQL query for Account" );

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("TransferHQLwithLAZY").
			setFirstResult(0).
			setMaxResults(20);

		List<Transfer> transfers = query.list();

		writer.printf("|%6s|%-30s|%-30s|%-25s|%-10s|\n", "id", "source", "target", "status", "amount");
		for (Transfer transfer: transfers) {

			//for (FAddress address : customer.getAddresses() ) {
			writer.printf("|%6d|%-30s|%-30s|%-25s|%-10s|\n",
				transfer.getId(),
				transfer.getSource().getName(),
				transfer.getTarget().getName(),
				transfer.getStatus(),
				transfer.getAmount());
			//}
		}

		session.getTransaction().commit();
		writer.flush();

	}

	private void executeHQLwithJoin(HttpServletResponse response) throws IOException
	{
		log.info("executeHQLwithJoin");
		PrintWriter writer = response.getWriter();
		writer.println("" + getServletContext().getServletContextName());


		writer.println("\nNamed HQL query for Transfer" );

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();


		Query query = session.getNamedQuery("findAllTransferNativeSql");
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List data = query.list();

		writer.printf("|%6s|%-30s|%-30s|%-25s|%-10s|\n", "id", "source", "target", "status", "amount");
		for(Object object : data) {
			Map row = (Map)object;
			Iterator<Object> iterator = row.keySet().iterator();
			while (iterator.hasNext()) {
				//String key º= row.get( iterator.next());
				Transfer transfer = (Transfer)row.get(iterator.next());
				writer.printf("|%6s|%-30s|%-30s|%-25s|%-10s|\n",
					transfer.getId(),
					transfer.getSource().getName(),
					transfer.getTarget().getName(),
					transfer.getStatus(),
					transfer.getAmount()
				);
			}
		}

		List<Transfer> transfers = query.list();

		session.getTransaction().commit();
		writer.flush();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{

		String type = request.getParameter("type");

		if (type == null || type.equals("1")) {
			executeHQLwithJoin(response);
			return;
		}
		executeHQLwithLazy(response);
	}

}
