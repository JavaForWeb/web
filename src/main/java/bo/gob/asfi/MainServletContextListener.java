package bo.gob.asfi;

import bo.gob.asfi.utils.Common;
import bo.gob.asfi.utils.DBSession;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Properties;

/**
 * Created by fernando on 10/25/16.
 */

@WebListener
public class MainServletContextListener implements ServletContextListener
{
	static Logger log = Logger.getLogger(MainServletContextListener.class.getName());

	@Override public void contextInitialized(ServletContextEvent sce)
	{
		log.info("Main Servlet Context Listener started ");

		Properties config = Common.readConfig();

		log.info("Starting PostgreSQL tutorial ");

		DBSession.getInstance().initFactory(config);

	}

	@Override public void contextDestroyed(ServletContextEvent sce)
	{
		log.info("server shut down");
	}
}
