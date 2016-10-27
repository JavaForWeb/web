package bo.asfi.gob;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by fernando on 10/25/16.
 */

@WebListener
public class MainServletContextListener implements ServletContextListener
{
	@Override public void contextInitialized(ServletContextEvent sce)
	{
		System.out.println("Main Servlet Context Listener started ");
	}

	@Override public void contextDestroyed(ServletContextEvent sce)
	{
		System.out.println ("server shut down");
	}
}
