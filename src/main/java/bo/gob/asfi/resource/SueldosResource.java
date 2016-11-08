package bo.gob.asfi.resource;

import bo.gob.asfi.entity.AbonoSueldos;
import bo.gob.asfi.entity.Account;
import bo.gob.asfi.entity.Transfer;
import bo.gob.asfi.utils.DBSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Random;

/**
 * Created by fernando on 11/3/16.
 */

@Path("sueldos")
public class SueldosResource
{
	static Logger log = Logger.getLogger(SueldosResource.class.getName());
	static Integer count = 0;
	static Random random = new Random();

	static Integer countAccounts = null;
	static Integer countTransfers = null;
	static Integer countSueldos   = null;

	public SueldosResource()
	{
		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		countAccounts = ((Long)session.createQuery("select count(*) from Account").uniqueResult()).intValue();
		countTransfers = ((Long)session.createQuery("select count(*) from Transfer").uniqueResult()).intValue();
		//countSueldos   = ((Long)session.createQuery("select count(*) from AbonoSueldos").uniqueResult()).intValue();

		log.info( "there are " + countAccounts  + " accounts");
		log.info( "there are " + countTransfers + " transfers");
		//log.info( "there are " + countSueldos   + " sueldos");
	}

	@GET
	@Produces({"application/json"})
	public List<AbonoSueldos> getSueldos() {
		try {
			count++;

			int pageSize = 20;
			int pagesTotal = countSueldos / pageSize;
			int pageNumber = random.nextInt(pagesTotal) * pageSize;

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.createQuery("select a from AbonoSueldos as a").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<AbonoSueldos> abonoSueldoses = query.list();
			session.getTransaction().commit();

			return abonoSueldoses;
		} catch( Exception e) {
			log.error(e);
		}
		return null;
	}

	@Path("transfer")
	@GET
	//@Produces({"application/xml","application/json"})
	@Produces({"application/json"})
	public List<Transfer> getTransfers() {
		try {
			count++;

			int pageSize = 20;
			int pagesTotal = countTransfers / pageSize;
			int pageNumber = random.nextInt(pagesTotal) * pageSize;

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.createQuery("select t from Transfer as t").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<Transfer> transfers = query.list();
			session.getTransaction().commit();

			return transfers;
		} catch( Exception e) {
			log.error(e);
		}
		return null;
	}
}
