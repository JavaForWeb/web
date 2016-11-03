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
import java.util.Random;

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
	static Integer count = 0;
	static Integer countAccounts = 0;
	static Integer countTransfers = 0;
	static Random random = new Random();

	public TransferServlet()
	{
		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		countAccounts = ((Long)session.createQuery("select count(*) from Account").uniqueResult()).intValue();
		countTransfers = ((Long)session.createQuery("select count(*) from Transfer").uniqueResult()).intValue();

		log.info( "there are " + countTransfers + " transfers");
	}

	private void executeCount(HttpServletResponse response) throws IOException
	{
		log.info("executeHQLwithLazy");
		PrintWriter writer = response.getWriter();
		writer.println("" + getServletContext().getServletContextName());
		writer.println("  ?type=0 count transfer");
		writer.println("  ?type=1 executeHQLwithLazy");
		writer.println("  ?type=2 transfer example");


		writer.println("\nNamed HQL query for Account" );

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		countTransfers = ((Long)session.createQuery("select count(*) from Transfer").uniqueResult()).intValue();

		log.info("there are " + countTransfers + " transfers");
		writer.println("there are " + countTransfers + " transfers");

		session.getTransaction().commit();
		writer.flush();
		writer.close();

	}

	private void executeHQLwithLazy(HttpServletResponse response) throws IOException
	{
		log.info("executeHQLwithLazy");
		PrintWriter writer = response.getWriter();

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

		writer.println("\nNamed HQL query for Transfer" );

		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();


		Query query = session.getNamedQuery("findAllTransferNativeSql").
			setFirstResult(0).
			setMaxResults(20);
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

	private void executeSampleTransfer(HttpServletResponse response) throws IOException
	{
		PrintWriter writer = response.getWriter();

		int source = random.nextInt(countAccounts);
		int target = random.nextInt(countAccounts);
		int amount = random.nextInt(100)*5;
		String transferDesc = "";
		String status;

		try {
			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Account accountSource = session.get(Account.class, source);
			Account accountTarget = session.get(Account.class, target);

			if(accountSource.getBalance() > amount) {
				accountSource.setBalance(accountSource.getBalance() - amount);
				accountTarget.setBalance(accountTarget.getBalance() + amount);

				transferDesc = String
					.format("(%d)%s sent %5d to (%d)%s ", accountSource.getId(), accountSource.getName(), amount, accountTarget.getId(),
						accountTarget.getName());
				status = "success";

			} else {
				transferDesc = String.format("(%d)%s try to send %5d to (%d)%s ", accountSource.getId(), accountSource.getName(), amount,
					accountTarget.getId(), accountTarget.getName());
				status = "no funds";
			}

			Transfer transfer = new Transfer(null, accountSource, accountTarget, amount, transferDesc, status);
			session.save(transfer);

			session.getTransaction().commit();
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		writer.print(transferDesc);
		writer.flush();

		count ++;
		log.info(count + " " + transferDesc );
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{

		String type = request.getParameter("type");

		if (type == null || type.equals("1")) {
			executeHQLwithLazy(response);
			return;
		}
		/*
		if (type == null || type.equals("2")) {
			executeHQLwithJoin(response);
			return;
		}
		*/
		if (type == null || type.equals("2")) {
			executeSampleTransfer(response);
			return;
		}
		executeCount(response);
	}

}
