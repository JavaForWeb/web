package bo.gob.asfi.utils;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fernando on 11/8/16.
 */
public class ApiOriginFilter implements javax.servlet.Filter {

	static Logger log = Logger.getLogger(DBSession.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		log.info("Adding Access Control Response Headers");
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD, OPTIONS");
		res.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
