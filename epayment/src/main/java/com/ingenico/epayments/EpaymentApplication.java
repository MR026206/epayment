package com.ingenico.epayments;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Spring Boot E-payment Application Start-up
 * @author Devoteam
 *
 */
@SpringBootApplication
public class EpaymentApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(EpaymentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EpaymentApplication.class, args);
		logger.debug("Epayment Application - Started");
	}
}
