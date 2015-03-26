package io.projekt.kino.web.config;

//import org.springframework.web.filter.DelegatingFilterProxy;
import io.projekt.kino.config.AppConfig;

import javax.servlet.Filter;

import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// https://github.com/spring-projects/spring-data-rest/wiki/Adding-Spring-Data-REST-to-an-existing-Spring-MVC-Application
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] { AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebMvcConfig.class, RepositoryRestMvcConfiguration.class };
	}

	@Override
	protected String[] getServletMappings()
	{
		
		return new String[] { "/rest/*" };
	}
	
	//Instead we used SecurityWebApplicationInitializer
	
	@Override
    protected Filter[] getServletFilters() {
       return new Filter[]{
    		   //new DelegatingFilterProxy("springSecurityFilterChain")
    		   new OpenEntityManagerInViewFilter()
       };
    } 
	
	

}
