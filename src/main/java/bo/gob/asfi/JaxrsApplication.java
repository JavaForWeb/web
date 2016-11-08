package bo.gob.asfi;

import bo.gob.asfi.resource.CuentasResource;
import bo.gob.asfi.resource.SueldosResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.OAuth2Definition;
import org.apache.log4j.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fernando on 11/8/16.
 */

public class JaxrsApplication extends Application
{
	static Logger log = Logger.getLogger(JaxrsApplication.class.getName());
	private static final Set<Object> emptySet =	Collections.emptySet();

	public JaxrsApplication() {
		BeanConfig beanConfig = new BeanConfig();

		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[]{"http"});
		beanConfig.setTitle("JavaForWeb API Documentation");
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/api");
		//beanConfig.setFilterClass("com.bpmiot.backend.util.ApiAuthorizationFilterImpl");
		beanConfig.setResourcePackage("bo.gob.asfi.resource");
		beanConfig.setScan(true);
		log.info("swagger found " + beanConfig.classes().size() + " classes");
		beanConfig.setPrettyPrint(true);

		Swagger swagger = beanConfig.getSwagger();

		swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
		swagger.securityDefinition("petstore_auth",
			new OAuth2Definition()
				.implicit("http://petstore.swagger.io/api/oauth/dialog")
				.scope("read:pets", "read your pets")
				.scope("write:pets", "modify pets in your account"));

		//new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);

	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		//add my Resources classes
		set.add(CuentasResource.class);
		set.add(SueldosResource.class);

		//add swagger classes
		set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		return set;
	}

	public Set<Object> getSingletons() {
/*
		JsonWriter json = new JsonWriter();
		CreditCardResource service = new CreditCardResource();

		HashSet<Object> set = new HashSet();
		set.add(json);
		set.add(service);
		*/
		return emptySet;
	}
}
