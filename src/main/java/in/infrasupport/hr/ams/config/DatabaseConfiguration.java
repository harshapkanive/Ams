package in.infrasupport.hr.ams.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DatabaseConfiguration {

	   @Autowired
	    private Environment environment;
	   
/*	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
	        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
	        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
	        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
	        return dataSource;
	    } */	   
	
	   @Bean
	    public DataSource dataSource() {
		   BasicDataSource dataSource = new BasicDataSource();
		   dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		   dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		   dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
	       dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
	       dataSource.setInitialSize(5);
	       dataSource.addConnectionProperty("maxTotal", "10");
	       dataSource.addConnectionProperty("maxWaitMillis", "10000");
	       dataSource.addConnectionProperty("destroy-method", "close");
		   return dataSource;
	   }
	   
}
