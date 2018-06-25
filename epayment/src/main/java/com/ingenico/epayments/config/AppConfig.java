package com.ingenico.epayments.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ingenico.epayments.dao.AccountDao;
import com.ingenico.epayments.dao.AccountDaoImpl;

/**
 * Application Configuration class.
 * 
 * @author Devoteam
 *
 */
@Configuration
@ConfigurationProperties
@ComponentScan(basePackages = "com.ingenico.epayments.*")
public class AppConfig {

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Value("${spring.datasource.url}")
	private String URL;

	@Value("${spring.datasource.username}")
	private String USER_NAME;

	@Value("${spring.datasource.driver-class-name}")
	private String DRIVER_CLASS;

	@Value("${spring.datasource.password}")
	private String PASSWORD;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {

		logger.info("-------Start : DataSource config-------------------------");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS);
		dataSource.setUrl(URL);
		dataSource.setUsername(USER_NAME);
		dataSource.setPassword(PASSWORD);
		logger.info("-------End : DataSource config-------------------------");

		return dataSource;
	}

	@Bean
	public AccountDao getAccountDao() {
		return new AccountDaoImpl(getDataSource());
	}

}
