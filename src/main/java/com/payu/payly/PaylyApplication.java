package com.payu.payly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableCaching
@Slf4j
public class PaylyApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(PaylyApplication.class, args);
	log.info ("::::::::::::PAYMENT LINK APPLICATION RUNNING::::::::::::");
	}

}
