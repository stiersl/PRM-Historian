/*
 * After adding this file, no web.xml or springmvc-servlet.xml is necessary.
 * If Eclipse complains about a missing web.xml, add this section to the pom.xml file:
    <properties>
    	<failOnMissingWebXml>false</failOnMissingWebXml>
    </properties>
 */
package com.stevenstier;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan
public class BasicWebMVCInit implements WebMvcConfigurer, WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(BasicWebMVCInit.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(dispatcherContext));
		dispatcher.addMapping("/");
	}

	// This method enables pass through of requests not handled by the dispatcher
	// servlet (for static assets, for example).
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		String dburl = System.getenv("JDBC_DATABASE_URL");
		
		if (dburl != null){
		  dataSource.setUrl(dburl);
		 }
		 else {
		   dataSource.setUrl("jdbc:postgresql://localhost:5432/prm");
		 }
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		return dataSource;
	}

	/*
	 * Uncomment this block to use JWT authorization.
	 */
	/*
	 * @Bean public JwtAuthInterceptor interceptor() { List<String> exceptions = new
	 * ArrayList<String>(); exceptions.add("/register"); exceptions.add("/login");
	 * return new JwtAuthInterceptor(exceptions); }
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(interceptor()); }
	 */
}
