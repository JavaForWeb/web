package bo.gob.asfi.resource;

import bo.gob.asfi.entity.Account;
import bo.gob.asfi.entity.Transfer;
import bo.gob.asfi.utils.DBSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by fernando on 11/7/16.
 */
@Api(value = "/cuentas", description = "Operaciones con cuentas")
@Path("cuentas")

public class CuentasResource
{
	static Logger log = Logger.getLogger(CuentasResource.class.getName());
	static Integer count = 0;
	static Random random = new Random();

	static Integer countAccounts = null;
	static Integer countTransfers = null;

	public CuentasResource()
	{
		Session session = DBSession.getInstance().getSession();
		session.beginTransaction();

		countAccounts = ((Long)session.createQuery("select count(*) from Account").uniqueResult()).intValue();
		countTransfers = ((Long)session.createQuery("select count(*) from Transfer").uniqueResult()).intValue();

		log.info( "there are " + countAccounts  + " accounts");
		log.info( "there are " + countTransfers + " transfers");
	}


	@GET
	@ApiOperation(value = "Lista paginada de cuentas [sin autenticación]",
		notes = "No requiere autenticacion.<br>Retorna una lista de cuentas, <br>si page no esta definida, se elige una pagina al azar, <br> pagesize es 20 registros por default",
		response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok"),
		@ApiResponse(code = 204, message = "no existe la pagina solicitada"),
		@ApiResponse(code = 400, message = "malformed message")
	})
	@Produces({"application/json"})
	public List<Account> getAccounts(
		@QueryParam("page") Integer page,
		@QueryParam("pagesize") Integer pageSize
	) {
		try {
			count++;

			if (pageSize == null) {
				pageSize = 20;
			}
			int pagesTotal = countAccounts / pageSize;
			int pageNumber = 0;
			if (page == null) {
				pageNumber = random.nextInt(pagesTotal) * pageSize;
			} else {
				if(page > pagesTotal || page < 0) {
					log.error("error no existe pagina");
					//throw new WebApplicationException(Response.Status.NOT_FOUND);
					throw new NotFoundException();
					/*
					Exception                    |Status code	| Description
					-----------------------------------------------------
					BadRequestException	         | 400	| Malformed message
					NotAuthorizedException       | 401	| Authentication failure
					ForbiddenException   	     | 403	| Not permitted to access
					NotFoundException            | 404	| Couldn’t find resource
					NotAllowedException          | 405	| HTTP method not supported
					NotAcceptableException       | 406	| Client media type requested not supported
					NotSupportedException        | 415	| Client posted media type not supported
					InternalServerErrorException | 500	| General server error
					ServiceUnavailableException  | 503	| Server is temporarily unavailable or busy
					*/
				}
				pageNumber = page * pageSize;
			}

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("AccountsHQLwithLAZY").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<Account> accounts = query.list();
			session.getTransaction().commit();

			return accounts;
		} catch( Exception e) {
			log.error(e);
			throw new BadRequestException();
		}
	}

	@GET
	@Path("secure1")
	@ApiOperation(value = "Lista paginada de cuentas [autenticación web.xml]",
		notes = "Usando web.xml para autenticacion.<br>Retorna una lista de cuentas, <br>si page no esta definida, se elige una pagina al azar, <br> pagesize es 20 registros por default",
		response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok"),
		@ApiResponse(code = 204, message = "no existe la pagina solicitada"),
		@ApiResponse(code = 400, message = "malformed message")
	})
	@Produces({"application/json"})
	public List<Account> getAccountsSecure1(
		@QueryParam("page") Integer page,
		@QueryParam("pagesize") Integer pageSize
	) {
		try {
			count++;

			if (pageSize == null) {
				pageSize = 20;
			}
			int pagesTotal = countAccounts / pageSize;
			int pageNumber = 0;
			if (page == null) {
				pageNumber = random.nextInt(pagesTotal) * pageSize;
			} else {
				if(page > pagesTotal || page < 0) {
					log.error("error no existe pagina");
					//throw new WebApplicationException(Response.Status.NOT_FOUND);
					throw new NotFoundException();
				}
				pageNumber = page * pageSize;
			}

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("AccountsHQLwithLAZY").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<Account> accounts = query.list();
			session.getTransaction().commit();

			return accounts;
		} catch( Exception e) {
			log.error(e);
			throw new BadRequestException();
		}
	}

	@GET
	@Path("secure2")
	@ApiOperation(value = "Lista paginada de cuentas  autenticación SecurityContext]",
		notes = "Usando SecurityContext para autenticacion.<br>Retorna una lista de cuentas, <br>si page no esta definida, se elige una pagina al azar, <br> pagesize es 20 registros por default",
		response = List.class
	)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok"),
		@ApiResponse(code = 204, message = "no existe la pagina solicitada"),
		@ApiResponse(code = 400, message = "malformed message")
	})
	@Produces({"application/json"})
	public List<Account> getAccountsSecure2(
		@Context SecurityContext sec,
		@QueryParam("page") Integer page,
		@QueryParam("pagesize") Integer pageSize
	) {
		log.info(sec.getUserPrincipal());
		log.info("isSecure: " + sec.isSecure());
		if (!sec.isUserInRole("updateREST")) {
			log.info(sec.getUserPrincipal() + " does not have updateREST role");
			throw new NotAuthorizedException("no está autorizado");
		}

		try {
			count++;

			if (pageSize == null) {
				pageSize = 20;
			}
			int pagesTotal = countAccounts / pageSize;
			int pageNumber = 0;
			if (page == null) {
				pageNumber = random.nextInt(pagesTotal) * pageSize;
			} else {
				if(page > pagesTotal || page < 0) {
					log.error("error no existe pagina");
					//throw new WebApplicationException(Response.Status.NOT_FOUND);
					throw new NotFoundException();
				}
				pageNumber = page * pageSize;
			}

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			Query query = session.getNamedQuery("AccountsHQLwithLAZY").
				setFirstResult(pageNumber).
				setMaxResults(pageSize);

			List<Account> accounts = query.list();
			session.getTransaction().commit();

			return accounts;
		} catch( Exception e) {
			log.error(e);
			throw new BadRequestException();
		}
	}

	@POST
	@ApiOperation(value = "crea nueva cuenta",
		notes = "Crear una nueva cuenta",
		response = Account.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "ok"),
		@ApiResponse(code = 201, message = "inserted"),
		@ApiResponse(code = 400, message = "malformed message")
	})
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Account newAccounts(
		@ApiParam(value = "datos de la nueva cuenta", required = true) Account newAccount
	) {
		try {
			Account account = new Account();
			account.setId(newAccount.getId());
			account.setName(newAccount.getName());
			account.setBalance(newAccount.getBalance());
			account.setDate(new Date().toString());
			account.setLocation(newAccount.getLocation());
			account.setScreenName(newAccount.getScreenName());
			account.setDescription(newAccount.getDescription());

			Session session = DBSession.getInstance().getSession();
			session.beginTransaction();

			session.save(account);
			session.getTransaction().commit();

			return account;
		} catch( Exception e) {
			log.error(e);
			throw new BadRequestException();
		}
	}

	@Path("transfers")
	@GET
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
