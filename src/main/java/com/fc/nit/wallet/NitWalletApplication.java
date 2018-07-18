package com.fc.nit.wallet;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class NitWalletApplication extends SpringBootServletInitializer implements CommandLineRunner {

	Logger log = Logger.getLogger(NitWalletApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(NitWalletApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("启动完成");
	}
}
